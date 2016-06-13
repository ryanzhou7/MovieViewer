package com.ryanzhou.company.movieviewer.detailMovie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ryanzhou on 6/12/16.
 */
public class MovieDetailPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mMovieTabItems;
    private String[] mMOVIE_TAB_TITLES = {"Overview", "Reviews", "Trailers"};

    public MovieDetailPagerAdapter(FragmentManager fm) {
        super(fm);
        initTabItems();
    }

    private void initTabItems() {
        mMovieTabItems = new Fragment[mMOVIE_TAB_TITLES.length];
        mMovieTabItems[0] = new MovieDetailsFragment();
        mMovieTabItems[1] = new MovieDetailsFragment();
        mMovieTabItems[2] = new MovieDetailsFragment();
    }

    @Override
    public Fragment getItem(int i) {
        return mMovieTabItems[i];
    }

    @Override
    public int getCount() {
        return mMovieTabItems.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMOVIE_TAB_TITLES[position];
    }
}
