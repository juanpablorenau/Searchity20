package com.example.searchity20.model;

import java.util.Date;

public class User {

    private String type;
    private String email;
    private String name;
    private String lastname;
    private String birthday;
    private String gender;
    private String province;
    private String university;
    private String degree;
    private String course;
    private String graduationYear;
    private String profile_picture_link;
    private Date registrationDate;

    //Precollege
    public User(String type, String email, String name, String lastname, String birthday, String gender, String profile_picture_link, Date registrationDate) {
        this.type = type;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.profile_picture_link = profile_picture_link;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile_picture_link() {
        return profile_picture_link;
    }

    public void setProfile_picture_link(String profile_picture_link) {
        this.profile_picture_link = profile_picture_link;
    }
}
