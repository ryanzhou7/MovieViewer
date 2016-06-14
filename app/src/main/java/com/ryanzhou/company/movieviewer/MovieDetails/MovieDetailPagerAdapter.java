package com.ryanzhou.company.movieviewer.movieDetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanzhou on 6/12/16.
 */
public class MovieDetailPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mMovieTabItems = new ArrayList<>();
    private final List<String> mMovieTitleList = new ArrayList<>();

    public MovieDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mMovieTabItems.add(fragment);
        mMovieTitleList.add(title);
    }
    @Override
    public Fragment getItem(int i) {
        return mMovieTabItems.get(i);
    }

    @Override
    public int getCount() {
        return mMovieTabItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMovieTitleList.get(position);
    }
}
