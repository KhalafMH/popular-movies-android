package com.impactprogrammer.android.popularmovies;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.impactprogrammer.android.popularmovies.moviedb.Movie;
import com.impactprogrammer.android.popularmovies.moviedb.MovieUtils;

/**
 * A {@link android.support.v7.widget.RecyclerView.ViewHolder} that holds a view for showing
 * a poster image for a {@link Movie}.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @NonNull
    private final ImageView posterImageView;
    @Nullable
    private final MovieListAdapter.MovieClickListener clickListener;
    private Movie movie;

    public MovieViewHolder(View itemView, @Nullable MovieListAdapter.MovieClickListener clickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.clickListener = clickListener;
        posterImageView = (ImageView) itemView.findViewById(R.id.posterImageView);
    }

    /**
     * Update the views of this holder with details about the {@link Movie} argument.
     *
     * @param movie the {@link Movie} for which this holder will show information.
     */
    public void bind(@NonNull Movie movie) {
        this.movie = movie;

        GlideApp.with(itemView)
                .load(MovieUtils.getPosterUri(itemView.getContext(), movie, 300))
                .placeholder(R.drawable.placeholder_image)
                .error(new ColorDrawable(Color.RED))
                .centerCrop()
                .into(posterImageView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view) {
        if (clickListener != null) {
            clickListener.onMovieItemClicked(movie);
        }
    }
}
