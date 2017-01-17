package com.udacity.projectpopularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.projectpopularmovies.Adapter.MoviesAdapter;
import com.udacity.projectpopularmovies.Utils.Networking;

import java.net.URL;

public class main extends AppCompatActivity implements MoviesAdapter.ListItemClickListener{
    public static final String TAG = "MAIN_ACTIVITY";
    private static final int LIST_OF_MOVIES = 10;
    public Toast myToast;
    private MoviesAdapter mAdapter;
    private RecyclerView mMoviesList;
    public static String[] movieTitles = {
             "Zootopia",
              "Hell or High Water",
              "Arrival",
              "The Jungle Book",
              "Moonlight","Love and Friendship",
             "Finding Dory",
            "Title 8",
            "Title 9",
            "Title 10"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = (RecyclerView)findViewById(R.id.pm_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);
        mAdapter = new MoviesAdapter(LIST_OF_MOVIES,this);

        mMoviesList.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ItemSelected = item.getItemId();
        if(ItemSelected == R.id.refresh_action){
            DebugLog("Refresh Button Pressed");
            Context context = this;
            String Message = "Refreshing the Page";
            int Length = Toast.LENGTH_LONG;
            MakeToast(context,Message,Length);
        }
        if(ItemSelected == R.id.about_action){
            DebugLog("About Button Pressed");
            Context context = this;
            String Message = "Opening About Page";
            int Length = Toast.LENGTH_LONG;
            MakeToast(context,Message,Length);
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadMovieData(){
        new FetchMoviesTesk().execute();
    }


    public void MakeToast(Context context, String message,int length){
        if(myToast!=null){
            myToast.cancel();
        }else{
            myToast.makeText(context,message,length).show();
        }
    }

    public void DebugLog(String message){
        Log.i(TAG,message);
    }

    public class FetchMoviesTesk extends AsyncTask<URL,Void,String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = Networking.buildUrl();
            if(params.length == 0){
                return  null;
            }
            try{

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

            String githubSearchResults = null;

            return null;
        }


        @Override
        protected void onProgressUpdate(Void... params) {

            super.onProgressUpdate(params);
        }


        @Override
        protected void onPostExecute(String databaseSeasrchResults) {

            super.onPostExecute(databaseSeasrchResults);
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(myToast != null){
            myToast.cancel();
        }
        String toastMessaage = "item#" + clickedItemIndex + " clicked";
        MakeToast(this,toastMessaage,Toast.LENGTH_LONG);
    }
}
