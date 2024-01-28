package weare.testing;


public class AdminPage extends BaseWeArePage {


    public AdminPage() {
        super(driver, "home.page");


    }


    public void adminEditPersonalProfile() {
        actions.waitForElementClickable("weAre.adminPage.gotoAdminZone");
        actions.clickElement("weAre.adminPage.gotoAdminZone");

        actions.waitForElementClickable("weAre.userPage.viewUsersButton");
        actions.clickElement("weAre.userPage.viewUsersButton");

        actions.waitForElementClickable("weAre.userPage.seeProfileButton");
        actions.clickElement("weAre.userPage.seeProfileButton");

        actions.waitForElementVisible("weAre.privatePage.editButton");
        actions.clickElement("weAre.privatePage.editButton");
        actions.waitForElementVisible("weAre.privatePage.firstname");
        actions.typeValueInField("Ana", "weAre.privatePage.firstname");
        actions.waitForElementVisible("weAre.privatePage.lastname");
        actions.typeValueInField("Stoyanova", "weAre.privatePage.lastname");
        actions.waitForElementVisible("weAre.privatePage.calendar");
        actions.typeValueInField("11/12/2023", "weAre.privatePage.calendar");
        actions.waitForElementVisible("weAre.privatePage.gender");
        actions.clickElement("weAre.privatePage.gender");
        actions.waitForElementVisible("weAre.privatePage.city");
        actions.clickElement("weAre.privatePage.city");
        actions.clickElement("weAre.privatePage.updateButtonOne");


    }
}




