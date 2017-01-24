// angular modules
import {NgModule} from "@angular/core";
import {APP_BASE_HREF} from "@angular/common";
import {BrowserModule} from "@angular/platform-browser";

// ngrx modules
import {StoreModule} from "@ngrx/store";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";

// global app parts
import {AppComponent} from "./app.component";
import {ROUTING, APP_ROUTING_PROVIDERS} from "./app.routes";
import {REDUCER, INITIAL_STATE} from "./app.reducers";

// shared module and feature modules
import {SharedModule} from "./common/shared.module";
import {CustomerModule} from "./customer/customer.module";
import {NavigationModule} from "./navigation/navigation.module";
import {ShipmentModule} from "./shipment/shipment.module";

@NgModule({
    imports: [
        BrowserModule,
        CustomerModule,
        NavigationModule,
        ROUTING,
        SharedModule.forRoot(),
        ShipmentModule,
        StoreModule.provideStore(REDUCER, INITIAL_STATE),
        StoreDevtoolsModule.instrumentOnlyWithExtension()
    ],
    declarations: [AppComponent],
    providers: [
        {provide: APP_BASE_HREF, useValue: "/"},
        APP_ROUTING_PROVIDERS
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}