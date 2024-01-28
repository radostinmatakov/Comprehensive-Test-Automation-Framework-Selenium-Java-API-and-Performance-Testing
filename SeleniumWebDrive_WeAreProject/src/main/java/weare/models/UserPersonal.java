package weare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPersonal {

    public int id;
    public String username;
    public ArrayList<String> authorities;
    public String email;
    public String firstName;
    public String lastName;
    public String gender;
    public Object city = "";
    public String birthYear;
    public Object personalReview = "";
    public String expertise = "Doctor";
    public Location location = new Location();
    public ArrayList<Skill> skills;
}
