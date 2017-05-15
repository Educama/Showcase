import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { EventFormComponent } from './event-form.component';
import { EventFormService } from './event-form.service';

@NgModule({
  declarations: [
    AppComponent,
    EventFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [ EventFormService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
