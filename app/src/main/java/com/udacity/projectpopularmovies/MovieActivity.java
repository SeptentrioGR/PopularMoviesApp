package com.udacity.projectpopularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {
    final String TAG = MovieActivity.class.getSimpleName();


    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mRatingDisplay;
    private TextView mReleaseDateDisplay;
    private TextView mMovieDescription;
    private String name = "";
    private String location = "";
    private String description = "";
    private String date = "";
    private String rating = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mMovieTitle = (TextView)findViewById(R.id.movie_title);
        mRatingDisplay = (TextView)findViewById(R.id.rating);
        mReleaseDateDisplay = (TextView)findViewById(R.id.release_date);
        mMoviePoster = (ImageView) findViewById(R.id.Poster);
        mMovieDescription = (TextView)findViewById(R.id.synopse);

        Intent intent = getIntent();

        if (intent.hasExtra(MainActivity.NAME)) {
            name = intent.getStringExtra(MainActivity.NAME);
        }
        if (intent.hasExtra(MainActivity.LOCATION)){
            location = intent.getStringExtra(MainActivity.LOCATION);

        }
        if (intent.hasExtra(MainActivity.DESCRIPTION)){
            description = intent.getStringExtra(MainActivity.DESCRIPTION);
        }
        if (intent.hasExtra(MainActivity.RATING)){
            rating = intent.getStringExtra(MainActivity.RATING);
        }
        if (intent.hasExtra(MainActivity.RELEASEDATE)){
            date = intent.getStringExtra(MainActivity.RELEASEDATE);
        }
        setMovieDetails(name,location,description,rating,date);

    }

    //Sets all the Movie Details
    public void setMovieDetails(String title,String imageLocation,String description,String rating,String date)
    {
        mMovieTitle.setText(title);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w342/" + imageLocation).into(mMoviePoster);
        mMovieDescription.setText(description);
        mRatingDisplay.setText(rating + " /10");
        mReleaseDateDisplay.setText(date.substring(0,4));

    }


}
