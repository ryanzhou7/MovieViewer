package com.ryanzhou.company.movieviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryanzhou on 5/9/16.
 */
public class Movie implements Parcelable{

    public static final String MOVIE_ITEM_KEY = "movieItemKey";
    public static final String MOVIE_ID_KEY = "movieIDKey";

    @SerializedName("original_title") private String mOriginalTitle;
    @SerializedName("poster_path") private String mImagePath;
    @SerializedName("overview") private String mSynopsis;
    @SerializedName("vote_average") private Double mUserRating;
    @SerializedName("release_date") private String mReleaseDate;
    @SerializedName("id") private long mMovieID;

    public Movie(String title, String imagePath, String synopsis, String releaseDate,
                 Double userRating,
                 long movieID){
        mOriginalTitle = title;
        mImagePath = imagePath;
        mSynopsis = synopsis;
        mReleaseDate = releaseDate;
        mUserRating = userRating;
        mMovieID = movieID;
    }

    protected Movie(Parcel in) {
        //needs to be chronological order
        mOriginalTitle = in.readString();
        mImagePath = in.readString();
        mSynopsis = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readDouble();
        mMovieID = in.readLong();
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

    public Boolean isValidImageUrl(){
        if( mImagePath == null || mImagePath.equals("null") || mImagePath.isEmpty() )
            return false;
        return true;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        final Character tab = '\t';
        builder.append(getmOriginalTitle() + tab);
        builder.append(getmUserRating() + tab);
        builder.append(getmReleaseDate() + tab);
        builder.append(getmSynopsis() + tab);
        builder.append(getmMovieID());
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
        dest.writeLong(mMovieID);
    }

    public long getmMovieID() {
        return mMovieID;
    }

    public void setmMovieID(long mMovieID) {
        this.mMovieID = mMovieID;
    }
}
