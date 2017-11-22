import { TestBed, inject } from '@angular/core/testing';

import { CargoFormService } from './cargo-form.service';

describe('CargoFormService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CargoFormService]
    });
  });

  it('should be created', inject([CargoFormService], (service: CargoFormService) => {
    expect(service).toBeTruthy();
  }));
});
