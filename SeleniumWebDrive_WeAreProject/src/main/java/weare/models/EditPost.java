package weare.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class EditPost {
    public int postId;
    public String content;
    public String picture;
    public String date;
    public ArrayList<Object> likes;
    public ArrayList<Object> comments;
    public int rank;
    public Category category;
    public boolean liked;
    @JsonProperty("public")
    public boolean mypublic;
}
