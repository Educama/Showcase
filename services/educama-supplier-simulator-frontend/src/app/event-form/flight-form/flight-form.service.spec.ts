import { TestBed, inject } from '@angular/core/testing';

import { FlightFormService } from './flight-form.service';

describe('FlightFormService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FlightFormService]
    });
  });

  it('should be created', inject([FlightFormService], (service: FlightFormService) => {
    expect(service).toBeTruthy();
  }));
});
