import { Component, OnInit } from '@angular/core';

import { StudentService } from './student.service';

import { environment } from '../environments/environment.prod';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  studService: StudentService;

  public version: string = environment.version;

  constructor (studService: StudentService) {
    this.studService = studService;
  }

  ngOnInit() {
    this.studService.fetchStudents();
  }
}
