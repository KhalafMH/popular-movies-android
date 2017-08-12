package com.impactprogrammer.android.popularmovies.moviedb;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.impactprogrammer.android.popularmovies.MovieDB;
import com.impactprogrammer.android.popularmovies.QueryPreferences;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java8.util.concurrent.CompletableFuture;

/**
 * A singleton object that will hold and manage caching and updates to the {@link MovieList} objects
 * used in the app. This object is thread safe.
 */

public class MovieListManager {
    private static final String LOG_TAG = MovieListManager.class.getSimpleName();
    private static final Lock lock = new ReentrantLock();
    @Nullable
    private static MovieListManager instance = null;

    private final Context context;

    @Nullable
    private MovieList cachedMovieListByRating = null;
    @Nullable
    private MovieList cachedMovieListByPopularity = null;


    private MovieListManager(final Context context) {
        this.context = context.getApplicationContext();
        cachedMovieListByRating = QueryPreferences.getMovieListByRating(context);
        cachedMovieListByPopularity = QueryPreferences.getMovieListByPopularity(context);
    }

    /**
     * Return the singleton instance of this class. The instance will be created in memory in the
     * first call to this method. This method is thread safe.
     *
     * @param context the calling context.
     * @return the {@link MovieListManager} singleton object.
     */
    public static MovieListManager getInstance(final Context context) {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new MovieListManager(context);
            }
            lock.unlock();
        }
        return instance;
    }

    /**
     * Returns the cached {@link MovieList} object which is associated with the specified sortOrder.
     * This method is thread safe.
     *
     * @param sortOrder the sort order. One of the constants defined in {@link MovieUtils}.
     * @return the {@link MovieList} object corresponding to sortOrder.
     */
    @Nullable
    public MovieList getCachedMovieList(final int sortOrder) {
        if (sortOrder == MovieUtils.SORT_ORDER_BY_POPULARITY) {
            return cachedMovieListByPopularity;
        } else if (sortOrder == MovieUtils.SORT_ORDER_BY_RATING) {
            return cachedMovieListByRating;
        } else {
            throw new IllegalArgumentException("sortOrder must be of the constants defined in MovieUtils");
        }
    }

    /**
     * Atomically assign a new value to the {@link MovieList} object associated with sortOrder and persist
     * the new value to disk. This method is thread safe.
     *
     * @param movieList the new {@link MovieList} object to be cached.
     * @param sortOrder the sort order. One of the constants defined in {@link MovieUtils}.
     */
    private void setCachedMovieList(@Nullable MovieList movieList, final int sortOrder) {
        lock.lock();
        if (sortOrder == MovieUtils.SORT_ORDER_BY_RATING) {
            cachedMovieListByRating = movieList;
            QueryPreferences.setMovieListByRating(context, movieList);

        } else if (sortOrder == MovieUtils.SORT_ORDER_BY_POPULARITY) {
            cachedMovieListByPopularity = movieList;
            QueryPreferences.setMovieListByPopularity(context, movieList);

        } else {
            throw new IllegalArgumentException("sortOrder must be one of the constants defined in MovieUtils");
        }
        lock.unlock();
    }

    /**
     * Synchronously fetch a new value for the {@link MovieList} object associated with sortOrder and assign it
     * to be the new cached value. This method is thread safe.
     *
     * @param movieDB   the {@link MovieDB} object created by {@link retrofit2.Retrofit}.
     * @param sortOrder the sort order. Must be one of the constants defined in {@link MovieUtils}.
     * @return a newly fetched {@link MovieList} object which is associated with sortOrder.
     */
    @Nullable
    public MovieList updateMovieListBlocking(@NonNull final MovieDB movieDB, final int sortOrder) {
        /*
         * thread safety is guaranteed because the only mutation is done using the thread safe method
         * setCachedMovieList().
         */
        final String sortBy = MovieUtils.getSortByQueryParameter(sortOrder);
        MovieList body;
        try {
            body = movieDB.getMovies(sortBy).execute().body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Networking error while requesting movieList", e);
            return null;
        }
        setCachedMovieList(body, sortOrder);
        return body;
    }

    /**
     * Asynchronously fetch the {@link MovieList} object associated with sortOrder from TheMovieDB
     * API and update the cached value and invoke listener with the result if it is not null.
     * Returns a {@link CompletableFuture} that will be completed with the result.
     * This method is thread safe.
     *
     * @param movieDB   the {@link MovieDB} object created by {@link retrofit2.Retrofit}.
     * @param sortOrder the sort order. Must be one of the constants defined in {@link MovieUtils}.
     * @param listener  the {@link OnNewMovieListReceivedListener} that will be called
     *                  with the new {@link MovieList} once it is received.
     * @return the {@link CompletableFuture} that will be completed after
     * the new {@link MovieList} is received.
     */
    public CompletableFuture<MovieList> updateMovieListAsync(@NonNull final MovieDB movieDB,
                                                             final int sortOrder,
                                                             @Nullable final OnNewMovieListReceivedListener listener) {
        final CompletableFuture<MovieList> future = new CompletableFuture<>();
        new AsyncTask<Void, Void, MovieList>() {
            @Override
            protected MovieList doInBackground(Void... voids) {
                return updateMovieListBlocking(movieDB, sortOrder);
            }

            @Override
            protected void onPostExecute(@Nullable MovieList movieList) {
                if (listener != null && movieList != null) {
                    listener.onMovieListReceived(movieList);
                }
                if (movieList != null) {
                    future.complete(movieList);
                } else {
                    future.cancel(true);
                }
            }
        }.execute();
        return future;
    }

    /**
     * A listener that will execute once a new {@link MovieList} object is received
     */
    public interface OnNewMovieListReceivedListener {
        /**
         * Will execute once a new {@link MovieList} object is received
         *
         * @param movieList the new {@link MovieList} object
         */
        void onMovieListReceived(@NonNull MovieList movieList);
    }
}
