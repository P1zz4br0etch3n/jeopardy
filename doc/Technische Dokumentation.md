# Technische Dokumentation

## Projektidee

## Verständlichkeit
### Backend
Die Anwendung setzt sich zusammen aus Backend und Frontend. Das Backend stellt
eine RESTful  API bereit, über die sowohl User- als auch Quizdaten angelegt,
abgerufen, verändert und gelöscht werden können. Die Daten werden in der in Wildfly
integrierten H2 Datenbank (Datasource 'ExampleDS') für die Laufzeit persistiert. Es
muss also kein Datenbank Schema zum Ausführen dieser Anwendung angelegt werden. Im
Gegenzug bleiben die Daten bei einem Server Neustart allerdings nicht enthalten.
Um schnell wieder Testdaten erzeugen zu können wurde ein weiterer REST Endpunkt 
bereitgestellt, bei dessen Aufruf der Server Dummy Datensätze erzeugt und in der
Datenbank ablegt. Zudem wurde in der Datei `backend.html` eine Ansicht erstellt
um den Aufruf dieses Endpunktes zu vereinfachen und im Frontend auf der Login Seite 
ein Button eingefügt, der auf diese Ansicht verweist.

### Frontend
Das Frontend wurde in Angular entwickelt und bezieht die anzuzeigenden Daten über 
die RESTful API vom Backend. Für die verschiedenen Ansichten wurden jeweils eigene
Komponenten erstellt, die nach Bedarf um Services ergänzt wurden. Zusätzlich dazu wurde
ein Authentifizierungs-Service für das Login und Session Handling sowie ein Auth Guard
implementiert, der bei jeder Anfrage die Gültigkeit der Anmeldung überprüft. Diese 
Überprüfung findet sowohl lokal im Frontend (Session Attribut `isLoggedIn`) wie auch 
parallel dazu im Backend durch einen weiteren REST Endpunkt statt. Um das Frontend
auszuliefern wurde es zunächst in statisches Javascript kompiliert und dann in das
in der Maven Konfiguration definierte `webResources` Verzeichnis kopiert. Das führt dazu,
dass der Wildfly Server selbst das Frontend und das Backend ausliefert. Damit das
funktioniert, musste im Angular App Routing die option `useHash: true` gesetzt werden,
wodurch die Frontend Pfade nicht mehr als Server-Pfade, sondern mit einem vorangestellten
'#' kompiliert werden. Zudem musste ein Mechanismus eingebaut werden, der nach einem
Neuladen der Seite (Angular State wird dabei resettet) die Session aus dem localStorage
wiederherstellt. Da dabei auch der User Name und die User Id in den localStorage geschrieben
werden und diese potenziell durch Benutzer geändert werden können, werden diese beiden 
bei der serverseitigen Überprüfung der Authentizität ebenfalls geprüft. Dies hat bisher
keinen Nutzen für die Anwendung, aber sollten später mal Rechte mit der User Id verbunden
sein, sind die Sicherheits-Vorkehrungen dafür bereits getroffen. 

### Maven
Die Maven Konfiguration umfasst im Wesentlichen nur die Beschaffung der Abhängigkeiten
und die Regeln für den Build. Die `web.xml` musste außerhalb des `webapp` Verzeichnisses
angelegt werden um das kompilieren des Frontends in dieses Verzeichnis zu ermöglichen,
da dabei der komplette Ordnerinhalt gelöscht wird.

### JavaEE
Die Beans werden durch Annotations automatisch entdeckt. Servlets mussten keine erstellt
werden, da durch die Klasse `RestApplication` automatisch eines für die RESTful API erzeugt
wird. Für die Persistenz wurde wie gesagt der `ExampleDS` Datasrote von Wildfly verwendet.


## Design

![uml diagram](jeopardyUML.png "UML Diagram")



### Beans
In der Anwendung gibt es drei Beans. Eiens für die Authentifizierung, eines für die Dummy
Daten und eines für Persistenz Aufgaben. Das `AuthenticationBean` erledigt Aufgaben wie den
Login, Logout, Erzeugung von Tokens und Gültigkeitsprüfung von Tokens. Das heißt, es wird
überall dort verwendet, wo überprüft werden muss, ob die Anfrage von einem authentifizierten
Benutzer stammt. Das `DummyBean` hat lediglich den Zweck den State zu halten ob die Dummy
Daten bereits angelegt wurden oder nicht. Im `PersistenceBean` finden sämtliche Interaktionen
mit der Datenbank statt.

### Models
Da das Datenmodell dieser Anwendung sehr einfach gehalten wurde, sind die Models für die
Auslieferung der Daten auch gleichzeitig die Entities für Persistenzschicht. Die ids der
Entities sind alle 'generatedValues', so dass man sich darüber keine Gedanken machen muss.
Der einzige selbst definierte Constraint im Datenmodell ist das `unique` Constraint auf dem
User Namen. Für die Kaskadierung von Game, Category und Question wurde der Typ ALL gewählt,
wodurch z.B. bei einer Löschung eines Games auch die zugeordneten Categories und Questions 
entfernt werden, da diese nicht alleine existieren sollen.

### Rest
Durch die Anwesenheit der Klasse `RestApplication` wird das Servlet für die RESTful API
erzeugt. Die weiteren Klassen unterteilen die REST Endpoints in mehrere Bereiche:
Authentifizierung, Dummy Daten, User und Games. Die Klasse `ValidationUser` dient als
Datenstruktur für die Validierung von einem Token mit zugehöriger User Id und Username.
Aus den geschützten Endpoints wird jeweils die Methode `authenticateUserByAuthToken()`
aus dem `AuthenticationBean` aufgerufen. Ist das Token gültig, werden die entsprechenden
Daten aus der Datenbank geholt und zurück gegeben. Andernfalls wird der HTTP Status 401
Unauthorized in den Antwort-Header geschrieben. Wird ein einzelnes Objekt mit einer 
ungültigen ID angefragt, so kommt in den Antwort-Header der Status 404 Not Found.
Das Token wird bei jeder Anfrage im Header unter der Bezeichnung `X-Auth-Token`
erwartet. Wird dieser Header nicht mitgeschickt, erhält der Anfragende ebenfalls
den Status 401.  