package com.udacity.projectpopularmovies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.udacity.projectpopularmovies.R;
import com.udacity.projectpopularmovies.main;

/**
 * Created by Petros on 1/17/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.NumberViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;
    private int mNumberItems;

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
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediatly = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediatly);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        viewHolder.mMovieTitle.setText("Test 1");

        viewHolderCount ++;
        Log.d(TAG,"onCreateViewHolder: number of ViewHolders created:" + viewHolderCount);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG,"#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mMovieTitle;
        ImageButton mMoviePoster;

        public NumberViewHolder(View itemView){
            super(itemView);
            mMovieTitle = (TextView)itemView.findViewById(R.id.pm_movie_title);
            mMoviePoster = (ImageButton)itemView.findViewById(R.id.pm_image_poster);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            String name = main.movieTitles[listIndex];
            if(!name.equals("")) {
                mMovieTitle.setText(name);
            }else{
                mMovieTitle.setText("");
            }
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


}

