import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Quiz } from '../quiz';
import { Question } from '../question';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from "../authentication/auth.service";

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
    categories: [null, null, null, null, null],
    creator: {
      id: this.authService.currentUser.id,
    },
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

  constructor(private http: HttpClient, public authService: AuthService) { }

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
    this.createdQuiz.categories[this.categoryCount] = {
      id: this.categoryCount,
      name: this.category,
      questions: []
    };
    let questions: Question[] = [];
    questions.push({
      id: 0,
      points: 100,
      name: this.question1,
      answer: this.answer1,
    });
    questions.push({
      id: 1,
      points: 200,
      name: this.question2,
      answer: this.answer2,
    });
    questions.push({
      id: 2,
      points: 300,
      name: this.question3,
      answer: this.answer3,
    });
    questions.push({
      id: 3,
      points: 400,
      name: this.question4,
      answer: this.answer4,
    });
    questions.push({
      id: 4,
      points: 500,
      name: this.question5,
      answer: this.answer5,
    });
    this.createdQuiz.categories[this.categoryCount].questions = [];
    this.createdQuiz.categories[this.categoryCount].questions = this.createdQuiz.categories[this.categoryCount].questions.concat(questions);
    console.log(this.createdQuiz.categories[this.categoryCount].questions);
    this.clear();
    this.categoryCount++;
  }

  createQuiz() {
    this.createdQuiz.name = this.quizname;
    this.sendQuiz(this.createdQuiz);
  }

  ngOnInit() {

  }
  previousQuestion() {
    this.categoryCount--;
    let tmpQuestions: Question[];
    tmpQuestions = this.createdQuiz.categories[this.categoryCount].questions;
    this.question1 = tmpQuestions[0].name;
    this.answer1 = tmpQuestions[0].answer;
    this.question2 = tmpQuestions[1].name;
    this.answer2 = tmpQuestions[1].answer;
    this.question3 = tmpQuestions[2].name;
    this.answer3 = tmpQuestions[2].answer;
    this.question4 = tmpQuestions[3].name;
    this.answer4 = tmpQuestions[3].answer;
    this.question5 = tmpQuestions[4].name;
    this.answer5 = tmpQuestions[4].answer;
    this.category = this.createdQuiz.categories[this.categoryCount].name;
  }

  sendQuiz(quiz: Quiz) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'X-Auth-Token': this.authService.currentUser.authToken,
      })
    };

    this.http.post('rest/games',
     JSON.stringify(quiz), httpOptions).subscribe({ error: e => console.error(e) });

  }
}
