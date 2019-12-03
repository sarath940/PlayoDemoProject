package com.example.playodemoproject.datamodule;


import com.example.playodemoproject.Util.ErrorCode;
import com.example.playodemoproject.Util.NetworkStatus;
import com.example.playodemoproject.models.NewsModelResponse;
import com.example.playodemoproject.network.NewsService;
import com.example.playodemoproject.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsRemoteDataSource implements NewsDataSource {

    private static NewsRemoteDataSource ourInstance;
    private NewsService apiService;

    private NewsRemoteDataSource() {
    }

    public static NewsRemoteDataSource getInstance() {
        if (ourInstance == null) {
            ourInstance = new NewsRemoteDataSource();
        }
        return ourInstance;
    }


    @Override
    public void getNewsSearch(NetworkStatus networkStats, String query, final LoadCallBackListener callBackListener) {
        apiService = RetrofitClient.getClient().create(NewsService.class);

        Call<NewsModelResponse> call = apiService.searchNews(query);
        call.enqueue(new Callback<NewsModelResponse>() {
            @Override
            public void onResponse(Call<NewsModelResponse> call, Response<NewsModelResponse> response) {
                if (response.isSuccessful()) {
                    callBackListener.onLoaded(response.body());
                } else {
                    callBackListener.onError(ErrorCode.CONNECTION_PROBLEM);

                }
            }

            @Override
            public void onFailure(Call<NewsModelResponse> call, Throwable t) {
                callBackListener.onError(ErrorCode.CONNECTION_PROBLEM);
            }
        });
    }
}
