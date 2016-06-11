package com.ryanzhou.company.movieviewer.homeGrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.detailMovie.MovieDetailsActivity;
import com.ryanzhou.company.movieviewer.model.Movie;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnListFragmentInteractionListener{

    public final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            //Nothing saved, initial creation
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_grid_container, new MovieFragment()).commit();
        }
    }
    @Override
    public void onListFragmentInteraction(Movie m){
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(Movie.MOVIE_ITEM_KEY, m);
        startActivity(intent);
    }
}
