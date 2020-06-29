package com.example.sanhak3;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApiCommunity {
    @POST("/user/ComuMenu")
    Call<CommunityResponse> getComuMenu();

    @POST("/user/ComuContent")
    Call<ContentResponse> getContent(@Body JsonObject data);
//    @Body String data

    @POST("/user/ComuNewArticle")
    Call<ContentNewResponse> newArticle(@Body ContentData data);

    @POST("/user/ComuArticle")
    Call<ArticleResponse> getArticle(@Body JsonObject data);

    @POST("/user/ComuComment")
    Call<CommentResponse> getComment(@Body JsonObject data);

    @POST("/user/ComuNewComment")
    Call<ContentNewResponse> newComment(@Body ContentData data);

//    @POST("/user/join")
//    Call<JoinResponse> userJoin(@Body String data);
}
