package com.example.playodemoproject.MainActivityModule;

import android.content.Context;

import com.example.playodemoproject.R;
import com.example.playodemoproject.Util.NetworkStatus;
import com.example.playodemoproject.datamodule.NewsRepository;
import com.example.playodemoproject.models.Hit;
import com.example.playodemoproject.models.NewsModelResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {
    private MainPresenter mainPresenter;

    @Mock
    NewsModelResponse newsModelResponse;

    @Mock
    Hit hit;

    @Mock
    NetworkStatus networkStatus;
    @Mock
    private NewsRepository newsRepository;

    @Mock
    private MainActivityContract.view mainView;

    @Mock
    private Context context;

    @Captor
    private ArgumentCaptor<NewsRepository.LoadCallBackListener> loadPostsCallbackCaptor;
    private List<Hit> hits1;

    @Before
    public void setupCardsPresenter() {
        MockitoAnnotations.initMocks(this);
        // Get a reference to the class under test
        mainPresenter = new MainPresenter(mainView, newsRepository);

    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mainPresenter = new MainPresenter(mainView, newsRepository);
        // Then the presenter is set to the view
        verify(mainView).setPresenter(mainPresenter);
    }

    @Test
    public void socialPostsAreLoadedAndDisplayed() {
        // make call
        mainPresenter.getNewsList(networkStatus);

        // Callback is captured and invoked with stubbed cards
        verify(newsRepository).getNewsSearch(eq(networkStatus),eq(""),loadPostsCallbackCaptor.capture());
        hits1 = newsModelResponse.getHits();
        loadPostsCallbackCaptor.getValue().onLoaded(hits1);

        // Then progress indicator is hidden posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mainView);
        inOrder.verify(mainView).showLoadingIndicator(true);
        inOrder.verify(mainView).showLoadingIndicator(false);
        verify(mainView).showList(hits1);
    }

    @Test
    public void socialPostsLoadedNetworkErrorDisplayed() {

        // make call
        mainPresenter.getNewsList(networkStatus);
        // Callback is captured and invoked with stubbed cards
        verify(newsRepository).getNewsSearch(eq(networkStatus),eq(""),loadPostsCallbackCaptor.capture());
        hits1 = newsModelResponse.getHits();
        loadPostsCallbackCaptor.getValue().onError(100);

        // Then progress indicator is hidden posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mainView);
        inOrder.verify(mainView).showLoadingIndicator(true);
        inOrder.verify(mainView).showLoadingIndicator(false);
        verify(mainView).showNetworkError(true);
    }


    @Test
    public void socialPostsLoadedNoResultErrorDisplayed() {

        when(mainView.getErrorString(R.string.connection_problem)).thenReturn("connection problem");
        // make call
        mainPresenter.getNewsList(networkStatus);
        // Callback is captured and invoked with stubbed cards
        verify(newsRepository).getNewsSearch(eq(networkStatus),eq(""),loadPostsCallbackCaptor.capture());
        hits1 = newsModelResponse.getHits();
        loadPostsCallbackCaptor.getValue().onError(200);

        // Then progress indicator is hidden posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mainView);
        inOrder.verify(mainView).showLoadingIndicator(true);
        inOrder.verify(mainView).showLoadingIndicator(false);
        verify(mainView).showError("connection problem");
    }


    @Test
    public void searchSocialPostsAreLoadedAndDisplayed() {
        // make call
        mainPresenter.getSearchNewsList("news",networkStatus);

        // Callback is captured and invoked with stubbed cards
        verify(newsRepository).getNewsSearch(eq(networkStatus),eq("news"),loadPostsCallbackCaptor.capture());
        hits1 = newsModelResponse.getHits();
        loadPostsCallbackCaptor.getValue().onLoaded(hits1);

        // Then progress indicator is hidden posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mainView);
        inOrder.verify(mainView).showLoadingIndicator(true);
        inOrder.verify(mainView).showLoadingIndicator(false);
        verify(mainView).showSearchList(hits1);
    }

    @Test
    public void searchSocialPostsLoadedNetworkErrorDisplayed() {

        // make call
        mainPresenter.getSearchNewsList("news",networkStatus);
        // Callback is captured and invoked with stubbed cards
        verify(newsRepository).getNewsSearch(eq(networkStatus),eq("news"),loadPostsCallbackCaptor.capture());
        hits1 = newsModelResponse.getHits();
        loadPostsCallbackCaptor.getValue().onError(100);

        // Then progress indicator is hidden posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mainView);
        inOrder.verify(mainView).showLoadingIndicator(true);
        inOrder.verify(mainView).showLoadingIndicator(false);
        verify(mainView).showNetworkError(true);
    }


    @Test
    public void searchSocialPostsLoadedNoResultErrorDisplayed() {

        when(mainView.getErrorString(R.string.no_result)).thenReturn("no result");
        // make call
        mainPresenter.getSearchNewsList("news",networkStatus);
        // Callback is captured and invoked with stubbed cards
        verify(newsRepository).getNewsSearch(eq(networkStatus),eq("news"),loadPostsCallbackCaptor.capture());
        hits1 = newsModelResponse.getHits();
        loadPostsCallbackCaptor.getValue().onError(300);

        // Then progress indicator is hidden posts are shown in UI
        InOrder inOrder = Mockito.inOrder(mainView);
        inOrder.verify(mainView).showLoadingIndicator(true);
        inOrder.verify(mainView).showLoadingIndicator(false);
        verify(mainView).showError("no result");
    }
}