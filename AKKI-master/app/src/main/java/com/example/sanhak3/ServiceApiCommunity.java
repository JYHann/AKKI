package com.example.sanhak3;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApiCommunity {
    @POST("/user/ComuMenu")
    Call<CommunityResponse> getComuMenu();

//    @POST("/user/join")
//    Call<JoinResponse> userJoin(@Body String data);
}
