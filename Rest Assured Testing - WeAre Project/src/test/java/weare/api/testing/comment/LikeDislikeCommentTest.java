package weare.api.testing.comment;

import api.CommentController;
import api.PostController;
import base.BaseTestSetup;
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

public class LikeDislikeCommentTest extends BaseTestSetup {
    String uniqueContent;

    @BeforeClass
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
            System.out.println(USER_SUCCESS_MESSAGE + " " + currentUserId);
        }
        if (isDeletedPost) {
            uniqueContent = DataGenerator.generateUniqueContentPost();
            createPost = ModelGenerator.generatePostModel(uniqueContent);
            authenticateAndFetchCookies();
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
            System.out.println(String.format(CREATE_COMMENT_SUCCESS_MESSAGE, createdComment.commentId));
            isCommentDeleted = false;
        }
    }

    @Test
    public void likeDislikeComment() {

        Response response = CommentController.LikeDislikeComment(cookies, createdComment.commentId);

        isResponse200(response);

        Comment likedComment = response.as(Comment.class);
        Assert.assertEquals(likedComment.liked, true, POST_NOT_LIKED_MESSAGE);
        System.out.println(String.format(LIKE_COMMENT_SUCCESS_MESSAGE, createdComment.commentId));

        response = CommentController.LikeDislikeComment(cookies, createdComment.commentId);
        Comment dislikedComment = response.as(Comment.class);
        Assert.assertEquals(dislikedComment.liked, false, POST_NOT_DISLIKED_MESSAGE);
        System.out.println(String.format(DISLIKE_COMMENT_SUCCESS_MESSAGE, createdComment.commentId));

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



