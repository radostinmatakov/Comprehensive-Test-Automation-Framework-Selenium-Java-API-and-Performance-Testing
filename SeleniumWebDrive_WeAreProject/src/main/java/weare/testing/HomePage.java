package weare.testing;

import org.openqa.selenium.WebDriver;

public class HomePage extends BaseWeArePage{

    public HomePage(WebDriver driver) {
        super(driver, "home.page");
    }

    public void logoutUser(){
        actions.waitForElementVisible("weAre.homeButton");
        actions.clickElement("weAre.homeButton");
        actions.waitForElementVisible("weAre.logoutButton");
        actions.clickElement("weAre.logoutButton");
    }
}
