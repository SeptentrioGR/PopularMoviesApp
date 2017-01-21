package com.udacity.projectpopularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.projectpopularmovies.Model.Movie;
import com.udacity.projectpopularmovies.MovieActivity;
import com.udacity.projectpopularmovies.R;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.NumberViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private ArrayList<Movie> mMovieData;
    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;
    private int mNumberItems;
    private Context context;
    private View view;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public MoviesAdapter(int numberOfItems,ListItemClickListener listener){
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediatly = false;
        view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediatly);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        String MoviePosterLocation = mMovieData.get(position).getmImage();
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/" + MoviePosterLocation).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if(mMovieData==null){
            return 0;
        }else {
            return mMovieData.size();
        }
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mMoviePoster;

        NumberViewHolder(View itemView){
            super(itemView);
            mMoviePoster = (ImageView)itemView.findViewById(R.id.pm_image_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }


    public void setMovieData(ArrayList<Movie> movieData){
        mMovieData = movieData;
        notifyDataSetChanged();
    }



}

