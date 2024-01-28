package api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.Post;
import utils.Serializer;

import static utils.Constants.APPLICATION_JSON;
import static utils.Constants.POST_ID;
import static utils.Endpoints.*;

public class PostController {

    public static Response createPost(Cookies cookies, Post post) {
        String bodyPostString = Serializer.convertObjectToJsonString(post);
        return RestAssured.given()
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .body(bodyPostString)
                .when()
                .post(CREATЕ_POST_ENDPOINT);
    }

    public static Response deletePost(Cookies cookies, int postId) {
        return RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .queryParam("postId", postId)
                .when()
                .delete(DELETE_POST_ENDPOINT);
    }

    public static Response editPost(Cookies cookies, Post editedPost) {
        String bodyEditPostString = Serializer.convertObjectToJsonString(editedPost);
        return RestAssured.given()
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .queryParam("postId", editedPost.postId)
                .body(bodyEditPostString)
                .when()
                .put(EDIT_POST_ENDPOINT);
    }

    public static Response getNewsFeed(Cookies cookies) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .queryParam("sorted", true)
                .log().headers()
                .when()
                .get(GET_ALL_POSTS_ENDPOINT);
    }

    public static Response likeAndDislikePost(Cookies cookies, int postId) {
        return RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .queryParam(POST_ID, postId)
                .when()
                .post(LIKE_POST_ENDPOINT);
    }
}
