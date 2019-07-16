package com.honeywell.rtcmobile.service.login;

import com.honeywell.rtcmobile.model.Product;
import com.honeywell.rtcmobile.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @GET("/api/v1/auth/products")
    Call<List<List<Product>>> getProducts(

            @Header("Authorization")
            String authHeader
    );

    @POST("/api/v1/auth/login")
    Call<User> login(

            @Body
            User user
    );
}
