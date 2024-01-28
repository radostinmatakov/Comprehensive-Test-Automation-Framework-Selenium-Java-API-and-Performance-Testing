package utils;

import com.github.javafaker.Faker;
import weare.models.*;

import java.util.Random;

public class ModelGenerator {
    private static Faker faker = new Faker();
    private static Random random = new Random();


    public static PostModel generatePostModel(String uniqueContent, boolean isPublic) {
        PostModel createPost = new PostModel();
        createPost.content = uniqueContent;
        createPost.picture = "";
        createPost.mypublic = isPublic;

        return createPost;
    }

    public static PostModel generatePostModel(boolean isPublic) {
        PostModel createPost = new PostModel();
        createPost.content = DataGenerator.generateUniqueContentPost();
        createPost.picture = "";
        createPost.mypublic = isPublic;

        return createPost;
    }

    public static CommentModel generateCommentModel(int postId, int userId) {
        CommentModel createComment = new CommentModel();
        createComment.commentId = 0;
        createComment.content = DataGenerator.generateUniqueContentPost();
        createComment.postId = postId;
        createComment.userId = userId;
        createComment.likes = null;
        return createComment;
    }

    public static SendRequest generateSendRequestModel(int receiverUserId, String receiverUsername) {
        SendRequest sendRequestToUser = new SendRequest();
        sendRequestToUser.id = receiverUserId;
        sendRequestToUser.username = receiverUsername;
        return sendRequestToUser;
    }
    public static UserRegister generateUserRegisterModel() {
        String password = DataGenerator.generateUniquePassword();
        UserRegister userRegister = new UserRegister();
        userRegister.email = DataGenerator.generateUniqueEmail();
        userRegister.username = DataGenerator.generateUniqueUsername();
        userRegister.password = password;
        userRegister.confirmPassword = password;

        Category category = new Category();
        category.id = 100;
        category.name = "All";
        userRegister.category = category;
        return userRegister;
    }

    public static UserPersonal generateUserPersonalModel() {
        UserPersonal profile = new UserPersonal();
        profile.firstName = "firstTestUpdated";
        profile.lastName = "lastTestUpdated";
        profile.gender = "MALE";
        profile.city = "Sofia";
        profile.birthYear = "1990-04-04";
        profile.email = DataGenerator.generateUniqueEmail();
        return profile;

    }
}
