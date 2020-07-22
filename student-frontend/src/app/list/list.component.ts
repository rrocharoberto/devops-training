import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { StudentService } from '../student.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit, OnDestroy {
  students = [];
  activatedRoute: ActivatedRoute;
  swService: StudentService;
  loadedCategory = 'all';
  subscription;

  constructor(activatedRoute: ActivatedRoute, swService: StudentService) {
    this.activatedRoute = activatedRoute;
    this.swService = swService;
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      (params) => {
        this.students = this.swService.getStudents(params.category);
        this.loadedCategory = params.category;
      }
    );
    this.subscription = this.swService.studentsChanged.subscribe(
      () => {
        this.students = this.swService.getStudents(this.loadedCategory);
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
