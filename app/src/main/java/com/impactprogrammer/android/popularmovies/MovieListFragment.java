package com.impactprogrammer.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.impactprogrammer.android.popularmovies.moviedb.Movie;
import com.impactprogrammer.android.popularmovies.moviedb.MovieImageConfiguration;
import com.impactprogrammer.android.popularmovies.moviedb.MovieImageConfigurationManager;
import com.impactprogrammer.android.popularmovies.moviedb.MovieImageConfigurationManager.OnNewMovieImageConfigurationReceivedListener;
import com.impactprogrammer.android.popularmovies.moviedb.MovieList;
import com.impactprogrammer.android.popularmovies.moviedb.MovieListManager;
import com.impactprogrammer.android.popularmovies.moviedb.MovieUtils;

import java.util.concurrent.CancellationException;

import java8.util.concurrent.CompletableFuture;
import java8.util.function.Function;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A fragment that shows a grid of movie posters that can be sorted either by popularity or
 * by highest rating.
 */
public class MovieListFragment extends Fragment implements
        MovieListAdapter.MovieClickListener,
        OnNewMovieImageConfigurationReceivedListener {
    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();
    private static final String THE_MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/";

    private final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    @Nullable
    private GridLayoutManager layoutManager;
    private MovieListManager movieListManager;
    private MovieImageConfigurationManager movieImageConfigurationManager;
    private int sortOrder;
    private CompletableFuture<MovieList> movieListByRatingFuture;
    private CompletableFuture<MovieList> movieListByPopularityFuture;
    @Nullable
    private MovieListAdapter adapter;
    @Nullable
    private MovieDB movieDB;
    @Nullable
    private Spinner sortSpinner;
    @Nullable
    private RecyclerView recyclerView;

    /**
     * Create a new instance of this {@link Fragment}.
     *
     * @return a new instance of this {@link Fragment}.
     */
    public static Fragment newInstance() {
        return new MovieListFragment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieImageConfigurationManager = MovieImageConfigurationManager.getInstance(getActivity());
        movieListManager = MovieListManager.getInstance(getActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(THE_MOVIE_DB_BASE_URL)
                .build();
        movieDB = retrofit.create(MovieDB.class);

        movieImageConfigurationManager.updateMovieImageConfigurationAsync(movieDB, this);
        movieListByRatingFuture =
                movieListManager.updateMovieListAsync(movieDB, MovieUtils.SORT_ORDER_BY_RATING, null);
        movieListByPopularityFuture =
                movieListManager.updateMovieListAsync(movieDB, MovieUtils.SORT_ORDER_BY_POPULARITY, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        updateSpinnerSelection();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        sortSpinner = (Spinner) view.findViewById(R.id.sortSpinner);
        assert sortSpinner != null;
        updateSpinnerSelection();
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sortOrder = (int) id;
                switch (sortOrder) {
                    case MovieUtils.SORT_ORDER_BY_RATING:
                    case MovieUtils.SORT_ORDER_BY_POPULARITY:
                        QueryPreferences.setSortOrder(getActivity(), sortOrder);
                        break;
                    default:
                        throw new AssertionError("Spinner not expected to have a third value");
                }
                updateMovieList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // not needed
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.moviesRecyclerView);
        assert recyclerView != null;
        layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieListAdapter(null, this);
        recyclerView.setAdapter(adapter);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float density = getActivity().getResources().getDisplayMetrics().density;
                int measuredWidthDp = (int) (view.getMeasuredWidth() / density);
                layoutManager.setSpanCount(measuredWidthDp / 190); // 190dp is the hardcoded width in list_item_poster.xml
            }
        });
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMovieItemClicked(final Movie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        String movieJson = gson.toJson(movie);
        intent.putExtra(Intent.EXTRA_TEXT, movieJson);
        startActivity(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMovieImageConfigurationReceived(@NonNull final MovieImageConfiguration configuration) {
        updateMovieList();
    }

    /**
     * set the sortSpinner selected item to the current selection or the cached selection
     */
    private void updateSpinnerSelection() {
        assert sortSpinner != null;
        sortSpinner.setSelection(QueryPreferences.getSortOrder(getActivity()));
    }

    /**
     * fetch the latest available movie list and update recyclerView to display the new list.
     */
    private void updateMovieList() {
        CompletableFuture<MovieList> future = getMovieListFuture(sortOrder);
        MovieList cachedMovieList = movieListManager.getCachedMovieList(sortOrder);
        if (cachedMovieList != null) {
            assert adapter != null;
            MovieList movieList;
            try {
                movieList = future.getNow(cachedMovieList);
            } catch (CancellationException e) {
                movieList = cachedMovieList;
            }
            if (movieList != null) {
                adapter.setMovieList(movieList.getResults());
            }
        }
        if (!future.isDone()) {
            future.thenApply(new Function<MovieList, Void>() {
                @Override
                public Void apply(MovieList movieList) {
                    updateMovieList();
                    return null;
                }
            });
        }
    }

    /**
     * Select either movieListByRatingFuture or movieListByPopularityFuture depending on the
     * sortOrder specified.
     *
     * @param sortOrder the sort order for selecting the corresponding future.
     *                  Must be one of the constants defined in {@link MovieUtils}
     * @return the {@link MovieList} future corresponding to the sortOrder passed in
     */
    private CompletableFuture<MovieList> getMovieListFuture(int sortOrder) {
        if (sortOrder == MovieUtils.SORT_ORDER_BY_RATING) {
            return movieListByRatingFuture;
        } else if (sortOrder == MovieUtils.SORT_ORDER_BY_POPULARITY) {
            return movieListByPopularityFuture;
        } else {
            throw new IllegalArgumentException("unknown sort order");
        }
    }
}
