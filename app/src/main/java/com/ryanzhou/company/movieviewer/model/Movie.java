package com.ryanzhou.company.movieviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ryanzhou on 5/9/16.
 */
public class Movie implements Parcelable{

    public static final String MOVIES_LIST_KEY = "moviesListKey";
    public static final String MOVIE_ITEM_KEY = "movieItemKey";

    private String mOriginalTitle;
    private String mImagePath;
    private String mSynopsis;
    private Double mUserRating;
    private String mReleaseDate;

    public Movie(String title, String imagePath, String synopsis, String releaseDate, Double userRating){
        mOriginalTitle = title;
        mImagePath = imagePath;
        mSynopsis = synopsis;
        mReleaseDate = releaseDate;
        mUserRating = userRating;
    }

    protected Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mImagePath = in.readString();
        mSynopsis = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public void setmOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }

    public void setmSynopsis(String mSynopsis) {
        this.mSynopsis = mSynopsis;
    }

    public Double getmUserRating() {
        return mUserRating;
    }

    public void setmUserRating(Double mUserRating) {
        this.mUserRating = mUserRating;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        final Character tab = '\t';
        builder.append(getmOriginalTitle() + tab);
        builder.append(getmUserRating() + tab);
        builder.append(getmReleaseDate() + tab);
        builder.append(getmSynopsis());
        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mOriginalTitle);
        dest.writeString(mImagePath);
        dest.writeString(mSynopsis);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mUserRating);
    }
}
