import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  question = 'Ist dies der Platz an dem die Frage stehen wird?';
  answers = ['Antwort1', 'Antwort2', 'Antwort3', 'Antwort4'];

  constructor() {}

  ngOnInit() {

  }
}

