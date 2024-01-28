package weare.api.testing.post;

import api.PostController;
import base.BaseTestSetup;
import io.restassured.response.Response;
import models.Post;
import models.UserRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataGenerator;
import utils.ModelGenerator;

public class DeleteAPostTest extends BaseTestSetup {
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
    public void deleteAPost() {
        authenticateAndFetchCookies();
        Response response = PostController.deletePost(cookies, createdPost.postId);
        isResponse200(response);

        System.out.println("Post with Id" + " " + createdPost.postId + " " + "Deleted successfully.");
        isDeletedPost = true;

    }
}
