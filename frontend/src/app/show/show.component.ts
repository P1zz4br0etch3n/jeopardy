import { Component, OnInit, OnChanges } from '@angular/core';
import { QuizService } from '../quiz.service';
import { Question } from '../question';

@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.css']
})
export class ShowComponent implements OnInit, OnChanges {

  show = false;
  selectedQuestion: Question;
  questionCount = 0;
  constructor(private quizService: QuizService) {}
  ngOnInit() {
    this.selectedQuestion = this.quizService.getSelectedQuestion();
  }

  ngOnChanges() {
    this.selectedQuestion = this.quizService.getSelectedQuestion();
  }

  showAnswer() {
    this.show = true;
  }
}
