package com.ryanzhou.company.movieviewer.movieDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ryanzhou.company.movieviewer.R;

public class MovieDetailsActivity extends AppCompatActivity{

    private final String LOG_TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle receivedData = getIntent().getExtras();
        MovieDetailsFragmentsContainer movieDetailsFragmentsContainer =
                MovieDetailsFragmentsContainer.newInstance(receivedData);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_details_fragments_container, movieDetailsFragmentsContainer)
                .commit();

    }
}
