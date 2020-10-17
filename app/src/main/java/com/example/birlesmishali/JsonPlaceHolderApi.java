package com.example.birlesmishali;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    //@Headers({"authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ikc3SEFpVGRrb3VzWnF3cDlOYlRkaitETllXWE5aSVFhaVJ1QzI1aVFoaDA9IiwibmJmIjoxNjAxODE4NDQyLCJleHAiOjE2MDE5MDQ4NDIsImlhdCI6MTYwMTgxODQ0Mn0.MTEnpCNIEG2IbrzMqr-tEZd27hkPmLF7LHwWQ1rwzng"})
    @GET("api/categories") //"posts" is a relative url
    Call<TopLevel> getTopLevel();


/*
    // retrofit website
    @GET("secretinfo")
    Call<ResponseBody> getSecret(@Header("Authorization") String authToken);


 */



}
