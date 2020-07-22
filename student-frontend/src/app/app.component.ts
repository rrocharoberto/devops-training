import { Component, OnInit } from '@angular/core';

import { StudentService } from './student.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  studService: StudentService;

  constructor (studService: StudentService) {
    this.studService = studService;
  }

  ngOnInit() {
    this.studService.fetchStudents();
  }
}
