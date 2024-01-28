package weare.testing;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import weare.models.UserPersonal;

public class UserPage extends BaseWeArePage {
    public UserPage(WebDriver driver, String urlKey) {
        super(driver, urlKey);
    }


    public void sendConnectionRequest() {
        actions.clickElement("weAre.userPage.connectButton");
    }

    public void approveConnectionRequest() {
        actions.clickElement("weAre.userPage.newRequestsButton");
        actions.waitForElementVisible("weAre.userPage.approveButton");
        actions.clickElement("weAre.userPage.approveButton");
    }

    public void disconnectFromUser() {
        actions.clickElement("weAre.userPage.disconnectButton");
    }


    public void assertHasOneFriend() {
        try {
            actions.assertElementPresent("weAre.userPage.oneFriend");
        } catch (Exception e) {
            Assertions.fail("User has no friends.");
        }
    }

    public void assertHasNoFriends() {
        try {
            actions.assertElementPresent("weAre.userPage.noFriends");
        } catch (Exception e) {
            Assertions.fail("User has friends.");
        }
    }
}
