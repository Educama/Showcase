import { TestBed, inject } from "@angular/core/testing";
import { PostRobotService } from "./post-robot.service";

describe("PostRobotService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PostRobotService]
    });
  });

  it("should be created", inject([PostRobotService], (service: PostRobotService) => {
    expect(service).toBeTruthy();
  }));
});
