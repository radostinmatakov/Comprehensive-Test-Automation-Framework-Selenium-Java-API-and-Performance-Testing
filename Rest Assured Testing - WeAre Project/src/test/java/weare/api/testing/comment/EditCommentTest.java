package weare.api.testing.comment;

import api.CommentController;
import api.PostController;
import base.BaseTestSetup;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.Comment;
import models.Post;
import models.UserRegister;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataGenerator;
import utils.ModelGenerator;

import static org.testng.Assert.assertEquals;
import static utils.Constants.*;

public class EditCommentTest extends BaseTestSetup {
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
            System.out.println(response);
            isResponse200(response);

            createdComment = response.as(Comment.class);
            System.out.println(COMMENT_SUCCESS_MESSAGE + " " + createdComment.commentId);
            isCommentDeleted = false;
        }
    }

    @Test
    public void editComment() {
        String updatedUniqueContent = DataGenerator.generateUniqueContentPost();
        Response response = CommentController.editComment(cookies, createdComment.commentId, updatedUniqueContent);
        isResponse200(response);
        System.out.println(String.format(EDIT_COMMENT_SUCCESS_MESSAGE, commentId));

        Response comment = CommentController.findOneCommentOfAPost(cookies, createdComment.commentId);
        System.out.println(comment.asString());


    }

    @AfterClass
    public void tearDown() {
        if (!isDeletedPost) {
            PostController.deletePost(cookies, createdPost.postId);
            System.out.println(DELETE_POST_SUCCESS_MESSAGE + " " + createdPost.postId);
            isDeletedPost = true;
        }
        if (!isCommentDeleted) {
            CommentController.deleteComment(cookies, createdComment.commentId);
            System.out.println(DELETE_COMMENT_ID_SUCCESS_MESSAGE + " " + createdPost.postId);
            isCommentDeleted = true;
        }
    }
}
