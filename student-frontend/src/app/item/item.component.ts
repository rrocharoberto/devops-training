import { Component, OnInit, Input } from '@angular/core';

import { StudentService } from '../student.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {
  @Input() student;
  studService: StudentService;

  constructor(studService: StudentService) {
    this.studService = studService;
  }

  ngOnInit() {
  }

  onAssign(category) {
    this.studService.onCategoryChosen(
      { 
        registry: this.student.registry, 
        name: this.student.name,
        category: category });
  }
}
