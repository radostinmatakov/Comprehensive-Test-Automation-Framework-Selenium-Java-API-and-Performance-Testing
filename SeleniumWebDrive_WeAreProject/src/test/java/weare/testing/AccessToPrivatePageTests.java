package weare.testing;


import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ModelGenerator;
import weare.api.UserController;
import weare.models.UserPersonal;
import weare.models.UserRegister;

import static utils.Endpoints.UPDATE_PERSONAL_ENDPOINT;


public class AccessToPrivatePageTests extends BaseTestSetup {
    static UserRegister userToRegister;
    static int registeredUserId;
    static Cookies cookies;

    @BeforeAll
    public static void setup() {
        userToRegister = ModelGenerator.generateUserRegisterModel();
        Response response = UserController.registerUser(userToRegister);
        cookies = UserController.authenticatedAndFetchCookies(userToRegister.username, userToRegister.password);

        registeredUserId = Integer.parseInt(response.asString().split(" ")[6]);
        loginPage.navigateToPage();
        LoginPage.loginUser(userToRegister.username, userToRegister.password);
        LoginPage.assertElementPresent("weAre.loginPage.logoutLink");

        privatePage = new PrivatePage(driver, String.format(UPDATE_PERSONAL_ENDPOINT, registeredUserId));
        privatePage.navigateToPage();
    }

    @Test
    public void userProfileUpdatedWithNewData_When_UserInitiatesUpdate() {
        UserPersonal userPersonal = ModelGenerator.generateUserPersonalModel();
        privatePage.updatePersonalInformation(userPersonal);
        privatePage.navigateToPage();
        privatePage.assertNamesArePresent(userPersonal.firstName, userPersonal.lastName);
    }

    @Test
    public void workspaceBusinessUpdated_When_UserEditsBusinessInformation() {
        privatePage.updateWorkspaceBusiness();
        privatePage.navigateToPage();
        privatePage.assertProfessionPresent();
    }

    @Test
    public void serviceAndWeeklyAvailabilityUpdated_When_UserEditsBusinessInformation() {
        String skill = "Quality Assurance";
        privatePage.updateServiceAndWeeklyAvailability(skill);
        privatePage.navigateToPage();
        privatePage.assertProfessionPresent();
    }

    @Test
    public void personalInfoAndSafetyUpdated_When_UserEditsInformation() {
        privatePage.updatePersonalInfoAndSafety();
        privatePage.navigateToPage();
    }
}