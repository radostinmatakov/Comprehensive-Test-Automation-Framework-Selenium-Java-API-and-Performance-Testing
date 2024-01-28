package weare.api.testing.comment;

import api.CommentController;
import api.PostController;
import base.BaseTestSetup;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.Comment;
import models.Post;
import models.UserRegister;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataGenerator;
import utils.ModelGenerator;

import static org.testng.Assert.assertEquals;
import static utils.Constants.*;

public class CreateCommentTest extends BaseTestSetup {
    String uniqueContent;
    Cookies cookies;

    @BeforeClass
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
            userId = currentUserId;
            System.out.println(USER_SUCCESS_MESSAGE + userId);

        }
        cookies = authenticateAndFetchCookies();
        if (isDeletedPost) {
            uniqueContent = DataGenerator.generateUniqueContentPost();
            createPost = ModelGenerator.generatePostModel(uniqueContent);
            Response response = PostController.createPost(cookies, createPost);

            createdPost = response.as(Post.class);
            assertEquals(createdPost.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);

            postId = createdPost.postId;
            System.out.println(POST_SUCCESS_MESSAGE + postId);
            isDeletedPost = false;
        }
    }

    @Test
    public void createComment() {
        String uniqueContent = DataGenerator.generateUniqueContentPost();
        createComment = ModelGenerator.generateCommentModel(uniqueContent, postId, currentUserId);
        Response response = CommentController.createComment(cookies, createComment);
        isResponse200(response);

        createdComment = response.as(Comment.class);
        assertEquals(createdComment.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);
        Assert.assertNotNull(createdComment.commentId, COMMENT_ID_NULL_MESSAGE);
        Assert.assertNotNull(createdComment.content, CONTENT_NULL_MESSAGE);
        Assert.assertNotNull(createdComment.likes, LIKES_NULL_MESSAGE);
        Assert.assertNotNull(createdComment.date, DATE_NULL_MESSAGE);
        Assert.assertNotNull(createdComment.liked, LIKED_NULL_MESSAGE);


        commentId = createdComment.commentId;
        System.out.println(COMMENT_SUCCESS_MESSAGE + commentId + ALL_PROPERTIES_NOT_NULL);

    }

    @AfterClass
    public void tearDown() {
        if (!isDeletedPost) {
            PostController.deletePost(cookies, createdPost.postId);
            System.out.println(DELETE_POST_SUCCESS_MESSAGE + createdPost.postId);
            isDeletedPost = true;
        }
        if (!isCommentDeleted) {
            CommentController.deleteComment(cookies, createdComment.commentId);
            System.out.println(DELETE_POST_SUCCESS_MESSAGE + createdPost.postId);
            isCommentDeleted = true;
        }
    }
}
