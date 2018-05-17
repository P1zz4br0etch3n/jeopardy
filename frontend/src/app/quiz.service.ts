import { Injectable } from '@angular/core';
import { Quiz } from './quiz';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Question } from './question';



@Injectable()
export class QuizService {
  selectedQuiz: Quiz;
  question: Question = {
    points: 100,
    name: 'Ist dies ein Test?',
    answer: 'Ja'
  };
  question1: Question = {
    points: 200,
    name: 'Ist dies ein zweiter Test?',
    answer: 'Ja2'
  };
  question2: Question = {
    points: 300,
    name: 'Ist dies ein Test?',
    answer: 'Ja'
  };
  question3: Question = {
    points: 400,
    name: 'Ist dies ein zweiter Test?',
    answer: 'Ja2'
  };
  question4: Question = {
    points: 500,
    name: 'Ist dies ein Test?',
    answer: 'Ja'
  };
  testQuiz: Quiz = {
    id: 0,
    name: 'Testquiz',
    questions1: [this.question, this.question1, this.question2, this.question3, this.question4],
    questions2: [this.question, this.question1, this.question2, this.question3, this.question4],
    questions3: [this.question, this.question1, this.question2, this.question3, this.question4],
    questions4: [this.question, this.question1, this.question2, this.question3, this.question4],
    questions5: [this.question, this.question1, this.question2, this.question3, this.question4]
  };
  testQuiz1: Quiz = {
    id: 0,
    name: 'Testquiz1',
    questions1: [this.question, this.question1],
    questions2: [this.question, this.question1],
    questions3: [this.question, this.question1],
    questions4: [this.question, this.question1],
    questions5: [this.question, this.question1]
  };
  testQuiz2: Quiz = {
    id: 0,
    name: 'Testquiz2',
    questions1: [this.question, this.question1],
    questions2: [this.question, this.question1],
    questions3: [this.question, this.question1],
    questions4: [this.question, this.question1],
    questions5: [this.question, this.question1]
  };
  testQuiz3: Quiz = {
    id: 0,
    name: 'Testquiz3',
    questions1: [this.question, this.question1],
    questions2: [this.question, this.question1],
    questions3: [this.question, this.question1],
    questions4: [this.question, this.question1],
    questions5: [this.question, this.question1]
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
