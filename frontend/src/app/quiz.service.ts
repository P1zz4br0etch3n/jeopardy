import { Injectable } from '@angular/core';
import { Quiz } from './quiz';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Question } from './question';



@Injectable()
export class QuizService {
  selectedQuiz: Quiz;
  question: Question = {
    id: 0,
    name: 'Ist dies ein Test?',
    answer: ['Ja', 'Nein', 'Vlt', 'Jein'],
    correct: [0]
  };
  question1: Question = {
    id: 0,
    name: 'Ist dies ein zweiter Test?',
    answer: ['Ja2', 'Nein2', 'Vlt2', 'Jein2'],
    correct: [0]
  };
  testQuiz: Quiz = {
    id: 0,
    name: 'Testquiz',
    questions: [this.question, this.question1]
  };
  testQuiz1: Quiz = {
    id: 0,
    name: 'Testquiz1',
    questions: [this.question]
  };
  testQuiz2: Quiz = {
    id: 0,
    name: 'Testquiz2',
    questions: [this.question]
  };
  testQuiz3: Quiz = {
    id: 0,
    name: 'Testquiz3',
    questions: [this.question]
  };
  quizzes: Quiz[] = [this.testQuiz, this.testQuiz1, this.testQuiz2, this.testQuiz3, this.testQuiz3, this.testQuiz3];
  constructor() { }

  getQuizzes(): Observable<Quiz[]> {
    return of(this.quizzes);
  }

  setSelectedQuiz(selQuiz: Quiz) {
    this.selectedQuiz = selQuiz;
  }

  getSelectedQuiz(): Quiz {
    return this.selectedQuiz;
  }

}
