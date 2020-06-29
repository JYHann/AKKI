package com.example.sanhak3;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContentResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("contentList")
    private ArrayList<JsonObject> contentlist;

    @SerializedName("userId")
    private int userId;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<JsonObject> getContentlist() { return contentlist; }

    public int getUserId() {
        return userId;
    }
}
