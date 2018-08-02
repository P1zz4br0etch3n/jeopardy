
import { Injectable } from '@angular/core';
import { Quiz } from './quiz';
import { Question } from './question';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {AuthService} from "./authentication/auth.service";



@Injectable()
export class QuizService {
  constructor(private http: HttpClient, private authService: AuthService) { }

  quiz: Quiz;
  configUrl = 'rest/games';
  loginUrl = 'rest/login';


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
    const httpOptions = {
      headers: new HttpHeaders({
        'X-Auth-Token': this.authService.currentUser.authToken,
      })
    };

    return this.http.get(this.configUrl, httpOptions);
  }



}
