package base;

import api.UserController;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import utils.ModelGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.testng.Assert.assertEquals;
import static utils.Endpoints.AUTHENTICATE_ENDPOINT;
import static utils.Endpoints.BASE_URL;

public class BaseTestSetup {

    public static Cookies cookies;
    public static String currentUsername;
    public static String currentEmail;
    public static int currentUserId;
    public static String currentPassword;
    public static int postId;
    public static int userId;
    public static int commentId;
    public static Comment createdComment;
    public static String senderUsername;
    public static String receiverUsername;
    public static String receiverEmail;
    public static int senderUserId;
    public static int receiverUserId;
    public static Post createdPost;
    public static Post editPost;

    public static Boolean isRegistered = false;
    public static Boolean isRegisteredTwoUsers = false;
    public static Boolean isDeletedPost = true;
    public static UserRegister userToRegister;
    public static Post createPost;
    public static Comment createComment;
    public static Boolean isCommentDeleted = true;
    public static Skill skillToCreated;
    public static Skill createdSkill;
    public static UserProfile currentUserProfile;
    public static UserPersonal currentUserPersonalProfile;


    public static void isResponse200(Response response) {
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SC_OK, "Incorrect status code.");
        if (statusCode == SC_OK) {
            System.out.println("The response is 200");
        }
    }

    @BeforeClass
    public void setup() throws InterruptedException {
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        if (userToRegister == null) {
            userToRegister = ModelGenerator.generateUserRegisterModel();
        }
    }


    public void register(UserRegister userToRegister) {
        currentUsername = userToRegister.username;
        currentEmail = userToRegister.email;
        currentPassword = userToRegister.password;
        Response response = UserController.registerUser(userToRegister);
        cookies = authenticateAndFetchCookies(currentUsername, currentEmail);

        isResponse200(response);
        isRegistered = true;
        String[] responseString = response.asString().split(" ");
        Assert.assertEquals(responseString[3], currentUsername);

        currentUserId = Integer.parseInt(responseString[6]);
        System.out.println("Registered successfully!");
        System.out.println("Username: " + currentUsername);
        System.out.println("ID: " + currentUserId);
    }


    public Cookies authenticateAndFetchCookies() {
        RestAssured.baseURI = BASE_URL;

        Response response = RestAssured.given()
                .contentType("multipart/form-data")
                .multiPart("username", currentUsername)
                .multiPart("password", currentPassword)
                .when()
                .post(AUTHENTICATE_ENDPOINT);

        cookies = response.detailedCookies();
        return cookies;
    }

    public Cookies authenticateAndFetchCookies(String username, String password) {
        RestAssured.baseURI = BASE_URL;

        Response response = RestAssured.given()
                .contentType("multipart/form-data")
                .multiPart("username", username)
                .multiPart("password", password)
                .when()
                .post(AUTHENTICATE_ENDPOINT);

        cookies = response.detailedCookies();
        return cookies;
    }
}