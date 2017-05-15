export class ErrorServiceMock {

    public showError(input: string) {
        console.log("Error Service was triggered, translate Message: ", input);
    }
}
;