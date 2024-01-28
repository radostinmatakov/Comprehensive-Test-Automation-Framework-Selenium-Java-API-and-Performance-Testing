package weare.api.testing.comment;

import api.CommentController;
import api.PostController;
import base.BaseTestSetup;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.Comment;
import models.Post;
import models.UserRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataGenerator;
import utils.ModelGenerator;

import static org.testng.Assert.assertEquals;
import static utils.Constants.*;

public class DeleteCommentTest extends BaseTestSetup {
    String uniqueContent;
    Cookies cookies;

    @BeforeClass
    public void setup() {

        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
        }
        cookies = authenticateAndFetchCookies();
        if (isDeletedPost) {
            uniqueContent = DataGenerator.generateUniqueContentPost();
            createPost = ModelGenerator.generatePostModel(uniqueContent);
            Response response = PostController.createPost(cookies, createPost);

            createdPost = response.as(Post.class);
            assertEquals(createdPost.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);
            postId = createdPost.postId;
            System.out.println(POST_SUCCESS_MESSAGE + " " + postId);
            isDeletedPost = false;
        }
        if (isCommentDeleted) {
            createComment = ModelGenerator.generateCommentModel(DataGenerator.generateUniqueContentPost(), postId, currentUserId);

            Response response = CommentController.createComment(cookies, createComment);
            isResponse200(response);

            createdComment = response.as(Comment.class);
            System.out.println(POST_SUCCESS_MESSAGE + " " + createdComment.commentId);
            isCommentDeleted = false;
        }
    }

    @Test
    public void deleteComment() throws InterruptedException {
        Response response = CommentController.deleteComment(cookies, createdComment.commentId);
        isResponse200(response);
        System.out.println(String.format(DELETE_COMMENT_SUCCESS_MESSAGE, commentId));
        isCommentDeleted = true;
        Thread.sleep(1000);
    }
}
