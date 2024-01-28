package weare.api.testing.post;

import api.PostController;
import base.BaseTestSetup;
import io.restassured.response.Response;
import models.Post;
import models.PostModelLikeDislike;
import models.UserRegister;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataGenerator;
import utils.ModelGenerator;

import static utils.Constants.*;

public class LikeDislikeAPostTest extends BaseTestSetup {

    @BeforeClass
    public void PostCreated() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
        }

        if (isDeletedPost) {
            String uniqueContent = DataGenerator.generateUniqueContentPost();
            createPost = ModelGenerator.generatePostModel(uniqueContent);
            authenticateAndFetchCookies();
            Response response = PostController.createPost(cookies, createPost);
            createdPost = response.as(Post.class);
            postId = createdPost.postId;
            System.out.println(String.format(POST_CREATED_MESSAGE, postId));
            isDeletedPost = false;
        }
    }

    @Test
    public void likeDislikeAPost() {

        Response response = PostController.likeAndDislikePost(cookies, postId);
        isResponse200(response);

        PostModelLikeDislike editPostLikeDislike = response.as(PostModelLikeDislike.class);
        Assert.assertEquals(editPostLikeDislike.liked, true, POST_NOT_LIKED_MESSAGE);
        System.out.println(String.format(POST_LIKED_MESSAGE_FORMAT, postId));

        Response dislikeResponse = PostController.likeAndDislikePost(cookies, postId);
        isResponse200(dislikeResponse);

        System.out.println(String.format(POST_DISLIKED_MESSAGE, postId));

    }

    @AfterTest
    public void tearDown() {
        if (!isDeletedPost) {
            PostController.deletePost(cookies, createdPost.postId);
            System.out.println(String.format(POST_DELETED_MESSAGE, postId));
            isDeletedPost = true;
        }
    }
}
