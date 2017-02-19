package com.udacity.projectpopularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.udacity.projectpopularmovies.Data.Preferences;
import com.udacity.projectpopularmovies.MainActivity;
import com.udacity.projectpopularmovies.Model.Movie;
import com.udacity.projectpopularmovies.MovieActivity;
import com.udacity.projectpopularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.NumberViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private Context mContext;
    private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;
    private View view;
    private Cursor mCursor;

    public interface ListItemClickListener {
        void onClick(long clickedItemIndex);
    }

    public MoviesAdapter(Context context, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediatly = false;
        view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediatly);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        mCursor.moveToPosition(position);
//        Glide.with(mContext)
//                .load("https://image.tmdb.org/t/p/w500"+mCursor.getString(MainActivity.INDEX_MOVIE_POSTER_PATH)).into(holder.mMoviePoster);
        Picasso.with(mContext)
                .load("https://image.tmdb.org/t/p/" + Preferences.getPreferenceImageSize(mContext) + mCursor.getString(MainActivity.INDEX_MOVIE_POSTER_PATH)).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mMoviePoster;

        NumberViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.pm_image_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int movie_id = Integer.parseInt(mCursor.getString(MainActivity.INDEX_MOVIE_ID));
            mOnClickListener.onClick(movie_id);

        }
    }


}

