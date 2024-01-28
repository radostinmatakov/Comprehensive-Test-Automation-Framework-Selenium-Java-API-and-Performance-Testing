package utils;

import com.github.javafaker.Faker;
import models.*;

import java.util.ArrayList;
import java.util.Random;

public class ModelGenerator {
    private static Faker faker = new Faker();
    private static Random random = new Random();

    public static Skill generateSkillModel(int categoryId) {
        Category category = new Category();
        category.id = categoryId;

        Skill skill = new Skill();
        skill.category = category;
        skill.skill = "Test Skill" + System.currentTimeMillis();
        skill.skillId = 0;
        return skill;
    }


    public static Post generatePostModel(String uniqueContent){
        Post createPost = new Post();
        createPost.content = uniqueContent;
        createPost.picture = "";
        createPost.mypublic = true;

        return createPost;
    }
    public static Post generatePostModel(){
        Post createPost = new Post();
        createPost.content = DataGenerator.generateUniqueContentPost();
        createPost.picture = "";
        createPost.mypublic = true;

        return createPost;
    }

    public static Comment generateCommentModel(String uniqueContent, int postId, int userId){
        Comment createComment = new Comment();
        createComment.commentId = 0;
        createComment.content = uniqueContent;
        createComment.deletedConfirmed = true;
        createComment.postId = postId;
        createComment.userId = userId;
        createComment.likes = null;
        return createComment;
    }

    public static SendRequest generateSendRequestModel( int receiverUserId, String receiverUsername){
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
    public static UserRegister generateUserRegisterModel(String username, String email) {
        String password = DataGenerator.generateUniquePassword();
        UserRegister userRegister = new UserRegister();
        userRegister.email = username;
        userRegister.username = email;
        userRegister.password = password;
        userRegister.confirmPassword = password;

        Category category = new Category();
        category.id = 100;
        category.name = "All";
        userRegister.category = category;
        return userRegister;
    }

    public static Page generatePageModel(int size){
        Page page = new Page();
        page.size = size;
        return page;
    }

    public static UserPersonal generateUserPersonalModel(){
        UserPersonal profile = new UserPersonal();
        profile.firstName = "firstTestUpdated";
        profile.lastName = "lastTestUpdated";
        profile.gender = "MALE";
        profile.city = "Sofia";
        profile.birthYear = "1990-04-04";
        return profile;
    };

    public static UserProfile generateUserProfileModel(){
        UserProfile profile = new UserProfile();

        return profile;
    }

    public static SearchUser generateSearchUserModel(String name){
        SearchUser byName = new SearchUser();
        byName.index = 0;
        byName.next = true;
        byName.searchParam1 = "";
        byName.searchParam2 = name;
        byName.size = 1;
        return byName;
    }

    public static ExpertiseProfile generateUserExpertiseProfile(int categoryId, String categoryName, double availability){
        ExpertiseProfile profile = new ExpertiseProfile();
        profile.category = new Category();
        profile.category.id = categoryId;
        profile.category.name = categoryName;
        profile.availability = availability;
        profile.skills = new ArrayList<>();
        return profile;
    };
}
