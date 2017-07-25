package com.example.faiz.vividways.Models;

/**
 * Created by Faiz on 7/19/2017.
 */

public class UserModel {

    private String email;
    private String password;
    private String cpassword;
    private String userID;
    private String fname;
    private String lname;
    private String imgURL;
    private String country;


    public UserModel(String email, String password, String userID, String fname, String lname, String imgURL, String country) {
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.fname = fname;
        this.lname = lname;
        this.imgURL = imgURL;
        this.country = country;
    }

    public UserModel(String fname, String lname, String imgURL) {
        this.fname = fname;
        this.lname = lname;
        this.imgURL = imgURL;
    }

    public UserModel() {
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
