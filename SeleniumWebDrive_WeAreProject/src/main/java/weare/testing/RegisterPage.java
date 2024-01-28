package weare.testing;

import org.openqa.selenium.WebDriver;

public class RegisterPage extends BaseWeArePage {

    public RegisterPage(WebDriver driver) {
        super(driver, "home.page");
    }


    public void userRegister(String username, String password, String email) {
        navigateToPage();
        assertNavigatedUrl();

        actions.waitForElementVisible("weAre.loginPage.registerLink");
        actions.clickElement("weAre.loginPage.registerLink");

        actions.waitForElementVisible("weAre.registrationPage.nameField");
        actions.typeValueInField(username, "weAre.registrationPage.nameField");

        actions.waitForElementVisible("weAre.registrationPage.emailField");
        actions.typeValueInField(email, "weAre.registrationPage.emailField");

        actions.waitForElementVisible("weAre.registrationPage.passwordField");
        actions.typeValueInField(password, "weAre.registrationPage.passwordField");

        actions.waitForElementVisible("weAre.registrationPage.confirmField");
        actions.typeValueInField(password, "weAre.registrationPage.confirmField");

        actions.waitForElementClickable("weAre.registrationPage.submitButton");
        actions.clickElement("weAre.registrationPage.submitButton");

    }

    public void userRegisterWithProfessionalSelection(String username, String password, String email) {
        navigateToPage();
        assertNavigatedUrl();

        actions.waitForElementVisible("weAre.loginPage.registerLink");
        actions.clickElement("weAre.loginPage.registerLink");

        actions.waitForElementVisible("weAre.registrationPage.nameField");
        actions.typeValueInField(username, "weAre.registrationPage.nameField");

        actions.waitForElementVisible("weAre.registrationPage.emailField");
        actions.typeValueInField(email, "weAre.registrationPage.emailField");

        actions.waitForElementVisible("weAre.registrationPage.passwordField");
        actions.typeValueInField(password, "weAre.registrationPage.passwordField");

        actions.waitForElementVisible("weAre.registrationPage.confirmField");
        actions.typeValueInField(password, "weAre.registrationPage.confirmField");

        actions.waitForElementClickable("weAre.registrationPage.categoryDropdown");
        actions.clickElement("weAre.registrationPage.categoryDropdown");

        actions.clickElement("weAre.registrationPage.selectAccountant");

        actions.waitForElementClickable("weAre.registrationPage.submitButton");
        actions.clickElement("weAre.registrationPage.submitButton");
    }

}

