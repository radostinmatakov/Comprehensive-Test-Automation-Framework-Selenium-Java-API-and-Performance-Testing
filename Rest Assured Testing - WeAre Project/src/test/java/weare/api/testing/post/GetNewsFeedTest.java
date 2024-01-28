package weare.api.testing.post;

import api.PostController;
import base.BaseTestSetup;
import io.restassured.response.Response;
import models.Post;
import models.UserRegister;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataGenerator;
import utils.ModelGenerator;

import static utils.Constants.*;


public class GetNewsFeedTest extends BaseTestSetup {

    @BeforeClass
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
        }

        String uniqueContent = DataGenerator.generateUniqueContentPost();
        createPost = ModelGenerator.generatePostModel(uniqueContent);
        authenticateAndFetchCookies();
        Response response = PostController.createPost(cookies, createPost);
        createdPost = response.as(Post.class);
    }

    @Test
    public void getNewsFeed() {
        Response response = PostController.getNewsFeed(cookies);
        isResponse200(response);

        Post[] posts = response.as(Post[].class);
        Assert.assertNotNull(posts, POSTS_ARE_NULL_MESSAGE);
        Assert.assertTrue(posts.length > 0, ARRAY_SIZE_MESSAGE);

        Assert.assertNotNull(posts[0].content, CONTENT_IS_NULL_MESSAGE);
        System.out.println(SUCCESSFULLY_FETCHED_ALL_POSTS_MESSAGE);
    }
}


