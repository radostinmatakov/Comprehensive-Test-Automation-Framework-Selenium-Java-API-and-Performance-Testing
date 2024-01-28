package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPersonal {

    public int id;
    public String username;
    public ArrayList<String> authorities;
    public String email;
    public Object firstName;
    public Object lastName;
    public Object gender;
    public Object city = "";
    public Object birthYear;
    public Object personalReview = "";
    public String expertise = "Doctor";
    public Location location = new Location();
    public ArrayList<Skill> skills;
}
