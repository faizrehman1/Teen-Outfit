package com.example.faiz.vividways.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.faiz.vividways.Models.UserModel;

/**
 * Created by Faiz on 7/31/2017.
 */

public class SharedPref {
    private static String NAME = "packageName";
    private static String U_FNAME = "fname";
    private static String U_LNAME = "lanme";
    private static String U_ID = "userid";
    private static String U_EMAIL = "email";
    private static String U_IMG_URL = "iamgurl";
    private static String U_PASS = "u_dob";
    private static String U_GEND = "u_gea";
    private static String U_STATUS = "u_status";

    public static void setCurrentUser(Context context, UserModel user) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(U_FNAME, user.getUser_fname()).apply();
        preferences.edit().putString(U_LNAME, user.getUser_lname()).apply();
        preferences.edit().putString(U_IMG_URL, user.getUser_imgURL()).apply();
        preferences.edit().putString(U_ID, user.getUser_userID()).apply();
        preferences.edit().putString(U_EMAIL, user.getUser_email()).apply();
        preferences.edit().putString(U_GEND, user.getUser_gender()).apply();
        preferences.edit().putString(U_PASS, user.getUser_password()).apply();
    }

    public static UserModel getCurrentUser(Context context) {
        UserModel user = new UserModel();
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        user.setUser_fname(preferences.getString(U_FNAME, ""));
        user.setUser_lname(preferences.getString(U_LNAME, ""));
        user.setUser_email(preferences.getString(U_EMAIL, ""));
        user.setUser_userID(preferences.getString(U_ID, ""));
        user.setUser_gender(preferences.getString(U_GEND, ""));
        user.setUser_imgURL(preferences.getString(U_IMG_URL, ""));
        user.setUser_imgURL(preferences.getString(U_PASS, ""));
        return user;
    }

}
