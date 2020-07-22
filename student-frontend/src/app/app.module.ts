import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TabsComponent } from './tabs/tabs.component';
import { ListComponent } from './list/list.component';
import { ItemComponent } from './item/item.component';
import { StudentService } from './student.service';
import { CreateStudentComponent } from './create-student/create-student.component';
import { HeaderComponent } from './header/header.component';

const routes = [
  { path: 'students', component: TabsComponent, children: [
    { path: '', redirectTo: 'all', pathMatch: 'full' },
    { path: ':category', component: ListComponent }
  ] },
  { path: 'new-student', component: CreateStudentComponent },
  { path: '**', redirectTo: '/students' }
];

@NgModule({
  declarations: [
    AppComponent,
    TabsComponent,
    ListComponent,
    ItemComponent,
    CreateStudentComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(routes),
    HttpModule
  ],
  providers: [StudentService],
  bootstrap: [AppComponent]
})
export class AppModule { }
