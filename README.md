# jeopardy
Jeopardy as an JavaEE class project. The frontend is built with Angular2 and 
connected via RESTful API.

## install
To install the backend please run `mvn clean install`.

To install the frontend please run `npm install` in frontend dir.

## run
First run the backend by deploying the jeopardy.war from maven target folder 
to wildfly and run wildfly. After starting wildfly you can access the backend 
on this address: [https://localhost:8443/jeopardy](https://localhost:8443/jeopardy).

Then run the frontend with the command `npm run start` in frontend dir. After starting 
you can access the app on this address: [http://localhost:4200](http://localhost:4200).   