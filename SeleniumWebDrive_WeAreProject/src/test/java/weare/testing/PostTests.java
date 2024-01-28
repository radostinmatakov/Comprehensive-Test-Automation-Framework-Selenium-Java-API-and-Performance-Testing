package weare.testing;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;
import utils.ModelGenerator;
import weare.api.PostController;
import weare.api.UserController;
import weare.models.PostModel;
import weare.models.UserRegister;

import static com.telerikacademy.testframework.Utils.getUIMappingByKey;

public class PostTests extends BaseTestSetup {
    protected static UserRegister userToRegister;
    protected static int registeredUserId;
    protected static Cookies cookies;
    protected static String postContent;
    protected static String postContentUpdate;
    protected static PostModel[] postsList;
    protected static PostModel post;

    @BeforeAll
    public static void setup() {
        userToRegister = ModelGenerator.generateUserRegisterModel();
        Response response = UserController.registerUser(userToRegister);
        cookies = UserController.authenticatedAndFetchCookies(userToRegister.username, userToRegister.password);

        registeredUserId = Integer.parseInt(response.asString().split(" ")[6]);
        loginPage.navigateToPage();
        LoginPage.loginUser(userToRegister.username, userToRegister.password);
        LoginPage.assertElementPresent("weAre.loginPage.logoutLink");

        postContentUpdate = DataGenerator.generateUniqueContentPost();
    }

    @BeforeEach
    public void beforeEach() {
        postContent = DataGenerator.generateUniqueContentPost();
    }

    @Test
    public void PublicPostCreated_When_UserCreatesPublicPostWithValidInput() {
        newPostPage.navigateToPage();
        newPostPage.createPost(postContent, "Public");

        allPostPage.assertNavigatedUrl();
        allPostPage.assertElementPresent(String.format(getUIMappingByKey("weAre.allPostPage.postContent"), postContent));

        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(postContent, postsList[0].content);
        Assertions.assertEquals(true, postsList[0].mypublic, "Post is not public");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + postsList[0].postId);
        postPage.navigateToPage();
        postPage.delete(postsList[0].postId);
    }

    @Test
    public void PublicPostUpdated_When_UserUpdatesPublicPostWithValidInput() {
        post = PostController.createPost(cookies, ModelGenerator.generatePostModel(postContent, true)).as(PostModel.class);
        Assertions.assertEquals(true, post.mypublic, "Post is not public");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + post.postId);
        postPage.navigateToPage();
        postPage.update(postContentUpdate, "Public", post.postId);

        postPage.assertNavigatedUrl();
        postPage.assertElementPresent(String.format(getUIMappingByKey("weAre.allPostPage.postContent"), postContentUpdate));

        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(postContentUpdate, postsList[0].content);
        Assertions.assertEquals(true, postsList[0].mypublic, "Post is not public");

        postPage.navigateToPage();
        postPage.delete(postsList[0].postId);
    }

    @Test
    public void PublicPostLiked_When_UserLikesPublicPost() {
        post = PostController.createPost(cookies, ModelGenerator.generatePostModel(postContent, true)).as(PostModel.class);
        Assertions.assertEquals(true, post.mypublic, "Post is not public");

        latestPostsPage.navigateToPage();
        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(post.postId, postsList[0].postId, "Post is not the same");

        latestPostsPage.assertPostIsPresent(post.content);
        latestPostsPage.likeDislikePost(post.postId);
        latestPostsPage.assertPostIsLiked(post.postId);
        PostModel likedPost = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class)[0];

        Assertions.assertEquals(post.postId, likedPost.postId, "Post is not the same");
        Assertions.assertEquals(1, likedPost.likes.size(), "Post is not liked");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + post.postId);
        postPage.navigateToPage();
        postPage.delete(postsList[0].postId);
    }

    @Test
    public void PublicPostDeleted_When_UserDeletesPublicPost() {
        post = PostController.createPost(cookies, ModelGenerator.generatePostModel(postContent, true)).as(PostModel.class);
        Assertions.assertEquals(true, post.mypublic, "Post is not public");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + post.postId);
        postPage.navigateToPage();
        postPage.delete(post.postId);

        postPage.assertElementPresent(String.format(getUIMappingByKey("weAre.allPostPage.deleteMessage"), "Post deleted successfully"));
        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(0, postsList.length);
    }

    @Test
    public void privatePostCreated_When_UserCreatesPrivatePostWithValidInput() {
        newPostPage.navigateToPage();
        newPostPage.createPost(postContent, "Private");

        allPostPage.assertNavigatedUrl();
        allPostPage.assertElementPresent(String.format(getUIMappingByKey("weAre.allPostPage.postContent"), postContent));

        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(postContent, postsList[0].content);
        Assertions.assertEquals(false, postsList[0].mypublic, "Post is not private");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + postsList[0].postId);
        postPage.navigateToPage();
        postPage.delete(postsList[0].postId);
    }

    @Test
    public void privatePostUpdated_When_UserUpdatesPrivatePostWithValidInput() {
        post = PostController.createPost(cookies, ModelGenerator.generatePostModel(postContent, false)).as(PostModel.class);
        Assertions.assertEquals(false, post.mypublic, "Post is not private");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + post.postId);
        postPage.navigateToPage();
        postPage.update(postContentUpdate, "Private", post.postId);

        postPage.assertNavigatedUrl();
        postPage.assertElementPresent(String.format(getUIMappingByKey("weAre.allPostPage.postContent"), postContentUpdate));

        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(postContentUpdate, postsList[0].content);
        Assertions.assertEquals(false, postsList[0].mypublic, "Post is not private");

        postPage.navigateToPage();
        postPage.delete(postsList[0].postId);
    }

    @Test
    public void privatePostDisliked_When_UserDislikesPrivatePost() {
        post = PostController.createPost(cookies, ModelGenerator.generatePostModel(postContent, false)).as(PostModel.class);
        Assertions.assertEquals(false, post.mypublic, "Post is not private");

        latestPostsPage.navigateToPage();
        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(post.postId, postsList[0].postId, "Post is not the same");
        PostController.likeAndDislikePost(cookies, postsList[0].postId);
        latestPostsPage.navigateToPage();
        PostModel dislikedPost = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class)[0];
        latestPostsPage.assertPostIsPresent(postsList[0].content);
        latestPostsPage.likeDislikePost(postsList[0].postId);


        latestPostsPage.assertPostDisliked(postsList[0].postId);

        dislikedPost = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class)[0];

        Assertions.assertEquals(post.postId, dislikedPost.postId, "Post is not the same");
        Assertions.assertEquals(0, dislikedPost.likes.size(), "Post is liked");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + post.postId);
        postPage.navigateToPage();
        postPage.delete(postsList[0].postId);
    }

    @Test
    public void privatePostDeleted_When_UserDeletesPrivatePost() {
        post = PostController.createPost(cookies, ModelGenerator.generatePostModel(postContent, false)).as(PostModel.class);
        Assertions.assertEquals(false, post.mypublic, "Post is not private");

        postPage = new PostPage(driver, "http://localhost:8081/posts/" + post.postId);
        postPage.navigateToPage();
        postPage.delete(post.postId);

        postPage.assertElementPresent(String.format(getUIMappingByKey("weAre.allPostPage.deleteMessage"), "Post deleted successfully"));
        postsList = UserController.getProfilePosts(cookies, registeredUserId).as(PostModel[].class);
        Assertions.assertEquals(0, postsList.length);
    }
}
