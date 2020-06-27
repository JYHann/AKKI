package com.example.sanhak3;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("menulist")
    private ArrayList<JsonObject> menulist;

    @SerializedName("userId")
    private int userId;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<JsonObject> getMenulist() { return menulist; }

    public int getUserId() {
        return userId;
    }
}
