package com.example.playodemoproject.network;


import com.example.playodemoproject.models.NewsModelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("api/v1/search/")
    Call<NewsModelResponse> searchNews(@Query("query") String searchQuery);

}
