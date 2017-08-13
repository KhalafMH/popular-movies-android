package com.impactprogrammer.android.popularmovies;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.impactprogrammer.android.popularmovies.moviedb.Movie;
import com.impactprogrammer.android.popularmovies.moviedb.MovieUtils;

/**
 * Shows details about a single {@link Movie}.
 */
public class MovieDetailsFragment extends Fragment {
    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView releaseDateTextView;
    private TextView overviewTextView;
    private Movie movie;

    /**
     * Get a new instance of this {@link Fragment}.
     *
     * @return a new instance of this {@link Fragment} class.
     */
    public static Fragment newInstance() {
        return new MovieDetailsFragment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String movieExtra = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        movie = gson.fromJson(movieExtra, Movie.class);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        posterImageView = (ImageView) view.findViewById(R.id.posterImageView);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
        releaseDateTextView = (TextView) view.findViewById(R.id.releaseDateTextView);
        overviewTextView = (TextView) view.findViewById(R.id.overviewTextView);

        GlideApp.with(this)
                .load(MovieUtils.getPosterUri(getActivity(), movie, 300))
                .placeholder(R.drawable.placeholder_image)
                .error(new ColorDrawable(Color.RED))
                .centerCrop()
                .into(posterImageView);

        titleTextView.setText(movie.getOriginalTitle());
        ratingTextView.setText("" + movie.getVoteAverage() + " / 10");
        releaseDateTextView.setText(movie.getReleaseDate());
        overviewTextView.setText(movie.getOverview());

        return view;
    }
}
