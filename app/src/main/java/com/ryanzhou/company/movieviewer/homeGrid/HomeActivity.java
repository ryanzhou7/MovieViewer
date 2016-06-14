package com.ryanzhou.company.movieviewer.homeGrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.movieDetails.MovieDetailsActivity;

public class HomeActivity extends AppCompatActivity implements MovieGridFragment.OnListFragmentInteractionListener{

    public final String LOG_TAG = this.getClass().getSimpleName();
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            //Nothing saved, initial creation
            MovieGridFragment mgf = new MovieGridFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_grid_container, mgf)
                    .commit();
        }
    }
    @Override
    public void onListFragmentInteraction(Movie m){
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra(Movie.MOVIE_ITEM_KEY, m);
        startActivity(intent);
    }

}
