package org.educama.acceptancetests.steps;

import net.thucydides.core.annotations.Step;
import java.util.List;
import org.educama.acceptancetests.pages.CustomerCapturePage;
import org.educama.acceptancetests.pages.CustomerListPage;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Steps for customer list.
 */
public class CustomersListSteps {

    CustomerListPage customersListPage;
    CustomerCapturePage customerCapturePage;

    @Step
    public void openCustomersListPage() {
        customersListPage.open();
    }

    @Step
    public void addNewCustomer(String customerName, String street, String houseNumber, String zipCode, String city, boolean save) {
        customersListPage.openCustomerCapturePage();
        customerCapturePage.createCustomer(customerName, street, houseNumber, zipCode, city, save);
    }

    @Step
    public void checksTheCustomersListData(boolean saved) {
        boolean isThere = false;
        final int drei = 3;
        final int vier = 4;
        final int fuenf = 5;
        String name = "Samsung";
        String street = "Beispielstrasse";
        String housenumber = "1";
        String postalcode = "1234";
        String city = "Seoul";
            List<String> customerListName = customersListPage.getCustomersList(1);
            for (int i = 0; i < customerListName.size(); i++) {
                if (customerListName.get(i).equals(name)) {
                    if (street.equals(customersListPage.getCustomersList(2).get(i)) && housenumber.equals(customersListPage.getCustomersList(drei).get(i)) && postalcode.equals(customersListPage.getCustomersList(vier).get(i)) && city.equals(customersListPage.getCustomersList(fuenf).get(i))) {
                       isThere = true;
                       //System.out.println(customersListPage.getCustomersList(0).get(i));
                    }
                }
            }
        if (saved) {
            assertThat(isThere, is(true));
        } else {
            assertThat(isThere, is(false));
        }
    }

    @Step
    public boolean checksTheCustomerListDataForApple() {
        boolean isThere = false;
        String name = "Apple";
            List<String> customerListName = customersListPage.getCustomersList(1);
            for (int i = 0; i < customerListName.size(); i++) {
                if (customerListName.get(i).equals(name)) {
                        isThere = true;
                    }
                }
            return isThere;
            }

    @Step
    public void openCustomerChangeView(String customerName) {
            customersListPage.openCustomerChangePage(customerName);
    }

    @Step
    public void changeCustomerData(String customerName, boolean save) {
        customersListPage.changeCustomerName(customerName, save);
    }

    @Step
    public void checksTheCustomerListDataForChangedAppleToSamsung(boolean saved) {
        boolean isThere = false;
        String name = "Apple";
            List<String> customerListName = customersListPage.getCustomersList(1);
            for (int i = 0; i < customerListName.size(); i++) {
                if (customerListName.get(i).equals(name)) {
                        isThere = true;
                    }
                }
            if (saved) {
               assertThat(isThere, is(false));
            } else {
               assertThat(isThere, is(true));
            }
    }
}
