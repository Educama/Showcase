import {Component, Output, EventEmitter} from "@angular/core";
import {HTML_TEMPLATE} from "./create_instance_modal.component.html";

@Component({
    selector: 'create-instance-modal',
    template: HTML_TEMPLATE
})
export class CreateInstanceModalComponent {

    @Output() public modalOutput: EventEmitter<any> = new EventEmitter();

    isVisible: boolean;
    validFieldValues: boolean;

    showModal() {
        this.isVisible = true;
        this.validFieldValues = true;
    }

    submit(customer: string, pickupAddress: string, deliveryAddress: string) {
        if (!_checkFieldValues()) {
            this.validFieldValues = false;
            return;
        }
        this.close();
        this.modalOutput.emit({
            customer: customer,
            pickupAddress: pickupAddress,
            deliveryAddress: deliveryAddress

        });

        function _checkFieldValues() {
            if (customer.trim().length === 0) return false;
            if (pickupAddress.trim().length === 0) return false;
             if (deliveryAddress.trim().length === 0) return false;
            return true;
        }
    }

    close() {
        this.isVisible = false;
        this.modalOutput.emit(null);
    }
}
