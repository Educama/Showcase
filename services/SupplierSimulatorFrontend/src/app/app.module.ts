import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { FlightFormComponent } from './flight-form.component';
import { FlightFormService } from './flight-form.service';
import { CargoFormComponent } from './cargo-form.component';
import { CargoFormService } from './cargo-form.service';

@NgModule({
  declarations: [
    AppComponent,
    FlightFormComponent,
    CargoFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [ 
    FlightFormService,
    CargoFormService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
