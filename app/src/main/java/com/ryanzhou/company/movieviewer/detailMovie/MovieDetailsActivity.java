package com.ryanzhou.company.movieviewer.detailMovie;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ryanzhou.company.movieviewer.R;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsFragment.OnFragmentInteractionListener {

    private final String LOG_TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Code from https://developer.android.com/training/basics/fragments/fragment-ui.html
        if (findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) return;
            MovieDetailsFragment mdf = MovieDetailsFragment.newInstance( getIntent().getExtras() );
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mdf).commit();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //favorited movie
    }
}
