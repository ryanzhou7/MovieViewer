package com.ryanzhou.company.movieviewer.movieDetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.MovieReview;
import com.ryanzhou.company.movieviewer.model.MovieTrailer;
import com.ryanzhou.company.movieviewer.movieDetails.overviewTab.MovieOverviewFragment;
import com.ryanzhou.company.movieviewer.movieDetails.reviewsTab.MovieReviewsFragment;
import com.ryanzhou.company.movieviewer.movieDetails.trailersTab.MovieTrailersFragment;

public class MovieDetailsActivity extends AppCompatActivity implements MovieOverviewFragment.OnFragmentInteractionListener,
MovieReviewsFragment.OnListFragmentInteractionListener, MovieTrailersFragment.OnListFragmentInteractionListener{

    private final String LOG_TAG = this.getClass().getSimpleName();
    private MovieDetailPagerAdapter mMovieDetailPagerAdapter;
    private TabLayout mMovieTabLayout;
    ViewPager mViewPagerMovieDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //from http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
        mViewPagerMovieDetails = (ViewPager) findViewById(R.id.view_pager);
        mMovieTabLayout = (TabLayout) findViewById(R.id.tabs);
        MovieDetailPagerAdapter pagerAdapter = new MovieDetailPagerAdapter(getSupportFragmentManager());

        Bundle receivedData = getIntent().getExtras();

        MovieOverviewFragment movieOverviewFragment = MovieOverviewFragment.newInstance(receivedData);
        pagerAdapter.addFragment(movieOverviewFragment, MovieOverviewFragment.TAB_NAME);

        MovieReviewsFragment movieReviewsFragment = MovieReviewsFragment.newInstance(receivedData);
        pagerAdapter.addFragment(movieReviewsFragment, MovieReviewsFragment.TAB_NAME);

        MovieTrailersFragment movieTrailersFragment = MovieTrailersFragment.newInstance(receivedData);
        pagerAdapter.addFragment(movieTrailersFragment, MovieTrailersFragment.TAB_NAME);

        mViewPagerMovieDetails.setAdapter(pagerAdapter);
        mMovieTabLayout.setupWithViewPager(mViewPagerMovieDetails);

    }

    @Override
    public void onMoviewReviewSelected(MovieReview mr) {

    }

    @Override
    public void onMoviewTrailerSelected(MovieTrailer mt) {

    }

    @Override
    public void saveMovieAsFavorite(Movie m) {

    }
}
