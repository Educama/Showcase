import {Component, OnInit} from "@angular/core";
import {MenuItem} from "primeng/primeng";
import {TranslateService} from "@ngx-translate/core";
import {TranslationNotifierService} from "../../shared/translation/services/translation-notifier.service";

@Component({
    selector: "educama-navigation-bar",
    templateUrl: "./navigation-bar.component.html",
    styleUrls: ["./navigation-bar.component.scss"]
})
export class NavigationBarComponent {

    public items: MenuItem[];

    constructor(private _translateService: TranslateService,
                private _translationNotifierService: TranslationNotifierService) {

        // required to only load translations once the i18n file has been loaded
        this._translationNotifierService.translationsLoaded$.subscribe(
            () => {
                this.initMenuItems();
            }
        );

    }

    public initMenuItems() {

        this.items = [
            {
                label: this._translateService.instant("NAVIGATION-BAR-COMPONENT_MENU-ITEM_SHIPMENT-LIST"),
                routerLink: ["/shipments"]
            },
            {
                label: this._translateService.instant("NAVIGATION-BAR-COMPONENT_MENU-ITEM_TASK-LIST"),
                routerLink: ["/tasks/active"]
            },
            {
                label: this._translateService.instant("NAVIGATION-BAR-COMPONENT_MENU-ITEM_MASTER-DATA"),
                items: [
                    {
                        label: this._translateService.instant("NAVIGATION-BAR-COMPONENT_MENU-ITEM_CUSTOMER-LIST"),
                        routerLink: ["/customers"]
                    },
                    {
                        label: this._translateService.instant("NAVIGATION-BAR-COMPONENT_MENU-ITEM_AIRPORTS-LIST"),
                        routerLink: ["/airports"]
                    },
                    {
                      label: this._translateService.instant("NAVIGATION-BAR-COMPONENT_MENU-ITEM_AIRLINES-LIST"),
                      routerLink: ["/airlines"]
                    }
                ]
            }
        ];
    }

}
