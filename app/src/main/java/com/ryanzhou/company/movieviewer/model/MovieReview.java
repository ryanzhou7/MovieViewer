package com.ryanzhou.company.movieviewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryanzhou on 6/8/16.
 */
public class MovieReview {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
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
}
