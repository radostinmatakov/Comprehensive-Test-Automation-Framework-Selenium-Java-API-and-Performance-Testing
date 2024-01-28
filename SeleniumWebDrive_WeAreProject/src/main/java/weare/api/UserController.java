package weare.api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utils.Serializer;
import weare.models.Page;
import weare.models.UserRegister;

import static utils.Endpoints.*;

public class UserController {

    public static Response registerUser(UserRegister user) {
        String bodyUserString = Serializer.convertObjectToJsonString(user);

        return RestAssured.given().baseUri(BASE_URL)
                .contentType("application/json")
                .body(bodyUserString)
                .when()
                .post(USERS_ENDPOINT)
                .then().log().body().extract().response();
    }


    public static Response getProfilePosts(Cookies cookies, int currentUserId) {
        Page page = new Page();
        page.size = 10;
        String bodyPageString = Serializer.convertObjectToJsonString(page);
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("currentUserId", currentUserId)
                .body(bodyPageString)
                .when()
                .get(GET_PROFILE_POSTS_ENDPOINT);
    }

    public static Cookies authenticatedAndFetchCookies(String username, String password) {
        RestAssured.baseURI = BASE_URL;
        Response response = RestAssured.given()
                .contentType("multipart/form-data")
                .multiPart("username", username)
                .multiPart("password", password)
                .when()
                .post(AUTHENTICATE_ENDPOINT);

        return response.detailedCookies();
    }
}
