import {Routes, RouterModule} from "@angular/router";
import {CaseUiPageComponent} from "../../case-ui-page/case-ui-page.component";

/*
 * Router configuration for the component task
 */
const CASE_UI_ROUTES: Routes = [
    {
        path: "case-ui",
        component: CaseUiPageComponent
    }
];

export const CASE_UI_ROUTING = RouterModule.forChild(CASE_UI_ROUTES);
