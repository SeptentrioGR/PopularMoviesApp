package com.udacity.projectpopularmovies.Data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.udacity.projectpopularmovies.Model.Review;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Petros on 2/12/2017.
 */

public class ReviewProvider {
    private ArrayList<Review> mReviews;
    private Context mContext;

    public ReviewProvider(Context context) {
        mContext = context;
    }

    public ArrayList<Review> syncReview(String id) {
        new ReviewProvider.RetrieveMovieDataTask().execute(id);
        return mReviews;
    }

    class RetrieveMovieDataTask extends AsyncTask<String, Void, ArrayList<Review>> {
        @Override
        protected ArrayList<Review> doInBackground(String... params) {
            try {
                String id = params[0];
                URL myJasonURL = Networking.buildUrlForReviews(id);
                String JsonResponse = Networking.getResponseFromHttpUrl(myJasonURL);
                return TheMovieDVJsonUtils.getMovieReviewFromJason(mContext, JsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviewArrayList) {
            if (reviewArrayList != null && reviewArrayList.size() != 0) {
                mReviews = reviewArrayList;


            }
        }
    }
}
