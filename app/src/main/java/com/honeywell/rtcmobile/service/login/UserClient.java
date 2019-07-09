package com.honeywell.rtcmobile.service.login;

import com.honeywell.rtcmobile.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserClient {

    @GET("/index.php")
    Call<User> getUser(

            @Header("Authorization")
            String authHeader
    );
}
