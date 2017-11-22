import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoListComponent } from './cargo-list.component';

describe('CargoListComponent', () => {
  let component: CargoListComponent;
  let fixture: ComponentFixture<CargoListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CargoListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
