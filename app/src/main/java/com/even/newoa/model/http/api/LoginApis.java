package com.even.newoa.model.http.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApis {

    String HOST = "http://192.168.2.157:8080";

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/tokens")
    Call<ResponseBody> login(@Body RequestBody body);

//    @Headers({"Content-Type: application/json","Accept: application/json"})
//    @POST("/tokens")
//    Observable<String> login(@Body RequestBody  body);
}
