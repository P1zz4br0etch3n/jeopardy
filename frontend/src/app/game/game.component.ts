import { Component, OnInit, OnChanges } from '@angular/core';
import { QuizService } from '../quiz.service';
import { Quiz } from '../quiz';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, OnChanges {
  selectedQuiz: Quiz;
  questionCount = 0;
  constructor(private quizService: QuizService) {}
  ngOnInit() {
    this.selectedQuiz = this.quizService.getSelectedQuiz();
  }

  ngOnChanges() {
    this.selectedQuiz = this.quizService.getSelectedQuiz();
  }

  nextQuestion() {
    if (this.questionCount + 1 < this.selectedQuiz.questions.length) {
    this.questionCount++;
    }
  }
}

