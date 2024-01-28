package weare.testing;


import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import weare.models.UserPersonal;

public class PrivatePage extends BaseWeArePage{

    public PrivatePage(WebDriver driver, String urlKey) {
        super(driver, urlKey);
    }
    public void updatePersonalInformation(UserPersonal userPersonal) {

        actions.waitForElementVisible("weAre.privatePage.editButton");
        actions.clickElement("weAre.privatePage.editButton");
        actions.waitForElementVisible("weAre.privatePage.firstname");
        actions.typeValueInField(userPersonal.firstName, "weAre.privatePage.firstname");
        actions.waitForElementVisible("weAre.privatePage.lastname");
        actions.typeValueInField(userPersonal.lastName, "weAre.privatePage.lastname");
        actions.waitForElementVisible("weAre.privatePage.calendar");
        actions.typeValueInField("11/12/2023", "weAre.privatePage.calendar");
        actions.waitForElementVisible("weAre.privatePage.gender");
        actions.clickElement("weAre.privatePage.gender");
        actions.waitForElementVisible("weAre.privatePage.city");
        actions.clickElement("weAre.privatePage.city");
        actions.clickElement("weAre.privatePage.updateButtonOne");
    }

    public void updateWorkspaceBusiness() {

        actions.waitForElementVisible("weAre.privatePage.editButton");
        actions.clickElement("weAre.privatePage.editButton");
        actions.clickElement("weAre.privatePage.professionalCategory");
        actions.clickElement("weAre.privatePage.updateButtonTwo");
    }

    public void updateServiceAndWeeklyAvailability(String skill) {

        actions.waitForElementVisible("weAre.privatePage.editButton");
        actions.clickElement("weAre.privatePage.editButton");
        actions.waitForElementVisible("weAre.privatePage.skill");
        actions.typeValueInField(skill, "weAre.privatePage.skill");
        actions.waitForElementVisible("weAre.privatePage.weeklyAvailability");
        actions.typeValueInField("1.1", "weAre.privatePage.weeklyAvailability");
        actions.clickElement("weAre.privatePage.updateButtonThree");
    }

    public void updatePersonalInfoAndSafety() {

        actions.waitForElementVisible("weAre.privatePage.editButton");
        actions.clickElement("weAre.privatePage.editButton");
        actions.clickElement("weAre.privatePage.picturePrivacy");
        actions.clickElement("weAre.privatePage.updateButtonFour");
    }
    public void assertNamesArePresent(String firstName, String lastName) {
        try {
            actions.assertElementPresent(String.format("//p[text()='%s %s']", firstName, lastName));
        } catch (Exception e) {
            Assertions.fail("Names are not present.");
        }
    }
    public void assertProfessionPresent() {
        try {
            actions.assertElementPresent(String.format("//span[text()='Accountant']"));
        } catch (Exception e) {
            Assertions.fail("Profession is not present.");
        }
    }
}