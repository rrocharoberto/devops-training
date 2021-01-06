import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType, RequestOptions, Headers } from '@angular/http';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/map';

@Injectable()
export class StudentService {

  URL_BASE = "/api/";
  
  private students = [];
  studentsChanged = new Subject<void>();
  http: Http;

  constructor(http: Http) {
    this.http = http;
  }

  fetchStudents() {
    this.http.get(this.URL_BASE + 'students')
      .map((response: Response) => {
        const data = response.json();
        console.log(data);
        const extractedStudents = data._embedded.students;
        const students = extractedStudents.map((stud) => {
          return {registry: stud.registry, name: stud.name, category: ''};
        });
        return students;
      })
      .subscribe(
        (data) => {
          console.log(data);
          this.students = data;
          this.studentsChanged.next();
        }
      );
  }

  sendStudent (newStudent) {
    const options = new RequestOptions({
      headers: new Headers({
        'Content-Type':  'application/json'
      }),
      responseType: ResponseContentType.Json,
//      params: params,
      withCredentials: false
    });

    return this.http.post(this.URL_BASE + 'students', newStudent, options)
    .subscribe(
      (data) => {
        console.log("sendStudent to backend: " + data);
        this.students.push(newStudent);
        this.studentsChanged.next();
      }
    );
  }

  getStudents(chosenList) {
    console.log("Category chosen: " + chosenList);
    if (chosenList === 'all') {
      return this.students.slice();
    }
    return this.students.filter((stud) => {
      return stud.category === chosenList;
    })
  }

  onCategoryChosen(studentInfo) {
    const pos = this.students.findIndex((stud) => {
      return stud.registry === studentInfo.registry;
    })
    this.students[pos].name = studentInfo.name;
    this.students[pos].category = studentInfo.category;
    this.studentsChanged.next();
    console.log('Changed category of student of ' + studentInfo.name + ', to new category: ' + studentInfo.category);
  }

  addStudent(registry, name, category) {
    const pos = this.students.findIndex((stud) => {
      return stud.registry === registry;
    })
    if (pos !== -1) {
      return;
    }
    const newStudent = {registry: registry, name: name, category: category};

    this.sendStudent(newStudent);
  }
}
