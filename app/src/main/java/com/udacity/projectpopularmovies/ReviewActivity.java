package com.udacity.projectpopularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.udacity.projectpopularmovies.Adapter.ReviewAdapter;
import com.udacity.projectpopularmovies.Data.ReviewProvider;
import com.udacity.projectpopularmovies.Model.Review;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;

import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity {
    final String TAG = MovieActivity.class.getSimpleName();

    @Bind(R.id.pm_review_list)
    RecyclerView mReviewList;
    private ReviewAdapter mReviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mReviewList.setLayoutManager(layoutManager);
        mReviewList.setHasFixedSize(true);
        mReviewAdapter = new ReviewAdapter();
        mReviewList.setAdapter(mReviewAdapter);
        loadReviews("550");
    }

    public void loadReviews(String id){

        new RetrieveReviewDataTask().execute(id);
    }


    class RetrieveReviewDataTask extends AsyncTask<String,Void,ArrayList<Review>> {

        @Override
        protected ArrayList<Review> doInBackground(String... params) {
              /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }
            String id = params[0];
            URL myJasonURL = Networking.buildUrlForReviews(id);
            try{

                String JsonResponse = Networking.getResponseFromHttpUrl(myJasonURL);
                ArrayList<Review> simpleJsonWeatherData = TheMovieDVJsonUtils.getMovieReviewFromJason(ReviewActivity.this, JsonResponse);
                return simpleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(  ArrayList<Review> bool) {
            //Log.i(TAG,"LOADING COMPLETED" + mReviewArray.size());

            super.onPostExecute(bool);
        }
    }

}
