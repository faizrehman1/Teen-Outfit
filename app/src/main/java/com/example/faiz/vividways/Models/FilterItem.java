package com.example.faiz.vividways.Models;

/**
 * Created by AST on 8/3/2017.
 */

public class FilterItem {


    public String can_see;
    public String want_see;


    public FilterItem() {
    }

    public FilterItem(String can_see, String want_see) {
        this.can_see = can_see;
        this.want_see = want_see;
    }

    public String getCan_see() {
        return can_see;
    }

    public void setCan_see(String can_see) {
        this.can_see = can_see;
    }

    public String getWant_see() {
        return want_see;
    }

    public void setWant_see(String want_see) {
        this.want_see = want_see;
    }

    private static FilterItem myObj;
    public static FilterItem getInstance(String can_see, String want_see){
        if(myObj == null){
            myObj = new FilterItem(can_see, want_see);
        }
        return myObj;
    }

    public static FilterItem getInstanceIfNotNull(){
        return myObj;
    }

}
