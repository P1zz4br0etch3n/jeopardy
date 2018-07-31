
import { Injectable } from '@angular/core';
import { Quiz } from './quiz';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Question } from './question';
import { HttpClient, HttpHeaders } from '@angular/common/http';



@Injectable()
export class QuizService {
  constructor(private http: HttpClient) { }

  quiz: Quiz;
  configUrl = 'https://localhost:8443/jeopardy/rest/games';
  loginUrl = 'https://localhost:8443/jeopardy/rest/login';


  selectedQuiz: Quiz;
  selectedQuestion: Question;

  setSelectedQuiz(selQuiz: Quiz) {
    this.selectedQuiz = selQuiz;
  }

  getSelectedQuiz(): Quiz {
    return this.selectedQuiz;
  }

  setSelectedQuestion(selQuest: Question) {
    this.selectedQuestion = selQuest;
  }

  getSelectedQuestion(): Question {
    return this.selectedQuestion;
  }

  getQuizFromServer() {
   return this.http.get(this.configUrl);
  }



}
