import {NgModule, ModuleWithProviders} from "@angular/core";
import {CommonModule} from "@angular/common";
import {
    ButtonModule,
    DataTableModule,
    DialogModule,
    DropdownModule,
    InputTextModule,
    InputTextareaModule,
    MenubarModule,
    PanelModule
} from "primeng/primeng";

@NgModule({
    imports: [CommonModule],
    exports: [ButtonModule, DataTableModule, DialogModule, DropdownModule,
        InputTextModule, InputTextareaModule, MenubarModule, PanelModule]
})
export class UIModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: UIModule,
            providers: []
        };
    }
}