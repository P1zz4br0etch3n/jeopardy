import { Component, OnInit } from '@angular/core';
import { Quiz } from '../quiz';

@Component({
  selector: 'app-choose',
  templateUrl: './choose.component.html',
  styleUrls: ['./choose.component.css']
})
export class ChooseComponent implements OnInit {
    testQuiz: Quiz = {
        id: 0,
        name: 'Testquiz',
        questions: []
     };
    quizzes: Quiz[] = [this.testQuiz, this.testQuiz, this.testQuiz, this.testQuiz, this.testQuiz, this.testQuiz];
     ngOnInit() {

     }
}
