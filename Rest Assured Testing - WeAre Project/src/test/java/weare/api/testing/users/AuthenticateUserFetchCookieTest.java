package weare.api.testing.users;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.*;
import static utils.Endpoints.AUTHENTICATE_ENDPOINT;
import static utils.Endpoints.BASE_URL;

public class AuthenticateUserFetchCookieTest extends BaseUserSetup {


    @BeforeClass
    public void setup() {
        if (isRegistered == false) {
            userToRegister = ModelGenerator.generateUserRegisterModel();
            register(userToRegister);
            isRegistered = true;
        }
    }

    @Test
    public void AuthenticateAndFetchCookies() {
        RestAssured.baseURI = BASE_URL;
        Response response = RestAssured.given()
                .contentType(CONTENT_TYPE_MULTIPART_FORM_DATA)
                .multiPart(FIELD_USERNAME, userToRegister.username)
                .multiPart(FIELD_PASSWORD, userToRegister.password)
                .when()
                .post(AUTHENTICATE_ENDPOINT);

        cookies = response.detailedCookies();
        int statusCodeAuthentication = response.getStatusCode();
        Assert.assertEquals(statusCodeAuthentication, 302);
        System.out.println(String.format(STATUS_CODE_MESSAGE_FORMAT, statusCodeAuthentication));
    }
}





