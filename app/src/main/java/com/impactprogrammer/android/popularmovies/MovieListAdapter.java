package com.impactprogrammer.android.popularmovies;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.impactprogrammer.android.popularmovies.moviedb.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to manage a list of movies displayed in a {@link RecyclerView}.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    @Nullable
    private List<Movie> movieList;

    @Nullable
    private MovieClickListener clickListener = null;

    /**
     * Create a new {@link MovieListAdapter}.
     *
     * @param movieList a {@link List} of {@link Movie} objects. Can be null.
     * @param listener  the listener to be called when a {@link MovieViewHolder} item is clicked.
     */
    public MovieListAdapter(@Nullable List<Movie> movieList, @Nullable MovieClickListener listener) {
        super();
        this.movieList = movieList;
        this.clickListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_poster, parent, false);

        return new MovieViewHolder(itemView, clickListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (movieList != null) {
            holder.bind(movieList.get(position));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        } else {
            return 0;
        }
    }

    /**
     * Replace the {@link List} of {@link Movie} objects held by this adapter and update the
     * {@link RecyclerView}.
     *
     * @param movieList a {@link List} of {@link Movie} objects.
     */
    public void setMovieList(@Nullable List<Movie> movieList) {
        if (movieList != null) {
            this.movieList = new ArrayList<>(movieList);
        } else {
            this.movieList = null;
        }
        notifyDataSetChanged();
    }

    /**
     * This should be implemented by Activities or Fragments to listen to click events on
     * {@link MovieViewHolder} items held by this adapter
     */
    interface MovieClickListener {
        /**
         * Will be executed once a {@link MovieViewHolder} is clicked
         *
         * @param movie the {@link Movie} object associated with the clicked {@link MovieViewHolder}
         */
        void onMovieItemClicked(Movie movie);
    }
}
