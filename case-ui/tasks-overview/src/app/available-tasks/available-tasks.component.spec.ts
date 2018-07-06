import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { AvailableTasksComponent } from "./available-tasks.component";

describe("AvailableTasksComponent", () => {
  let component: AvailableTasksComponent;
  let fixture: ComponentFixture<AvailableTasksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableTasksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailableTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
