package com.ryanzhou.company.movieviewer.detailMovie;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ryanzhou.company.movieviewer.R;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsFragment.OnFragmentInteractionListener {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private MovieDetailPagerAdapter mMovieDetailPagerAdapter;
    private TabLayout mMovieTabLayout;
    private Toolbar mToolbar;
    ViewPager mViewPagerMovieDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //from http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
        mViewPagerMovieDetails = (ViewPager) findViewById(R.id.view_pager);
        mMovieTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPagerMovieDetails.setAdapter(new MovieDetailPagerAdapter(getSupportFragmentManager()));
        mMovieTabLayout.setupWithViewPager(mViewPagerMovieDetails);

        //Code from https://developer.android.com/training/basics/fragments/fragment-ui.html
        if (savedInstanceState == null){
            //no data saved
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //favorited movie
    }
}
