package api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.*;
import utils.Serializer;

import static utils.Constants.APPLICATION_JSON;
import static utils.Endpoints.*;

public class UserController {

    public static Response registerUser(UserRegister user) {
        String bodyUserString = Serializer.convertObjectToJsonString(user);

        return RestAssured.given().baseUri(BASE_URL)
                .contentType(APPLICATION_JSON)
                .body(bodyUserString)
                .when()
                .post(USERS_ENDPOINT)
                .then().log().body().extract().response();
    }

    public static Response getUsers(Page page) {
        String bodyPageString = Serializer.convertObjectToJsonString(page);

        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(APPLICATION_JSON)
                .body(bodyPageString)
                .when()
                .post(GET_USERS_BY_NAME_ENDPOINT);
    }

    public static Response getUserByName(Cookies cookies, SearchUser user) {
        String bodyGetUserByName = Serializer.convertObjectToJsonString(user);
        return RestAssured.given()
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .body(bodyGetUserByName)
                .when()
                .post(GET_USERS_BY_NAME_ENDPOINT);
    }

    public static Response updatePersonalProfile(Cookies cookies, UserPersonal userPersonal, int currentUserId) {
        String bodyUpdatedPersonalProfileString = Serializer.convertObjectToJsonString(userPersonal);
        return RestAssured.given()
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .pathParam("currentUserId", currentUserId)
                .body(bodyUpdatedPersonalProfileString)
                .when()
                .post("/api/users/auth/{currentUserId}/personal");
    }

    public static Response updateExpertiseProfile(Cookies cookies, ExpertiseProfile expertiseProfile, int currentUserId) {
        String bodyExpertiseProfileString = Serializer.convertObjectToJsonString(expertiseProfile);
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(APPLICATION_JSON)
                .cookies(cookies)
                .body(bodyExpertiseProfileString)
                .when()
//                .put("/api/users/expertise/" + currentUserId);
                .post(UPDATE_ENDPOINT + currentUserId + "/expertise");
    }

    public static Response getUserById(String currentUsername, int currentUserId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(APPLICATION_JSON)
                .queryParam("principal", currentUsername)
                .when()
                .get(UPDATE_ENDPOINT + currentUserId);
    }

    public static Response getProfilePosts(Cookies cookies, int currentUserId) {
        Page page = new Page();
        page.size = 10;
        String bodyPageString = Serializer.convertObjectToJsonString(page);
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .pathParam("currentUserId", currentUserId)
                .body(bodyPageString)
                .when()
                .get(GET_PROFILE_POSTS_ENDPOINT);
    }
}
