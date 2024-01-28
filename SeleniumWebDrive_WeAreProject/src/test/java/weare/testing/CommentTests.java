package weare.testing;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import utils.DataGenerator;
import utils.ModelGenerator;
import weare.api.CommentController;
import weare.api.PostController;
import weare.api.UserController;
import weare.models.CommentModel;
import weare.models.PostModel;
import weare.models.UserRegister;

public class CommentTests extends BaseTestSetup {
    static UserRegister userToRegister;
    static int registeredUserId;
    static Cookies cookies;
    static PostModel createdPost;
    static CommentModel createCommentModel;
    static CommentModel comment;

    @BeforeAll
    public static void setup() {
        userToRegister = ModelGenerator.generateUserRegisterModel();
        Response response = UserController.registerUser(userToRegister);
        cookies = UserController.authenticatedAndFetchCookies(userToRegister.username, userToRegister.password);
        registeredUserId = Integer.parseInt(response.asString().split(" ")[6]);

        PostModel postModel = ModelGenerator.generatePostModel(true);
        createdPost = PostController.createPost(cookies, postModel).as(PostModel.class);
        loginPage.navigateToPage();
        LoginPage.loginUser(userToRegister.username, userToRegister.password);
        LoginPage.assertElementPresent("weAre.loginPage.logoutLink");
        postPage = new PostPage(driver, "http://localhost:8081/posts/" + createdPost.postId);
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        createCommentModel = ModelGenerator.generateCommentModel(createdPost.postId, registeredUserId);
        if (!testInfo.getDisplayName().contains("createComment")) {
            comment = CommentController.createComment(cookies, createCommentModel).as(CommentModel.class);
            System.out.println("Comment with " + comment.commentId + " created");
        }
        postPage.navigateToPage();
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        if (!testInfo.getDisplayName().equals("deleteComment()")) {
            postPage.deleteComment(comment.commentId);
            System.out.println("Comment with " + comment.commentId + " deleted");
        }
    }

    @Test
    public void commentCreated_When_ValidDataProvided() {
        postPage.createComment(createCommentModel);
        postPage.navigateToPage();

        postPage.assertCommentPresent(createCommentModel);

        comment = CommentController.findAllCommentsOfAPost(cookies, createdPost.postId).as(CommentModel[].class)[0];
        Assertions.assertEquals(createCommentModel.content, comment.content);

    }

    @Test
    void commentLiked_When_UserClicksOnLikeButton() {
        postPage.assertCommentPresent(comment);
        postPage.likeComment(comment.commentId);
        postPage.assertCommentIsLiked(comment.commentId);

        comment = CommentController.findAllCommentsOfAPost(cookies, createdPost.postId).as(CommentModel[].class)[0];

        Assertions.assertEquals(1, comment.likes.size());
    }

    @Test
    public void commentEdited_When_UserEditsComment() {
        String updateCommentString = DataGenerator.generateUniqueContentPost() + " Updated";
        postPage.assertCommentPresent(createCommentModel);
        postPage.editComment(comment.commentId, updateCommentString);
        postPage.navigateToPage();

        createCommentModel.content = updateCommentString;
        postPage.assertCommentPresent(createCommentModel);

        comment = CommentController.findAllCommentsOfAPost(cookies, createdPost.postId).as(CommentModel[].class)[0];
        Assertions.assertEquals(updateCommentString, comment.content);
    }

    @Test
    void commentDisliked_When_UserDislikesComment() {
        postPage.assertCommentPresent(createCommentModel);

        postPage.likeComment(comment.commentId);
        postPage.dislikeComment(comment.commentId);
        postPage.assertCommentIsDisliked(comment.commentId);

        comment = CommentController.findAllCommentsOfAPost(cookies, createdPost.postId).as(CommentModel[].class)[0];
        Assertions.assertEquals(0, comment.likes.size());
    }

    @Test
    public void commentDeleted_When_UserDeletesComment() {
        postPage.assertCommentPresent(createCommentModel);
        postPage.deleteComment(comment.commentId);
        postPage.assertCommentIsDeleted();

        CommentModel[] commentsList = CommentController.findAllCommentsOfAPost(cookies, createdPost.postId).as(CommentModel[].class);
        Assertions.assertEquals(0, commentsList.length);
    }
}
