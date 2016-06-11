package com.ryanzhou.company.movieviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryanzhou on 6/11/16.
 */
public class MovieTrailer implements Parcelable {

    public static final String MOVIERTRAILER_ITEM_KEY = "movieTrailerItemKey";

    @SerializedName("key") private String mYoutubeVideoID;
    @SerializedName("site") private String mSite;   //Youtube
    @SerializedName("type") private String mType;   //Trailer, Teaser

    public MovieTrailer(Parcel in) {
        mYoutubeVideoID = in.readString();
        setmSite(in.readString());
        setmType(in.readString());
    }

    @Override
    public String toString() {
        return getmYoutubeVideoID();
    }

    public MovieTrailer(String id, String site, String type){
        this.setmYoutubeVideoID(id);
    }

    public String getmYoutubeVideoID() {
        return mYoutubeVideoID;
    }

    public void setmYoutubeVideoID(String id) {
        this.mYoutubeVideoID = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mYoutubeVideoID);
        dest.writeString(getmSite());
        dest.writeString(getmType());
    }

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };

    public String getmSite() {
        return mSite;
    }

    public void setmSite(String mSite) {
        this.mSite = mSite;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}