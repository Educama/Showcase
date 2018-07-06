import {
  Component,
  OnDestroy,
  OnInit
} from "@angular/core";
import {
  DomSanitizer,
  SafeResourceUrl
} from "@angular/platform-browser";
import {
  CONSTANTS_SHARED,
  PostRobotData,
  PostRobotService
} from "frontend-shared";

@Component({
  selector: "educama-case-ui-page",
  templateUrl: "./case-ui-page.component.html",
  styleUrls: [ "./case-ui-page.component.scss" ]
})
export class CaseUiPageComponent implements OnInit, OnDestroy {
  dynamicFrameUrl: SafeResourceUrl;

  private dynamicFrameUrlListener: any;

  private readonly EVENT_DOMAIN: string = "http://localhost:4200";

  constructor(private postRobotService: PostRobotService,
              private domSanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.dynamicFrameUrl = this.domSanitizer.bypassSecurityTrustResourceUrl("http://localhost:4201/");
    this.dynamicFrameUrlListener =
      this.postRobotService.getInstance().on(
        CONSTANTS_SHARED.POST_ROBOT.EVENT_NAME,
        { domain: this.EVENT_DOMAIN },
        (event: PostRobotData<any>) => {
          this.dynamicFrameUrl = this.domSanitizer.bypassSecurityTrustResourceUrl(event.data.url);
        }
      );
  }

  ngOnDestroy(): void {
    this.dynamicFrameUrlListener.cancel();
  }
}
