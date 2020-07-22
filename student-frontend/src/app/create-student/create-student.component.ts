import { Component, OnInit } from '@angular/core';

import { StudentService } from '../student.service';

@Component({
  selector: 'app-create-student',
  templateUrl: './create-student.component.html',
  styleUrls: ['./create-student.component.css']
})
export class CreateStudentComponent implements OnInit {
  availableCategories = [
    { display: 'Regular', value: 'Regular' },
    { display: 'Special', value: 'special' }
  ]
  studService: StudentService;
  defaultName = '';
  defaultRegristry = '';

  constructor(studService: StudentService) {
    this.studService = studService;
  }

  ngOnInit() {
  }

  onSubmit(submittedForm) {
    if (submittedForm.invalid) {
      return;
    }
    console.log(submittedForm);
    this.studService.addStudent(
      submittedForm.value.registry, 
      submittedForm.value.name, 
      submittedForm.value.category
    );
  }
}
