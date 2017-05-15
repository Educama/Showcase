import { TestBed, inject } from '@angular/core/testing';

import { EventFormService } from './event-form.service';

describe('EventFormService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EventFormService]
    });
  });

  it('should be created', inject([EventFormService], (service: EventFormService) => {
    expect(service).toBeTruthy();
  }));
});
