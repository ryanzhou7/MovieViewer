package com.ryanzhou.company.movieviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ryanzhou.company.movieviewer.model.Movie;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnListFragmentInteractionListener{

    public final String LOG_TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_grid_container, new MovieFragment())
                        .commit();
        }
    }
    @Override
    public void onListFragmentInteraction(Movie m){
        //we have the item that we clicked
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.TITLE, m.getmOriginalTitle());
        intent.putExtra(MovieDetailsActivity.PLOT_SYNOPSIS, m.getmSynopsis());
        intent.putExtra(MovieDetailsActivity.POSTER_IMAGE_URL, m.getmImagePath());
        intent.putExtra(MovieDetailsActivity.USER_RATING, m.getmUserRating());
        intent.putExtra(MovieDetailsActivity.RELEASE_DATE, m.getmReleaseDate());
        startActivity(intent);
    }

}
