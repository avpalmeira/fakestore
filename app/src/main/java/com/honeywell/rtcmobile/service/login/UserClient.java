package com.honeywell.rtcmobile.service.login;

import com.honeywell.rtcmobile.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @GET("/index.php")
    Call<User> getUser(

            @Header("Authorization")
            String authHeader
    );

    @POST("/api/v1/auth/login")
    Call<User> login(

            @Body
            User user
    );
}
