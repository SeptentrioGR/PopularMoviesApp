package com.udacity.projectpopularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.projectpopularmovies.Adapter.MoviesAdapter;
import com.udacity.projectpopularmovies.Data.MovieDBSyncTask;
import com.udacity.projectpopularmovies.Data.MoviesContract;
import com.udacity.projectpopularmovies.Data.Preferences;
import com.udacity.projectpopularmovies.Model.Movie;
import com.udacity.projectpopularmovies.Utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        MoviesAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    public static final String TAG = MainActivity.class.getSimpleName();
   
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
            MoviesContract.MovieEntry.MOVIE_RATING
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
    public static final int INDEX_MOVIE_RATING = 9;


    private static final int ID_MOVIE_LOADER = 44;

    //The Movie Adapter That Responsible to fill our Recycle View
    private MoviesAdapter mMovieAdapter;
    //A referense to our RecyclerView
    @Bind(R.id.pm_movies)
    RecyclerView mRecycleView;

    private int mPosition = RecyclerView.NO_POSITION;

    //A referense to our Progress Bar
    @Bind(R.id.pm_loading_indicator)
    ProgressBar mLoadingIndicator;

    //How Many Movies Will Be Shown in the Wierd
    private static final int LIST_OF_MOVIES = 10;


    //The Filter choosen in the settings
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private String filterChosen;
    private int gridsize = 2;

    //An Array of All Our Movies and their details
    ArrayList<Movie> mMovieList;

    String JsonResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // TODO (1) Add The Movie Data On The Database
        MovieDBSyncTask dbSyncTask = new MovieDBSyncTask(this);
        dbSyncTask.syncMovies();
        mRecycleView.setHasFixedSize(false);
        setSharedPreferences();
        //Creates are GridLayoutManager and sets it to be to colums
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(gridsize,1);

        //Sets the LayoutManagvet to GridLayoutManager
        mRecycleView.setLayoutManager(staggeredGridLayoutManager);
        mRecycleView.setHasFixedSize(true);
        //Creates the Movie Adapter
        mMovieAdapter = new MoviesAdapter(this,this);
        //Set The Adapter
        mRecycleView.setAdapter(mMovieAdapter);

        showLoading();

        getSupportLoaderManager().initLoader(ID_MOVIE_LOADER,null,this);


    }

    public void setSharedPreferences(){


        filterChosen = Preferences.getPreferenceFilter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_MOVIE_LOADER:
                Uri movieQueryUri = MoviesContract.MovieEntry.CONTENT_URI;
                return new CursorLoader(this,
                        movieQueryUri,
                        MAIN_MOVIE_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Impemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null) {
            mMovieAdapter.swapCursor(data);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mRecycleView.smoothScrollToPosition(mPosition);
            if (data.getCount() != 0) {
                showMovieDataView();
            }
        }else{
            showLoading();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mMovieAdapter.swapCursor(null);
    }

    @Override
    public void onClick(long clickedItemIndex) {
        Intent movieDetailIntent = new Intent(MainActivity.this,MovieActivity.class);
        Uri uriForMovieClicked = MoviesContract.MovieEntry.buildMovieUriWithId(clickedItemIndex);
        movieDetailIntent.setData(uriForMovieClicked);
        startActivity(movieDetailIntent);
    }

    private void showMovieDataView(){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    public void showLoading(){
        mRecycleView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ItemSelected = item.getItemId();

        if(ItemSelected == R.id.about_action){
            Context context = this;
            String Message = "Opening About Page";
            int Length = Toast.LENGTH_LONG;
            Utilities.MakeToast(context,Message,Length);
        }

        if(ItemSelected == R.id.settings_action){
            Context context = this;
            Intent startOptionsActivity = new Intent(context,SettingsActivity.class);
            startOptionsActivity.putExtra("filter",filterChosen);
            context.startActivity(startOptionsActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
