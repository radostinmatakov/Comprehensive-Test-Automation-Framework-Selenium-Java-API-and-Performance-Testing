package api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.Comment;
import utils.Serializer;

import static utils.Constants.*;
import static utils.Endpoints.*;

public class CommentController {

    public static Response createComment(Cookies cookies, Comment createComment) {
        String bodyCommentString = Serializer.convertObjectToJsonString(createComment);
        return RestAssured.given()
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .body(bodyCommentString)
                .when()
                .post(CREATЕ_COMMENT_ENDPOINT);
    }

    public static Response deleteComment(Cookies cookies, int commentId) {
        return RestAssured.given()
                .cookies(cookies)
                .queryParam(COMMENT_ID, commentId)
                .when()
                .delete(DELETE_COMMENT_ENDPOINT);
    }

    public static Response editComment(Cookies cookies, int commentId, String updatedUniqueContent) {
        return RestAssured.given()
                .cookies(cookies)
                .queryParam(COMMENT_ID, commentId)
                .queryParam("content", updatedUniqueContent)
                .when()
                .put(EDIT_COMMENT_ENDPOINT);
    }

    public static Response findAllComments(Cookies cookies) {
        return RestAssured.given()
                .cookies(cookies)
                .when()
                .get(FIND_ALL_COMMENTS_ENDPOINT);
    }

    public static Response findAllCommentsOfAPost(Cookies cookies, int postId) {
        return RestAssured.given()
                .cookies(cookies)
                .queryParam(POST_ID, postId)
                .when()
                .get(FIND_ALL_COMMENTS_OF_A_POST_ENDPOINT);
    }

    public static Response findOneCommentOfAPost(Cookies cookies, int commentId) {

        return RestAssured.given()
                .cookies(cookies)
                .queryParam(COMMENT_ID, commentId)
                .when()
                .get(FIND_ONE_COMMENT_OF_A_POST_ENDPOINT);
    }

    public static Response LikeDislikeComment(Cookies cookies, int commentId) {

        return RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .queryParam(COMMENT_ID, commentId)
                .when()
                .post(LIKE_COMMENT_ENDPOINT);
    }
}
