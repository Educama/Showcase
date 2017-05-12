import {NgModule, ModuleWithProviders} from "@angular/core";
import {CommonModule} from "@angular/common";
import {
    AutoCompleteModule,
    ButtonModule,
    CheckboxModule,
    RadioButtonModule,
    DataTableModule,
    DialogModule,
    DropdownModule,
    InputTextModule,
    InputTextareaModule,
    MenubarModule,
    PanelModule,
    TabViewModule,
} from "primeng/primeng";

@NgModule({
    imports: [CommonModule],
    exports: [AutoCompleteModule, ButtonModule, CheckboxModule, RadioButtonModule, DataTableModule, DialogModule, DropdownModule,
        InputTextModule, InputTextareaModule, MenubarModule, PanelModule, TabViewModule]
})
export class UIModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: UIModule,
            providers: []
        };
    }
}