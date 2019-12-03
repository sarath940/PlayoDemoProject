package com.example.playodemoproject.MainActivityModule;


import com.example.playodemoproject.BasePresenter;
import com.example.playodemoproject.BaseView;
import com.example.playodemoproject.Util.NetworkStatus;
import com.example.playodemoproject.models.Hit;

import java.util.List;

public interface MainActivityContract {

    interface view extends BaseView<presenter> {

        void showNetworkError(boolean active);

        void setUpUI();

        void showLoadingIndicator(boolean active);

        void showError(String errorMsg);

        void showList(List<Hit> hitList);

        String getErrorString(int resourceId);

        void showSearchList(List<Hit> hitList);

    }

    interface presenter extends BasePresenter {
        void getNewsList(NetworkStatus networkStatus);

        void getSearchNewsList(String search, NetworkStatus networkStatus);

        String filterError(Object errorCode);
    }
}
