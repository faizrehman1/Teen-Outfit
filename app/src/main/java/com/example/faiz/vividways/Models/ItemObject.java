package com.example.faiz.vividways.Models;

/**
 * Created by Faiz on 7/20/2017.
 */

public class ItemObject {
    public String itemID;
    public String itemImageURl;
    public boolean leaveit;
    public boolean takeit;
    public String userID;
    public String notification_text;


    public ItemObject(String itemID, String itemImageURl, boolean leaveit, boolean takeit, String userID) {
        this.itemID = itemID;
        this.itemImageURl = itemImageURl;
        this.leaveit = leaveit;
        this.takeit = takeit;
        this.userID = userID;
    }

    public ItemObject() {
    }



    public ItemObject(String itemID, String itemImageURl) {
        this.itemID = itemID;
        this.itemImageURl = itemImageURl;
    }



    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemImageURl() {
        return itemImageURl;
    }

    public void setItemImageURl(String itemImageURl) {
        this.itemImageURl = itemImageURl;
    }
}
