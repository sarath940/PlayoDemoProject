package com.example.playodemoproject.MainActivityModule;



import com.example.playodemoproject.R;
import com.example.playodemoproject.Util.ErrorCode;
import com.example.playodemoproject.Util.NetworkStatus;
import com.example.playodemoproject.datamodule.NewsDataSource;
import com.example.playodemoproject.datamodule.NewsRepository;
import com.example.playodemoproject.models.Hit;

import java.util.List;

public class MainPresenter implements MainActivityContract.presenter {

    private MainActivityContract.view view;
    private NewsRepository repository;

    public MainPresenter(MainActivityContract.view view, NewsRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        view.setUpUI();
    }

    @Override
    public void getNewsList(NetworkStatus networkStatus) {
        view.showLoadingIndicator(true);
        repository.getNewsSearch(networkStatus, "", new NewsDataSource.LoadCallBackListener() {
            @Override
            public void onLoaded(Object response) {
                view.showLoadingIndicator(false);
                view.showList((List<Hit>) response);
            }

            @Override
            public void onError(Object error) {
                view.showLoadingIndicator(false);
                if ((int) error == ErrorCode.NETWORK_ERROR) {
                    view.showNetworkError(true);
                } else {
                    view.showError(filterError(error));
                }
            }
        });
    }

    @Override
    public void getSearchNewsList(String search, NetworkStatus networkStatus) {
        view.showLoadingIndicator(true);
        repository.getNewsSearch(networkStatus, search, new NewsDataSource.LoadCallBackListener() {
            @Override
            public void onLoaded(Object response) {
                view.showLoadingIndicator(false);
                view.showSearchList((List<Hit>) response);
            }

            @Override
            public void onError(Object error) {
                view.showLoadingIndicator(false);
                if ((int) error == ErrorCode.NETWORK_ERROR) {
                    view.showNetworkError(true);
                } else {
                    view.showError(filterError(error));
                }
            }
        });
    }

    @Override
    public String filterError(Object errorCode) {
        String errorMsg;
        switch ((int) errorCode) {
            case ErrorCode.CONNECTION_PROBLEM:
                errorMsg = view.getErrorString(R.string.connection_problem);
                break;
            case ErrorCode.NETWORK_ERROR:
                errorMsg = view.getErrorString(R.string.network_problem);
                break;

            case ErrorCode.NO_RESULT:
                errorMsg = view.getErrorString(R.string.no_result);
                break;
            default:
                errorMsg = view.getErrorString(R.string.connection_problem);
                break;
        }
        return errorMsg;
    }
}
