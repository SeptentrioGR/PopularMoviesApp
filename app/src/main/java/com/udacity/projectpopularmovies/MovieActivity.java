package com.udacity.projectpopularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.squareup.picasso.Picasso;
import com.udacity.projectpopularmovies.Adapter.MoviesAdapter;
import com.udacity.projectpopularmovies.Adapter.ReviewAdapter;
import com.udacity.projectpopularmovies.Adapter.VideoAdapter;
import com.udacity.projectpopularmovies.Data.MoviesContract;
import com.udacity.projectpopularmovies.Data.ReviewProvider;
import com.udacity.projectpopularmovies.Data.TrailerProvider;
import com.udacity.projectpopularmovies.Model.Movie;
import com.udacity.projectpopularmovies.Model.Review;
import com.udacity.projectpopularmovies.Model.Video;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;
import com.udacity.projectpopularmovies.Utils.Utilities;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    final String TAG = MovieActivity.class.getSimpleName();
    public static HashMap<String,Boolean> favorites = new HashMap<>();
    public static final String[] MAIN_MOVIE_PROJECTION = {
            MoviesContract.MovieEntry.MOVIE_ID,
            MoviesContract.MovieEntry.MOVIE_TITLE,
            MoviesContract.MovieEntry.MOVIE_DESCRIPTION,
            MoviesContract.MovieEntry.MOVIE_POPULARITY,
            MoviesContract.MovieEntry.MOVIE_VOTE_COUNT,
            MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE,
            MoviesContract.MovieEntry.MOVIE_RELEASE_DATE,
            MoviesContract.MovieEntry.MOVIE_POSTER_PATH,
            MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH,
            MoviesContract.MovieEntry.MOVIE_IS_FAVORITE
    };

    public static final int INDEX_MOVIE_ID = 0;
    public static final int INDEX_MOVIE_TITLE = 1;
    public static final int INDEX_MOVIE_DESCRIPTION = 2;
    public static final int INDEX_MOVIE_POPULARITY = 3;
    public static final int INDEX_MOVIE_VOTE_COUNT = 4;
    public static final int INDEX_MOVIE_VOTE_AVERAGE = 5;
    public static final int INDEX_MOVIE_RELEASE_DATE = 6;
    public static final int INDEX_MOVIE_POSTER_PATH = 7;
    public static final int INDEX_MOVIE_BACKDROP_PATH = 8;
    public static final int INDEX_MOVIE_IS_FAVORITE = 9;
    private static final int ID_MOVIE_LOADER = 353;
    private Uri mUri;
    @Bind(R.id.movie_title)
    TextView mMovieTitle;
    @Bind(R.id.Poster)
    ImageView mMoviePoster;
    @Bind(R.id.rating)
    TextView mRatingDisplay;
    @Bind(R.id.release_date)
    TextView mReleaseDateDisplay;
    @Bind(R.id.synopse)
    TextView mMovieDescription;
    @Bind(R.id.pm_review_list)
    RecyclerView mReviewList;
    @Bind(R.id.pm_trailer_list)
    RecyclerView mVideoList;
    @Bind(R.id.pm_review_error)
    TextView mReviewErrorDisplay;
    @Bind(R.id.pm_video_error)
    TextView mVideoErrorDisplay;
    @Bind(R.id.favorite_movies_button)
    Button mFavoriteMovieButton;
    @Bind(R.id.Top_Movie_Detail_Layout)
    LinearLayout topLayout;
    Context mContext;
    private ReviewAdapter mReviewAdapter;
    private VideoAdapter mVideoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        setReview();
        setVideos();

        mUri = getIntent().getData();
        mContext = this;
        if (mUri == null) throw new NullPointerException("URI for MovieActivity cannot be null");
        getSupportLoaderManager().initLoader(ID_MOVIE_LOADER, null, this);

    }

    public void setReview(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,1);
        mReviewList.setLayoutManager(staggeredGridLayoutManager);
        mReviewList.setHasFixedSize(false);
        mReviewAdapter = new ReviewAdapter();
        mReviewList.setAdapter(mReviewAdapter);
    }

    public void setVideos(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,1);
        mVideoList.setLayoutManager(staggeredGridLayoutManager);
        mVideoList.setHasFixedSize(false);
        mVideoAdapter = new VideoAdapter(this);
        mVideoList.setAdapter(mVideoAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                mUri,
                MAIN_MOVIE_PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            cursorHasValidData = true;
        }
        if (!cursorHasValidData) {
            Log.e(TAG, "Cursror has not valid data");
        }
        if (data.getCount() != 0) {
            final String id = data.getString(INDEX_MOVIE_ID);
            final String title = data.getString(INDEX_MOVIE_TITLE);
            final String description = data.getString(INDEX_MOVIE_DESCRIPTION);
            final String popularity = data.getString(INDEX_MOVIE_POPULARITY);
            final String vote_count = data.getString(INDEX_MOVIE_VOTE_COUNT);
            final String vote_average = data.getString(INDEX_MOVIE_VOTE_AVERAGE);
            final String release_date = data.getString(INDEX_MOVIE_RELEASE_DATE);
            final String poster_path = data.getString(INDEX_MOVIE_POSTER_PATH);
            final String backdrop_path = data.getString(INDEX_MOVIE_BACKDROP_PATH);
            final int isFavorite = data.getInt(INDEX_MOVIE_IS_FAVORITE);
            PopulateMovieDetails(id,title,description,popularity,release_date,poster_path,vote_count,vote_average,isFavorite);

        }
    }

    public void PopulateMovieDetails(final String id,String title,String description,String popularity,String release_date,String poster_path,String vote_count,String vote_average,final int isFavorites){
        mMovieTitle.setText(title);
        mMovieDescription.setText(description);
        mReleaseDateDisplay.setText(release_date.substring(0, 4));
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500" + poster_path)
                .into(mMoviePoster);
//        Glide.with(this)
//                .load("https://image.tmdb.org/t/p/w500" + poster_path)
//                .into(mMoviePoster);
        mRatingDisplay.setText("Popularity" + "\n(" + vote_count + " ) " + vote_average + "\n" + popularity);
        UpdateFavorite(isFavorites);
        loadReviews(id);
        loadVideos(id);
        mFavoriteMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FavoriteMovie(id,isFavorites);
                    UpdateFavorite(isFavorites);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void UpdateFavorite(int isFavorite){
          if(isFavorite == 1){
            mFavoriteMovieButton.setBackground(getResources().getDrawable(R.drawable.favorite_movie));
        }else{
            mFavoriteMovieButton.setBackground(getResources().getDrawable(R.drawable. no_favorite_movie));
        }

    }

    public void FavoriteMovie(String id,int value){
        if(value == 0 ){
            updateMovie(id,"1");
        }else{
            updateMovie(id,"0");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public void loadReviews(String id){

        new RetrieveReviewDataTask().execute(id);
    }

    public void loadVideos(String id){

        new RetrieveVideoDataTask().execute(id);
    }


    class RetrieveVideoDataTask extends AsyncTask<String,Void,ArrayList<Video>> {

        @Override
        protected ArrayList<Video> doInBackground(String... params) {
              /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }
            String id = params[0];
            URL myJasonURL = Networking.buildUrlForVideos(id);
            try{
                String JsonResponse = Networking.getResponseFromHttpUrl(myJasonURL);
                ArrayList<Video> simpleJsonWeatherData = TheMovieDVJsonUtils.getMovieTrailersFromJSON(mContext, JsonResponse);
                return simpleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Video> mVideoArray)
        {
            if(mVideoArray != null){
                Log.i(TAG,mVideoArray.toString());
                mVideoList.setVisibility(View.VISIBLE);
                mVideoErrorDisplay.setVisibility(View.INVISIBLE);
                mVideoAdapter.setMovieData(mVideoArray);
            }else{
                mVideoList.setVisibility(View.INVISIBLE);
                mVideoErrorDisplay.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(mVideoArray);
        }
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
                ArrayList<Review> simpleJsonWeatherData = TheMovieDVJsonUtils.getMovieReviewFromJason(mContext, JsonResponse);
                return simpleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Review> mReviewArray)
        {
            if(mReviewArray != null && mReviewArray.size() >0){
                mReviewList.setVisibility(View.VISIBLE);
                mReviewErrorDisplay.setVisibility(View.INVISIBLE);
                mReviewAdapter.setReviewData(mReviewArray);
            }else{
                mReviewErrorDisplay.setVisibility(View.VISIBLE);
                mReviewList.setVisibility(View.INVISIBLE);
            }

            super.onPostExecute(mReviewArray);
        }
    }

    public void updateMovie(String id,String value){
        ContentValues mUpdateValues = new ContentValues();
        String mSelectionClause = MoviesContract.MovieEntry.MOVIE_ID + "= ?";
        String[] mSelectionArgs = {id};
        int mRowsUpdated = 0;
        mUpdateValues.put(MoviesContract.MovieEntry.MOVIE_IS_FAVORITE,Integer.parseInt(value));
        mRowsUpdated = getContentResolver().update(
                MoviesContract.MovieEntry.buildMovieUriWithId(Long.parseLong(id)),  // The user dictionary content URI
                mUpdateValues,                                                      // The columns to update
                mSelectionClause,                                                   // The column to select on
                mSelectionArgs                                                      // The value to compare to
        );
        if(mRowsUpdated>0){
            Log.i(TAG,"Updated Complete");
        }else{
            return;
        }
    }
}
