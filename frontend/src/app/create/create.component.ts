import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {NgForm} from '@angular/forms';
import { Quiz } from '../quiz';
import { Question } from '../question';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  categoryCount = 0;
  createdQuiz: Quiz = {
    id: 0,
    name: '',
    categories: ['Kat1', 'Kat2', 'Kat3', 'Kat4', 'Kat5'],
    questions1: [],
    questions2: [],
    questions3: [],
    questions4: [],
    questions5: []
  };

  @Input() quizname = 'Hier steht der Name des Quiz';
  @Input() category = '';
  @Input() answer1 = '';
  @Input() answer2 = '';
  @Input() answer3 = '';
  @Input() answer4 = '';
  @Input() answer5 = '';
  @Input() question1 = '';
  @Input() question2 = '';
  @Input() question3 = '';
  @Input() question4 = '';
  @Input() question5 = '';

  constructor() {}

  logger() {
    console.log('test');
  }


  clear() {
   this.category = '';
   this.answer1 = '';
   this.answer2 = '';
   this.answer3 = '';
   this.answer4 = '';
   this.answer5 = '';
   this.question1 = '';
   this.question2 = '';
   this.question3 = '';
   this.question4 = '';
   this.question5 = '';
  }

  nextQuestion() {
    let questions: Question[] = [];
    questions.push({
      points: 100,
      name: this.question1,
      answer: this.answer1,
    });
    questions.push({
      points: 200,
      name: this.question2,
      answer: this.answer2,
    });
    questions.push({
      points: 300,
      name: this.question3,
      answer: this.answer3,
    });
    questions.push({
      points: 400,
      name: this.question4,
      answer: this.answer4,
    });
    questions.push({
      points: 500,
      name: this.question5,
      answer: this.answer5,
    });
    switch (this.categoryCount) {
      case 0:
        this.createdQuiz.questions1 = [];
        this.createdQuiz.questions1.concat(questions);
        break;
      case 1:
        this.createdQuiz.questions2 = [];
        this.createdQuiz.questions2.concat(questions);
        break;
      case 2:
        this.createdQuiz.questions3 = [];
        this.createdQuiz.questions3.concat(questions);
        break;
      case 3:
        this.createdQuiz.questions4 = [];
        this.createdQuiz.questions4.concat(questions);
        break;
      case 4:
        this.createdQuiz.questions5 = [];
        this.createdQuiz.questions5.concat(questions);
        break;

    }
    questions = [];
    this.clear();
    this.categoryCount++;
  }

  createQuiz() {
    this.createdQuiz.name = this.quizname;

  }

  ngOnInit() {

  }
  previousQuestion() {
    this.categoryCount--;
  }
}
