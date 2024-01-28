package weare.api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utils.Serializer;
import weare.models.PostModel;

import static utils.Endpoints.*;

public class PostController {

    public static Response createPost(Cookies cookies, PostModel post) {
        String bodyPostString = Serializer.convertObjectToJsonString(post);
        return RestAssured.given()
                .cookies(cookies)
                .contentType("application/json")
                .body(bodyPostString)
                .when()
                .post(CREATE_POST_ENDPOINT);
    }


    public static Response likeAndDislikePost(Cookies cookies, int postId) {
        return RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .contentType("application/json")
                .queryParam("postId", postId)
                .when()
                .post(LIKE_POST_ENDPOINT);
    }
}
