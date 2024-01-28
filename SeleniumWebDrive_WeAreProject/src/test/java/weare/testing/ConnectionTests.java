package weare.testing;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import utils.ModelGenerator;
import weare.api.ConnectionController;
import weare.api.UserController;
import weare.models.ConnectionModel;
import weare.models.SendRequest;
import weare.models.UserRegister;

public class ConnectionTests extends BaseTestSetup {

    protected static UserRegister firstUser;
    protected static int firstUserId;
    protected static Cookies firstUserCookies;
    protected static UserRegister secondUser;
    protected static int secondUserId;
    protected static Cookies secondUserCookies;
    protected static ConnectionModel[] connectionsList;

    @BeforeAll
    public static void setup() {

    }

    @BeforeEach
    public void beforeEach() {
        firstUser = ModelGenerator.generateUserRegisterModel();
        secondUser = ModelGenerator.generateUserRegisterModel();

        Response responseFirstUser = UserController.registerUser(firstUser);
        firstUserCookies = UserController.authenticatedAndFetchCookies(firstUser.username, firstUser.password);
        firstUserId = Integer.parseInt(responseFirstUser.asString().split(" ")[6]);

        Response responseSecondUser = UserController.registerUser(secondUser);
        secondUserCookies = UserController.authenticatedAndFetchCookies(secondUser.username, secondUser.password);
        secondUserId = Integer.parseInt(responseSecondUser.asString().split(" ")[6]);
        loginPage.navigateToPage();
    }

    @AfterEach
    public void afterEach() {
        homePage.navigateToPage();
        homePage.logoutUser();
    }

    @Test
    public void connectionRequestSent_When_UserClicksOnSendRequestButton() {
        LoginPage.loginUser(firstUser.username, firstUser.password);
        LoginPage.assertElementPresent("weAre.loginPage.logoutLink");

        userPage = new UserPage(driver, String.format("http://localhost:8081/auth/users/%d/profile", secondUserId));

        connectionsList = ConnectionController.getUserRequests(secondUserCookies, secondUserId).as(ConnectionModel[].class);
        Assertions.assertEquals(0, connectionsList.length, "Connection list is not empty");

        userPage.navigateToPage();
        userPage.sendConnectionRequest();
        userPage.assertElementPresent("weAre.userPage.connectionRequestSentMessage");

        connectionsList = ConnectionController.getUserRequests(secondUserCookies, secondUserId).as(ConnectionModel[].class);
        Assertions.assertEquals(1, connectionsList.length, "Connection list is empty");
    }

    @Test
    public void connectionRequestApproved_When_UserClicksOnApproveButton() {
        SendRequest sendRequestToUser = ModelGenerator.generateSendRequestModel(secondUserId, secondUser.username);
        ConnectionController.sendRequest(sendRequestToUser, firstUserCookies, firstUser.username);
        connectionsList = ConnectionController.getUserRequests(secondUserCookies, secondUserId).as(ConnectionModel[].class);
        Assertions.assertEquals(1, connectionsList.length, "Connection list is empty");
        Assertions.assertEquals(connectionsList[0].approved, false, "Connection is already approved");


        LoginPage.loginUser(secondUser.username, secondUser.password);
        LoginPage.assertElementPresent("weAre.loginPage.logoutLink");

        userPage = new UserPage(driver, String.format("http://localhost:8081/auth/users/%d/profile", secondUserId));
        userPage.navigateToPage();
        userPage.approveConnectionRequest();

        userPage.navigateToPage();
        userPage.assertHasOneFriend();

        connectionsList = ConnectionController.getUserRequests(secondUserCookies, secondUserId).as(ConnectionModel[].class);
        Assertions.assertEquals(0, connectionsList.length, "Connection list is not empty");
    }

    ;

    @Test
    public void connectionRemoved_When_UserRemovesConnection() {
        SendRequest sendRequestToUser = ModelGenerator.generateSendRequestModel(secondUserId, secondUser.username);
        ConnectionController.sendRequest(sendRequestToUser, firstUserCookies, firstUser.username);
        connectionsList = ConnectionController.getUserRequests(secondUserCookies, secondUserId).as(ConnectionModel[].class);
        Assertions.assertEquals(1, connectionsList.length, "Connection list is empty");
        Assertions.assertEquals(connectionsList[0].approved, false, "Connection is already approved");

        ConnectionController.approveRequest(secondUserCookies, secondUserId, connectionsList[0].id);

        LoginPage.loginUser(firstUser.username, firstUser.password);
        LoginPage.assertElementPresent("weAre.loginPage.logoutLink");

        userPage = new UserPage(driver, String.format("http://localhost:8081/auth/users/%d/profile", secondUserId));
        userPage.navigateToPage();
        userPage.disconnectFromUser();
        userPage.navigateToPage();
        userPage.assertHasNoFriends();
    }

}
