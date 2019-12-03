package com.example.playodemoproject.datamodule;


import com.example.playodemoproject.Util.ErrorCode;
import com.example.playodemoproject.Util.NetworkStatus;
import com.example.playodemoproject.models.Hit;
import com.example.playodemoproject.models.NewsModelResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsRepository implements NewsDataSource {
    private static NewsRepository ourInstance;
    private NewsRemoteDataSource remoteDataSource;
    private HashMap<String, Boolean> isServiceLoading;
    private List<Hit> newsList;


    private NewsRepository(NewsRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.isServiceLoading = new HashMap<>();
        this.isServiceLoading.put("getNewsSearch", false);
        newsList = new ArrayList<>();


    }

    public static NewsRepository getInstance(NewsRemoteDataSource remoteDataSource) {
        if (ourInstance == null) {
            ourInstance = new NewsRepository(remoteDataSource);
        }
        return ourInstance;
    }

    @Override
    public void getNewsSearch(NetworkStatus networkStats, String query, final LoadCallBackListener callBackListener) {
        if (networkStats.isOnline()) {
            if (!isServiceLoading.get("getNewsSearch")) {
                remoteDataSource.getNewsSearch(networkStats, query, new LoadCallBackListener() {
                    @Override
                    public void onLoaded(Object response) {
                        NewsModelResponse imResponse = (NewsModelResponse) response;
                        newsList.clear();
                        if (imResponse.getHits() != null && imResponse.getHits().size() > 0) {
                            newsList.addAll(imResponse.getHits());
                        }
                        callBackListener.onLoaded(imResponse.getHits());
                    }

                    @Override
                    public void onError(Object error) {
                        callBackListener.onError(error);
                    }
                });
            }
        } else {
            callBackListener.onError(ErrorCode.NETWORK_ERROR);
        }
    }
}
