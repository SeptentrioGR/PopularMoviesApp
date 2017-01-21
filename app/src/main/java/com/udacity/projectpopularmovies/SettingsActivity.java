package com.udacity.projectpopularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    TextView FilterDisplayTextView;

    private final String FILTER = "filter";

    private String[] posible_filters = {
            "top_rated","popular"
    };
    private String filterChosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        FilterDisplayTextView = (TextView)findViewById(R.id.filterTextDisplay);

        Intent intent = getIntent();
        if(intent.hasExtra(FILTER)){
            filterChosen = intent.getStringExtra(FILTER);
        }
        FilterDisplayTextView.setText("Your movie filter: \n" + filterChosen);
    }
        public void ChangeFilter(View view) {

        if(filterChosen.equals(posible_filters[0])){
            filterChosen = posible_filters[1];
        }else {
            filterChosen = posible_filters[0];
        }
        FilterDisplayTextView.setText("Your movie filter: \n" + filterChosen);
    }

    //Save The filter and Goes Back To Main Activity
    public void SaveAndExit(View view) {
        Context context = this;
        Intent startMainActivity = new Intent(context,MainActivity.class);
        startMainActivity.putExtra(FILTER,filterChosen);
        context.startActivity(startMainActivity);
    }
}
