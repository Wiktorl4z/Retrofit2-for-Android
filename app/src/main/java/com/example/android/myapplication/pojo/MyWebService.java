package com.example.android.myapplication.pojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyWebService {
    @GET("/wsexample") // deklarujemy endpoint oraz metodę
    Call<DataBody>  getData();

    @POST("/wsexample") // deklarujemy endpoint, metodę oraz dane do wysłania
    Call<DataBody> postData(@Body DataBody pBody);
}
