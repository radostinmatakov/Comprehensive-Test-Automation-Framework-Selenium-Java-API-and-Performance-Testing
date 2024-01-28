package models;

import java.util.ArrayList;

public class Comment {
    public int commentId;
    public String content;
    public boolean deletedConfirmed;
    public int postId;
    public int userId;
    public String date;
    public ArrayList<Object> likes;
    public boolean liked;
    public Object hibernateLazyInitializer;
}
