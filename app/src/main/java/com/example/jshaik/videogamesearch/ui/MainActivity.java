package com.example.jshaik.videogamesearch.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.jshaik.videogamesearch.R;
import com.example.jshaik.videogamesearch.model.adapter.ImageAdapterGridView;

public class MainActivity extends AppCompatActivity {
    GridView androidGridView;
    ImageAdapterGridView imageAadapter;

    String[] gridViewString = {
            "Adventure", "Education", "Puzzle", "War", "Simulations", "Sports",
            "Olympic",
    } ;

    Integer[] imageIDs = {
            R.drawable.adventure, R.drawable.education, R.drawable.puzzle_games,
            R.drawable.war, R.drawable.simulations, R.drawable.sports,
            R.drawable.water_games,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.category_text);

        androidGridView = (GridView) findViewById(R.id.gridview_images);
        imageAadapter = new ImageAdapterGridView(this, gridViewString, imageIDs);
        androidGridView.setAdapter(imageAadapter);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                //firing the intent if grid item selected
                Intent searchIntent = new Intent(getApplicationContext(), VideoGameSearchActivity.class);
                searchIntent.setAction(Intent.ACTION_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, gridViewString[+ position]);
                startActivity(searchIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_m:
                //start search dialog
                super.onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}