package com.udacity.projectpopularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Petros on 1/20/2017.
 */

public class Movie implements Parcelable {

    public  int id;
    private String mTitle;
    private String mDesc;
    private String mPopularity;
    private String mVoteCount;
    public String mVoteAverage;
    private String mDate;
    private String mImage;
    private String mBackdropPath;
    private String mRating;

    public String getmPopularity() {
        return mPopularity;
    }

    public void setmPopularity(String mPopularity) {
        this.mPopularity = mPopularity;
    }

    public String getmVoteCount() {
        return mVoteCount;
    }

    public void setmVoteCount(String mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getmBackdropPath() {
        return mBackdropPath;
    }

    public void setmBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public Movie(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    //Parceable Methods
    //----------------------------------------------------------------------------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDesc);
        dest.writeString(this.mPopularity);
        dest.writeString(this.mVoteCount);
        dest.writeString(this.mVoteAverage);
        dest.writeString(this.mDate);
        dest.writeString(this.mImage);
        dest.writeString(this.mBackdropPath);
        dest.writeString(this.mRating);


    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.mTitle = in.readString();
        this.mDesc = in.readString();
        this.mPopularity = in.readString();
        this.mVoteCount = in.readString();
        this.mVoteAverage = in.readString();
        this.mDate = in.readString();
        this.mImage = in.readString();
        this.mBackdropPath = in.readString();
        this.mRating = in.readString();

    }
}
