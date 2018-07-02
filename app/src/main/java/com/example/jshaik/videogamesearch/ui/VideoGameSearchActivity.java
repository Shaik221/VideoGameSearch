package com.example.jshaik.videogamesearch.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jshaik.videogamesearch.R;
import com.example.jshaik.videogamesearch.VideoGameSearchApplication;
import com.example.jshaik.videogamesearch.di.component.DaggerApplicationComponent;
import com.example.jshaik.videogamesearch.di.contract.GamesListContract;
import com.example.jshaik.videogamesearch.di.module.ApplicationModule;
import com.example.jshaik.videogamesearch.model.beans.GamesResultsData;
import com.example.jshaik.videogamesearch.model.adapter.CustomSearchAdapter;
import com.example.jshaik.videogamesearch.presenter.GamesListPresenter;
import com.example.jshaik.videogamesearch.utils.ImageHandler;
import com.example.jshaik.videogamesearch.utils.NetworkHandler;

import java.util.List;

import javax.inject.Inject;


public class VideoGameSearchActivity extends AppCompatActivity implements GamesListContract.View {

    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private TextView textView;

    @Inject
    GamesListPresenter gamesListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_layout);

        DaggerApplicationComponent.builder()
                .netComponent(((VideoGameSearchApplication)getApplication().getApplicationContext()).getNetComponent())
                .applicationModule(new ApplicationModule(this))
                .build().inject(this);

        //setting picaso builder as single instance for whole application
        ImageHandler.setCustomPicasoBuilder(getApplicationContext());

        recyclerView = findViewById(R.id.listView);
        textView = findViewById(R.id.noData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VideoGameSearchActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.pdig_message));
        // search
        handleSearch();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch();
    }

    private void handleSearch() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);

            //check internet connectiviy and then fire the request
            if(NetworkHandler.isNetworkAvailable(this)) {
                pDialog.show();
                //calling service to get the list of games
                gamesListPresenter.loadGamesList(searchQuery);
            }
            else
            {
                Toast.makeText(this, "No Network!!", Toast.LENGTH_SHORT).show();
            }

        }
        else if(Intent.ACTION_VIEW.equals(intent.getAction())) {
            String selectedSuggestionRowId =  intent.getDataString();
            //execution comes here when an item is selected from search suggestions
            //you can continue from here with user selected search item
            Toast.makeText(this, "selected search suggestion "+selectedSuggestionRowId,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(String message) {
        pDialog.cancel();
    }

    @Override
    public void showComplete() {
        pDialog.cancel();
    }

    @Override
    public void showGameDetailsList(List<GamesResultsData> gameDetailsList)
    {
        setGameDetails(gameDetailsList);
    }


    /* Method to show List of games using RecyclerView with custom adapter*/
    private void setGameDetails(List<GamesResultsData> noticeArrayList) {
        if (noticeArrayList != null && noticeArrayList.size() > 0 ) {
            textView.setVisibility(View.GONE);
            CustomSearchAdapter adapter = new CustomSearchAdapter(VideoGameSearchActivity.this, noticeArrayList);
            recyclerView.setAdapter(adapter);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Data Found!!");
        }
    }

}
