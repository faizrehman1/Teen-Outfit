package com.example.faiz.vividways.Models;

/**
 * Created by Faiz on 7/19/2017.
 */

public class UserModel {

    private String user_email;
    private String user_password;
    private String user_userID;
    private String user_fname;
    private String user_lname;
    private String user_imgURL;
    private String user_country;
    private String user_gender;


    public UserModel(String user_email, String user_password, String user_userID, String user_fname, String user_lname, String user_imgURL, String user_country, String user_gender) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_userID = user_userID;
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_imgURL = user_imgURL;
        this.user_country = user_country;
        this.user_gender = user_gender;
    }

    public UserModel(String user_fname, String user_lname, String user_imgURL) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_imgURL = user_imgURL;
    }

    public UserModel() {
    }


    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_userID() {
        return user_userID;
    }

    public void setUser_userID(String user_userID) {
        this.user_userID = user_userID;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_imgURL() {
        return user_imgURL;
    }

    public void setUser_imgURL(String user_imgURL) {
        this.user_imgURL = user_imgURL;
    }

    public String getUser_country() {
        return user_country;
    }

    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }
}
