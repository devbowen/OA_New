package com.even.newoa.model.http.api;

import com.even.newoa.model.bean.LoginResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EmployeeApis {

    String HOST = "http://192.168.2.157:8080";

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/employee?offset=2&limit=3")
    Observable<LoginResponse> getAll(@Body RequestBody body);
}
