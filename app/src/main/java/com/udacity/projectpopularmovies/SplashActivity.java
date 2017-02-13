package com.udacity.projectpopularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private int waitForSeconds = 0;
    ProgressBar mLoadingIndicator;
    boolean myBoolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
            }
        }, waitForSeconds);


    }



}

