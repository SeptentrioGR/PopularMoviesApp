package com.udacity.projectpopularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.projectpopularmovies.Adapter.MoviesAdapter;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;
import com.udacity.projectpopularmovies.Utils.utilities;
import com.udacity.projectpopularmovies.Model.Movie;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener{
    public static final String TAG = "MAIN_ACTIVITY";
    //How Many Movies Will Be Shown in the Wierd
    private static final int LIST_OF_MOVIES = 10;
    //The Movie Adapter That Responsible to fill our Recycle View
    private MoviesAdapter mMovieAdapter;
    //A referense to our RecyclerView
    private RecyclerView mRecycleView;
    //An Array of All Our Movies and their details
    private  ArrayList<Movie> mMovieList;
    //A referense to our Progress Bar
    private ProgressBar mLoadingIndicator;
    //A Reference to our Error Message
    private TextView mErrorMessageDisplay;
    //Attributes used to send the information to the Movie Activity
    public static final String NAME = "name";
    public static final String LOCATION = "location";
    public static final String DESCRIPTION = "description";
    public static final String RATING = "rating";
    public static final String RELEASEDATE = "release_date";
    //The Filter choosen in the settings
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private String filterChosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pm_loading_indicator);
        mErrorMessageDisplay = (TextView)findViewById(R.id.pm_error_message);
        mRecycleView = (RecyclerView)findViewById(R.id.pm_movies);
        mRecycleView.setHasFixedSize(false);
        //Checks if there is any filter available and sets it either wise set it to default.
        Intent intent = getIntent();
        if (intent.hasExtra("filter")) {
            filterChosen = intent.getStringExtra("filter");

        }else{
            filterChosen = "popular";
        }
        //Creates are GridLayoutManager and sets it to be to colums

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);

        //Sets the LayoutManagvet to GridLayoutManager
        mRecycleView.setLayoutManager(gaggeredGridLayoutManager);

        //Creates the Movie Adapter
        mMovieAdapter = new MoviesAdapter(LIST_OF_MOVIES,this);
        //Set The Adapter
        mRecycleView.setAdapter(mMovieAdapter);
        //Load The Movies which the filter we chosen
        LoadMovieData(filterChosen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void showErrorMessage(){
        mRecycleView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public void hideErrorMessage(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ItemSelected = item.getItemId();

       if(ItemSelected == R.id.about_action){
            Context context = this;
            String Message = "Opening About Page";
            int Length = Toast.LENGTH_LONG;
            utilities.MakeToast(context,Message,Length);
        }

        if(ItemSelected == R.id.settings_action){
            Context context = this;
            Intent startOptionsActivity = new Intent(context,SettingsActivity.class);
            startOptionsActivity.putExtra("filter",filterChosen);
            context.startActivity(startOptionsActivity);
        }

        return super.onOptionsItemSelected(item);
    }
    //This Method Is Responsible For Loading All The Movies From JSON using ASyncTask with the filter
    public void LoadMovieData(String filter){
        new FetchMoviesTesk().execute(filter);
    }


    //Fetches the Movies from The Database and add them on A Json and fills the Array with All the Movies
    public class FetchMoviesTesk extends AsyncTask<String,Void,ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String ... params) {
            String filter = params[0];
            URL searchUrl = Networking.buildUrl(filter);

            try{
                String jsonMovieResponse = Networking.getResponseFromHttpUrl(searchUrl);
                 mMovieList = TheMovieDVJsonUtils.fillListFromJson(MainActivity.this,jsonMovieResponse);
                return mMovieList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }


        @Override
        protected void onProgressUpdate(Void... params) {

            super.onProgressUpdate(params);
        }


        @Override
        protected void onPostExecute(ArrayList<Movie> moviesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if(moviesData!=null){
                hideErrorMessage();
              mMovieAdapter.setMovieData(moviesData);
            }else{
                showErrorMessage();
            }

        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Context context = this;
        Intent startChildActivityIntent = new Intent(context,MovieActivity.class);
        startChildActivityIntent.putExtra(NAME,mMovieList.get(clickedItemIndex).getmTitle());
        startChildActivityIntent.putExtra(LOCATION,mMovieList.get(clickedItemIndex).getmImage());
        startChildActivityIntent.putExtra(DESCRIPTION,mMovieList.get(clickedItemIndex).getmDesc());
        startChildActivityIntent.putExtra(RATING,mMovieList.get(clickedItemIndex).getmRating());
        startChildActivityIntent.putExtra(RELEASEDATE,mMovieList.get(clickedItemIndex).getmDate());
        context.startActivity(startChildActivityIntent);
    }
}
