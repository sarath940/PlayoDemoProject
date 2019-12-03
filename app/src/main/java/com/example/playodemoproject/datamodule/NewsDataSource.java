package com.example.playodemoproject.datamodule;


import com.example.playodemoproject.Util.NetworkStatus;

public interface NewsDataSource {


    void getNewsSearch(NetworkStatus networkStats, String query, LoadCallBackListener callBackListener);

    interface LoadCallBackListener {
        void onLoaded(Object response);

        void onError(Object error);
    }
}
