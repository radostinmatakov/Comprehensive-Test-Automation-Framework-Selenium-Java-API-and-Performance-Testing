package weare.testing;

import org.openqa.selenium.WebDriver;

public class LoginPage extends BaseWeArePage {

    public LoginPage(WebDriver driver) {
        super(driver, "login.page");
    }

    public static void loginUser(String username, String password) {

        actions.waitForElementVisible("weAre.loginPage.loginNavLink");
        actions.clickElement("weAre.loginPage.loginNavLink");

        actions.waitForElementVisible("weAre.loginPage.usernameField");
        actions.typeValueInField(username, "weAre.loginPage.usernameField");
        actions.waitForElementVisible("weAre.loginPage.passwordField");
        actions.typeValueInField(password, "weAre.loginPage.passwordField");

        actions.clickElement("weAre.loginPage.loginButton");

    }
}
