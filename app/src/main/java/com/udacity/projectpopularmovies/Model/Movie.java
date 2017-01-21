package com.udacity.projectpopularmovies.Model;

/**
 * Created by Petros on 1/20/2017.
 */

public class Movie {

    private String mTitle;
    private String mImage;
    private String mDesc;
    private String mRating;
    private String mDate;

    public Movie(String title, String image, String desc,String rating,String date){
        mTitle = title;
        mImage = image;
        mDesc = desc;
        mRating = rating;
        mDate = date;
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
}
