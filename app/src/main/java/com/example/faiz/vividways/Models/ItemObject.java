package com.example.faiz.vividways.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Faiz on 7/20/2017.
 */

public class ItemObject implements Parcelable {
    public String itemID;
    public String itemImageURl;
    public int leaveit_count;
    public int takeit_count;
    public String userID;
    public String notification_text;
    public String caption;
    public boolean take_it_check;
    public boolean leave_it_check;

    public ItemObject() {
    }


    public ItemObject(String itemID, String itemImageURl, boolean take_it_check, boolean leave_it_check,String userID) {
        this.itemID = itemID;
        this.itemImageURl = itemImageURl;
        this.take_it_check = take_it_check;
        this.leave_it_check = leave_it_check;
        this.userID = userID;
    }


    public ItemObject(String itemID, String itemImageURl, int leaveit_count, int takeit_count, String userID, String caption) {
        this.itemID = itemID;
        this.itemImageURl = itemImageURl;
        this.leaveit_count = leaveit_count;
        this.takeit_count = takeit_count;
        this.userID = userID;
        this.caption = caption;
    }


    public boolean isTake_it_check() {
        return take_it_check;
    }

    public boolean isLeave_it_check() {
        return leave_it_check;
    }

    protected ItemObject(Parcel in) {
        itemID = in.readString();
        itemImageURl = in.readString();
        leaveit_count = in.readInt();
        takeit_count = in.readInt();
        userID = in.readString();
        notification_text = in.readString();
        caption = in.readString();
        take_it_check = in.readByte() != 0;
        leave_it_check = in.readByte() != 0;
    }

    public static final Creator<ItemObject> CREATOR = new Creator<ItemObject>() {
        @Override
        public ItemObject createFromParcel(Parcel in) {
            return new ItemObject(in);
        }

        @Override
        public ItemObject[] newArray(int size) {
            return new ItemObject[size];
        }
    };

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public ItemObject(String itemID, String itemImageURl) {
        this.itemID = itemID;
        this.itemImageURl = itemImageURl;
    }


    public String getItemID() {
        return itemID;
    }


    public String getItemImageURl() {
        return itemImageURl;
    }


    public int getLeaveit_count() {
        return leaveit_count;
    }


    public int getTakeit_count() {
        return takeit_count;
    }

    public void setLeaveit_count(int leaveit_count) {
        this.leaveit_count = leaveit_count;
    }

    public void setTakeit_count(int takeit_count) {
        this.takeit_count = takeit_count;
    }

    public String getUserID() {
        return userID;
    }


    public String getNotification_text() {
        return notification_text;
    }

    public void setNotification_text(String notification_text) {
        this.notification_text = notification_text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(itemID);
        out.writeString(itemImageURl);
        out.writeInt(leaveit_count);
        out.writeInt(takeit_count);
        out.writeString(userID);
    }
}
