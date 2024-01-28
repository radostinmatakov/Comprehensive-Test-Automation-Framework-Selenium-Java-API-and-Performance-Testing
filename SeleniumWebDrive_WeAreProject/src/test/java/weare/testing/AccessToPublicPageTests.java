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


public class AccessToPublicPageTests extends BaseTestSetup {
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
    public void personalInformationUpdated_When_ClickEditProfile() {

        UserPersonal userPersonal = ModelGenerator.generateUserPersonalModel();
        privatePage.updatePersonalInformation(userPersonal);
        privatePage.navigateToPage();
        privatePage.assertNamesArePresent(userPersonal.firstName, userPersonal.lastName);
    }

    @Test
    public void accessToPublicPage_When_UserClicksHomeButton() {

        publicPage = new PublicPage(driver, String.format("home.page"));
        publicPage.navigateToPage();
        publicPage.assertRegisterButton();
        publicPage.assertHomeButton();

    }

    @Test
    public void profileFoundByUsername_When_UserEntersNameInSearch() {
        UserPersonal userPersonal = ModelGenerator.generateUserPersonalModel();

        publicPage = new PublicPage(driver, String.format("home.page"));
        publicPage.navigateToPage();
        publicPage.accessToPublicSection_ValidUser(userPersonal);
        publicPage.assertValidProfilePresent();
    }

    @Test
    public void profileNotDisplayed_When_TrySearchingWithInvalidUsername() {
        publicPage = new PublicPage(driver, String.format("home.page"));
        publicPage.navigateToPage();

        String username = "Petar";
        publicPage.accessToPublicSection_InvalidUser(username);
        publicPage.assertInvalidProfilePresent();
    }
}

