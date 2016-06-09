package com.ryanzhou.company.movieviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryanzhou on 6/8/16.
 */
public class MovieReview implements Parcelable{

    public static final String MOVIEREVIEW_ITEM_KEY = "movieReviewItemKey";

    @SerializedName("author") private String mAuthor;
    @SerializedName("content") private String mContent;

    public MovieReview(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
    }

    @Override
    public String toString() {
        return( getmAuthor() + ": "+ getmContent());
    }

    public MovieReview(String a, String c){
        this.setmAuthor(a);
        this.setmContent(c);
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mContent);
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };
}
