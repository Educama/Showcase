import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { CaseUiPageComponent } from "./case-ui-page.component";

describe("CaseUiPageComponent", () => {
  let component: CaseUiPageComponent;
  let fixture: ComponentFixture<CaseUiPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaseUiPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaseUiPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should be created", () => {
    expect(component).toBeTruthy();
  });
});
