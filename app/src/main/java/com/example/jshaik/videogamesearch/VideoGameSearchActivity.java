package com.example.jshaik.videogamesearch;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jshaik.videogamesearch.adapter.CustomSearchAdapter;
import com.example.jshaik.videogamesearch.beans.GamesListData;
import com.example.jshaik.videogamesearch.beans.GamesResultsData;
import com.example.jshaik.videogamesearch.service.GetNoticeDataService;
import com.example.jshaik.videogamesearch.service.RetrofitInstance;
import com.example.jshaik.videogamesearch.utils.Constants;
import com.example.jshaik.videogamesearch.utils.ImageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*Retrofit*/
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoGameSearchActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private TextView textView;
    private CustomSearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting picaso builder as single instance for whole application
        ImageHandler.setCustomPicasoBuilder(getApplicationContext());

        setContentView(R.layout.searchable_layout);
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
        pDialog.show();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);

            Map<String, String> params = new HashMap<String, String>();
            params.put("api_key", Constants.API_KEY);
            params.put("format", Constants.SCHEMA_TYPE);
            params.put("query", searchQuery);
            params.put("limit", Constants.PAGE_LIMIT);
            params.put("resources", Constants.RESOURCE_TYPE);

            /** Create handle for the RetrofitInstance interface*/
            GetNoticeDataService service = RetrofitInstance.getRetrofitInstance().create(GetNoticeDataService.class);

            /** Call the method with parameter in the interface to get the notice data*/
            Call<GamesListData> call = service.gamesList(Constants.BASE_URL, params);

            /**Log the URL called*/
            Log.d("URL Called", call.request().url() + "");

            call.enqueue(new Callback<GamesListData>() {
                @Override
                public void onResponse(Call<GamesListData> call, Response<GamesListData> response) {
                    pDialog.cancel();
                    gamesListData(response.body().getResults());
                }

                @Override
                public void onFailure(Call<GamesListData> call, Throwable t) {
                    pDialog.cancel();
                    Toast.makeText(VideoGameSearchActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(Intent.ACTION_VIEW.equals(intent.getAction())) {
            String selectedSuggestionRowId =  intent.getDataString();
            //execution comes here when an item is selected from search suggestions
            //you can continue from here with user selected search item
            Toast.makeText(this, "selected search suggestion "+selectedSuggestionRowId,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /* Method to show List of games using RecyclerView with custom adapter*/
    private void gamesListData(List<GamesResultsData> noticeArrayList) {
        if (noticeArrayList != null && noticeArrayList.size() > 0 ) {
            textView.setVisibility(View.GONE);
            adapter = new CustomSearchAdapter(VideoGameSearchActivity.this, noticeArrayList);
            recyclerView.setAdapter(adapter);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Data Found!!");
        }
    }

}
