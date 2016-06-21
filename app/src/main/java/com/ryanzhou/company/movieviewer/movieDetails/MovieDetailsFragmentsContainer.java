package com.ryanzhou.company.movieviewer.movieDetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.MovieReview;
import com.ryanzhou.company.movieviewer.model.MovieTrailer;
import com.ryanzhou.company.movieviewer.movieDetails.overviewTab.MovieOverviewFragment;
import com.ryanzhou.company.movieviewer.movieDetails.reviewsTab.MovieReviewsFragment;
import com.ryanzhou.company.movieviewer.movieDetails.trailersTab.MovieTrailersFragment;

public class MovieDetailsFragmentsContainer extends Fragment implements MovieOverviewFragment.OnFragmentInteractionListener,
MovieReviewsFragment.OnListFragmentInteractionListener, MovieTrailersFragment.OnListFragmentInteractionListener{

    private final String LOG_TAG = this.getClass().getSimpleName();
    private MovieDetailPagerAdapter mMovieDetailPagerAdapter;
    private TabLayout mMovieTabLayout;
    ViewPager mViewPagerMovieDetails;

    public static MovieDetailsFragmentsContainer newInstance( Bundle bundle ){
        MovieDetailsFragmentsContainer movieDetailsFragmentsContainer =
                new MovieDetailsFragmentsContainer();
        movieDetailsFragmentsContainer.setArguments(bundle);
        return movieDetailsFragmentsContainer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details_fragments_container, container, false);
        //from http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
        mViewPagerMovieDetails = (ViewPager) view.findViewById(R.id.view_pager);
        mMovieTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        MovieDetailPagerAdapter pagerAdapter = new MovieDetailPagerAdapter(
                getChildFragmentManager()
        );

        Bundle receivedData = getArguments();

        MovieOverviewFragment movieOverviewFragment = MovieOverviewFragment.newInstance(receivedData);
        pagerAdapter.addFragment(movieOverviewFragment, MovieOverviewFragment.TAB_NAME);

        MovieReviewsFragment movieReviewsFragment = MovieReviewsFragment.newInstance(receivedData);
        pagerAdapter.addFragment(movieReviewsFragment, MovieReviewsFragment.TAB_NAME);

        MovieTrailersFragment movieTrailersFragment = MovieTrailersFragment.newInstance(receivedData);
        pagerAdapter.addFragment(movieTrailersFragment, MovieTrailersFragment.TAB_NAME);

        mViewPagerMovieDetails.setAdapter(pagerAdapter);
        mMovieTabLayout.setupWithViewPager(mViewPagerMovieDetails);
        return view;

    }

    @Override
    public void onMoviewReviewSelected(MovieReview mr) {

    }

    @Override
    public void onMoviewTrailerSelected(MovieTrailer mt) {
        String id = mt.getmYoutubeVideoID();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+ id));
        startActivity(intent);
    }

    @Override
    public void saveMovieAsFavorite(Movie m) {

    }
}
