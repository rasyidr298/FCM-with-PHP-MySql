package com.rrdev.fcmwithmysql.ApiService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("Login.php")
    Call<String> loginRequest(
            @Field("username") String username,
            @Field("password") String password);
    @FormUrlEncoded
    @POST("RegisterDevice.php")
    Call<String> registerRequest
            (@Field("username") String username,
             @Field("password") String password,
             @Field("token") String token);
}
