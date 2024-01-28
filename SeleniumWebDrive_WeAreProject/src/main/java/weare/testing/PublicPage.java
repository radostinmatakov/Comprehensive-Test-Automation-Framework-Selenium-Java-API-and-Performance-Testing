package weare.testing;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import weare.models.UserPersonal;

public class PublicPage extends BaseWeArePage {

    public PublicPage(WebDriver driver, String urlKey) {
        super(driver, urlKey);
    }

    public void accessToPublicSection_ValidUser(UserPersonal userPersonal) {

        actions.waitForElementVisible("weAre.publicPage.searchUser");
        actions.typeValueInField(userPersonal.firstName, "weAre.publicPage.searchUser");
        actions.clickElement("weAre.publicPage.searchButton");
    }

    public void accessToPublicSection_InvalidUser(String firstName) {

        actions.waitForElementVisible("weAre.publicPage.searchUser");
        actions.typeValueInField(firstName, "weAre.publicPage.searchUser");
        actions.clickElement("weAre.publicPage.searchButton");
    }

    public void assertRegisterButton() {
        try {
            actions.assertElementPresent(String.format("(//a[text()='REGISTER'])[1]"));
        } catch (Exception e) {
            Assertions.fail("Register button is not present.");
        }
    }

    public void assertHomeButton() {
        try {
            actions.assertElementPresent(String.format("//a[@class='nav-link' and @href='/']"));
        } catch (Exception e) {
            Assertions.fail("Sign in button is not present.");
        }
    }

    public void assertInvalidProfilePresent() {
        try {
            actions.assertElementPresent(String.format("//h3[@class='mb-3 bread']"));
        } catch (Exception e) {
            Assertions.fail("Profile is not present.");
        }
    }

    public void assertValidProfilePresent() {
        try {
            actions.assertElementPresent(String.format("//h2[@class='mr-3 text-black']"));
        } catch (Exception e) {
            Assertions.fail("Profile is not present.");
        }
    }
}