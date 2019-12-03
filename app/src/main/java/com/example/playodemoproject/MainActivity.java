package com.example.playodemoproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.playodemoproject.MainActivityModule.MainActivityContract;
import com.example.playodemoproject.MainActivityModule.MainPresenter;
import com.example.playodemoproject.Util.AppNetworkStatus;
import com.example.playodemoproject.datamodule.NewsListAdapter;
import com.example.playodemoproject.datamodule.NewsRemoteDataSource;
import com.example.playodemoproject.datamodule.NewsRepository;
import com.example.playodemoproject.models.Hit;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainActivityContract.view {
    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private EditText searchView;
    private Button searchButton;
    private NewsListAdapter newsListAdapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this, NewsRepository.getInstance(NewsRemoteDataSource.getInstance()));
        presenter.start();
        presenter.getNewsList(new AppNetworkStatus(this));
    }

    @Override
    public void showNetworkError(boolean active) {

        if (active) {
            Toast.makeText(this, getResources().getString(R.string.connection_problem), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setUpUI() {
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        searchButton = findViewById(R.id.searchButton);
        dialog = new ProgressDialog(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchView.getText().toString() == null || searchView.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please Enter The Text", Toast.LENGTH_LONG).show();
                } else {
                    presenter.getSearchNewsList(searchView.getText().toString(), new AppNetworkStatus(MainActivity.this));
                }
            }
        });
    }

    @Override
    public void showLoadingIndicator(boolean active) {

        if (active) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showList(List<Hit> hitList) {
        newsListAdapter = new NewsListAdapter(hitList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        newsListAdapter.setHasStableIds(true);
        recyclerView.setAdapter(newsListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public String getErrorString(int resourceId) {
        return getResources().getString(resourceId);
    }

    @Override
    public void showSearchList(List<Hit> hitList) {
        newsListAdapter.setItemModelList(hitList);
    }

    @Override
    public void setPresenter(MainActivityContract.presenter presenter) {
        this.presenter = (MainPresenter) presenter;
    }
}
