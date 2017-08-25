package com.example.faiz.vividways.Models;

/**
 * Created by AST on 8/18/2017.
 */

public class TopItems {
    public UserModel userModel;
    public ItemObject itemObject;


    public TopItems() {
    }

    public TopItems(UserModel userModel, ItemObject itemObject) {
        this.userModel = userModel;
        this.itemObject = itemObject;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ItemObject getItemObject() {
        return itemObject;
    }

    public void setItemObject(ItemObject itemObject) {
        this.itemObject = itemObject;
    }
}
