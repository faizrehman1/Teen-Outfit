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


    public UserModel(String email, String password, String cpassword, String userID, String fname, String lname, String imgURL) {
        this.email = email;
        this.password = password;
        this.cpassword = cpassword;
        this.userID = userID;
        this.fname = fname;
        this.lname = lname;
        this.imgURL = imgURL;
    }

    public UserModel() {
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

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
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
