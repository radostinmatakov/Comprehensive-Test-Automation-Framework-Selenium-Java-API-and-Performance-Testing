package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpertiseProfile {
    public int id;
    public ArrayList<Skill> skills;
    public Category category;
    public double availability;

}
