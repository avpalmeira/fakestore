package com.honeywell.rtcmobile.api.service;

import com.honeywell.rtcmobile.api.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserClient {

    @GET("/rest.php")
    Call<User> getUser(

            @Header("Authorization")
            String authHeader
    );
}
