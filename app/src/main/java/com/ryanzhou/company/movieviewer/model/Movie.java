package com.ryanzhou.company.movieviewer.model;

/**
 * Created by ryanzhou on 5/9/16.
 */
public class Movie {

    private String mOriginalTitle;
    private String mImagePath;
    private String mSynopsis;
    private Double mUserRating;
    private String mReleaseDate;

    public Movie(String title, String imagePath, String synopsis, Double userRating, String releaseDate){
        mOriginalTitle = title;
        mImagePath = imagePath;
        mSynopsis = synopsis;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }

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

}
