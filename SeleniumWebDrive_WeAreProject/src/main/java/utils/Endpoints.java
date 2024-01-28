package utils;

public class Endpoints {

    public static final String BASE_URL = "http://localhost:8081";
    public static final String USERS_ENDPOINT = "/api/users/";
    public static final String CREATE_POST_ENDPOINT = "/api/post/auth/creator";
    public static final String LIKE_POST_ENDPOINT = "/api/post/auth/likesUp";
    public static final String CREATE_COMMENT_ENDPOINT = "/api/comment/auth/creator";
    public static final String FIND_ALL_COMMENTS_OF_A_POST_ENDPOINT = "/api/comment/byPost";
    public static final String AUTHENTICATE_ENDPOINT = "/authenticate";
    public static final String UPDATE_PERSONAL_ENDPOINT = "http://localhost:8081/auth/users/%d/profile";
    public static final String SEND_REQUEST_ENDPOINT = "/api/auth/request";
    public static final String GET_USER_REQUESTS_ENDPOINT = "/api/auth/users/{receiverUserId}/request/";
    public static final String APPROVE_CONNECTION_REQUEST_ENDPOINT = "/api/auth/users/{receiverUserId}/request/approve";
    public static final String GET_PROFILE_POSTS_ENDPOINT = "http://localhost:8081/api/users/{currentUserId}/posts";
    public static final String SKILL_ENDPOINT = "/api/skill";

}
