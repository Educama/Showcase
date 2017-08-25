import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { EventFormComponent } from './event-form/event-form.component';
import { FlightFormComponent } from './event-form/flight-form/flight-form.component';
import { FlightFormService } from './event-form/flight-form/flight-form.service';
import { CargoFormComponent } from './event-form/cargo-form/cargo-form.component';
import { CargoFormService } from './event-form/cargo-form/cargo-form.service';
import { EventListComponent } from './event-list/event-list.component';
import { CargoListComponent } from './event-list/cargo-list/cargo-list.component';
import { FlightListComponent } from './event-list/flight-list/flight-list.component';

const appRoutes: Routes = [
  { path: '', component: EventListComponent },
  { path: 'form', component: EventFormComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    EventFormComponent,
    FlightFormComponent,
    CargoFormComponent,
    EventListComponent,
    CargoListComponent,
    FlightListComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }
    ),
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  exports: [
    EventFormComponent,
    EventListComponent,
    FlightListComponent,
    CargoListComponent
  ],
  providers: [
    FlightFormService,
    CargoFormService
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
