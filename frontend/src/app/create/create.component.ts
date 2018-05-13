import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  @Input() quizname = 'Hier steht der Name des Quiz';
  @Input() answer1 = '';
  @Input() answer2 = '';
  @Input() answer3 = '';
  @Input() answer4 = '';
  @Input() correct1 = false;
  @Input() correct2 = false;
  @Input() correct3 = false;
  @Input() correct4 = false;

  constructor() {}

  logger() {
    console.log(this.correct1);
  }

  onChange(e) {
    if (e.checked === true) {
      this.correct1 = true;
    } else {
      this.correct1 = false;
    }
  }

  onChange2(e) {
    if (e.checked === true) {
      this.correct2 = true;
    } else {
      this.correct2 = false;
    }
  }

  onChange3(e) {
    if (e.checked === true) {
      this.correct3 = true;
    } else {
      this.correct3 = false;
    }
  }

  onChange4(e) {
    if (e.checked === true) {
      this.correct4 = true;
    } else {
      this.correct4 = false;
    }
  }

  nextQuestion() {

  }

  ngOnInit() {

  }
}
