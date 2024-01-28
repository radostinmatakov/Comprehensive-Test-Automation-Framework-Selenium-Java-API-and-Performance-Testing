package weare.api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utils.Serializer;
import weare.models.CommentModel;

import static utils.Endpoints.*;

public class CommentController {

    public static Response createComment(Cookies cookies, CommentModel createComment) {
        String bodyCommentString = Serializer.convertObjectToJsonString(createComment);
        return RestAssured.given()
                .cookies(cookies)
                .contentType("application/json")
                .body(bodyCommentString)
                .when()
                .post(CREATE_COMMENT_ENDPOINT);
    }

    public static Response findAllCommentsOfAPost(Cookies cookies, int postId) {
        return RestAssured.given()
                .cookies(cookies)
                .queryParam("postId", postId)
                .when()
                .get(FIND_ALL_COMMENTS_OF_A_POST_ENDPOINT);

    }
}
