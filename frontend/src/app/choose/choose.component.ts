import { Component, OnInit } from '@angular/core';
import { Quiz } from '../quiz';
import { QuizService } from '../quiz.service';

@Component({
  selector: 'app-choose',
  templateUrl: './choose.component.html',
  styleUrls: ['./choose.component.css']
})
export class ChooseComponent implements OnInit {
  constructor(private quizService: QuizService) {}
    quizzes: Quiz[];

     getQuizzes() {
       this.quizService.getQuizzes().subscribe(quizzes => this.quizzes = quizzes);
     }

     onSelect(quiz: Quiz) {
       this.quizService.setSelectedQuiz(quiz);

       console.log(quiz.name);
     }

     ngOnInit() {
        this.getQuizzes();
     }
}
