package weare.api.testing.post;

import api.PostController;
import base.BaseTestSetup;
import io.restassured.response.Response;
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

public class CreatePostTest extends BaseTestSetup {

    @BeforeClass
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
        }
    }

    @Test
    public void createPost() {
        authenticateAndFetchCookies();
        String uniqueContent = DataGenerator.generateUniqueContentPost();
        createPost = ModelGenerator.generatePostModel(uniqueContent);

        Response response = PostController.createPost(cookies, createPost);

        isResponse200(response);

        createdPost = response.as(Post.class);

        assertEquals(createdPost.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);
        Assert.assertNotNull(createdPost.postId, POST_ID_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.picture, PICTURE_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.comments, COMMENTS_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.rank, RANK_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.category.id, CATEGORY_ID_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.category.name, CATEGORY_NAME_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.mypublic, PUBLIC_NULL_MESSAGE);


        System.out.println(String.format(POST_SUCCESS_ALL_PROPERTIES_NOT_NULL, createdPost.postId));
    }

    @Test
    public void createPostWith220CharactersLongMessage() {
        authenticateAndFetchCookies();
        String uniqueContent = DataGenerator.generateUniqueContentPost(220);
        createPost = ModelGenerator.generatePostModel(uniqueContent);

        Response response = PostController.createPost(cookies, createPost);

        isResponse200(response);

        createdPost = response.as(Post.class);

        assertEquals(createdPost.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);
        Assert.assertNotNull(createdPost.postId, POST_ID_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.picture, PICTURE_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.comments, COMMENTS_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.rank, RANK_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.category.id, CATEGORY_ID_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.category.name, CATEGORY_NAME_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.mypublic, PUBLIC_NULL_MESSAGE);


        System.out.println(String.format(POST_SUCCESS_ALL_PROPERTIES_NOT_NULL, createdPost.postId));
    }

    @Test
    public void createPostWith1CharacterLongMessage() {
        authenticateAndFetchCookies();
        String uniqueContent = DataGenerator.generateUniqueContentPost(1);
        createPost = ModelGenerator.generatePostModel(uniqueContent);

        Response response = PostController.createPost(cookies, createPost);

        isResponse200(response);

        createdPost = response.as(Post.class);

        assertEquals(createdPost.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);
        Assert.assertNotNull(createdPost.postId, POST_ID_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.picture, PICTURE_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.comments, COMMENTS_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.rank, RANK_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.category.id, CATEGORY_ID_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.category.name, CATEGORY_NAME_NULL_MESSAGE);
        Assert.assertNotNull(createdPost.mypublic, PUBLIC_NULL_MESSAGE);


        System.out.println(String.format(POST_SUCCESS_ALL_PROPERTIES_NOT_NULL, createdPost.postId));
    }


    @AfterClass
    public void tearDown() {
        editPost = createdPost;
        postId = createdPost.postId;

        if (!isDeletedPost) {
            PostController.deletePost(cookies, createdPost.postId);
            System.out.println(String.format(POST_DELETED_MESSAGE, postId));
            isDeletedPost = true;
        }
    }
}

