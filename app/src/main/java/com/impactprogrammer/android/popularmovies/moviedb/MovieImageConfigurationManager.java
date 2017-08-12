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
 * A singleton class that will hold and manage updates to the cached {@link MovieImageConfiguration}
 * object that will be used throughout the app. This object is thread safe.
 */

public class MovieImageConfigurationManager {
    private static final String LOG_TAG = MovieImageConfigurationManager.class.getSimpleName();
    private static final Lock lock = new ReentrantLock();
    @Nullable
    private static MovieImageConfigurationManager instance = null;

    private final Context context;

    @Nullable
    private MovieImageConfiguration cachedMovieImageConfiguration;

    private MovieImageConfigurationManager(final Context context) {
        this.context = context.getApplicationContext();
        cachedMovieImageConfiguration = QueryPreferences.getMovieImageConfiguration(this.context);
    }

    /**
     * Returns the singleton instance of this class. The instance will be created in memory in the
     * first call to this method. This method is thread safe.
     *
     * @param context the calling context
     * @return the singleton {@link MovieImageConfigurationManager} instance
     */
    public static MovieImageConfigurationManager getInstance(final Context context) {
        if (instance == null) {
            // synchronize inside if block instead of outside for better performance.
            lock.lock();
            // double check to prevent race condition.
            if (instance == null) {
                instance = new MovieImageConfigurationManager(context);
            }
            lock.unlock();
        }
        return instance;
    }

    /**
     * Returns the cached {@link MovieImageConfiguration} object. This method is thread safe.
     *
     * @return the cached {@link MovieImageConfiguration} object
     */
    @Nullable
    public MovieImageConfiguration getCachedMovieImageConfiguration() {
        /*
         * This method is thread safe because it does not mutate the object.
         */
        return cachedMovieImageConfiguration;
    }

    /**
     * Atomically assigns a new value to the cached {@link MovieImageConfiguration} object and persists the
     * new value to disk. This method is thread safe.
     *
     * @param configuration the new {@link MovieImageConfiguration} object to be cached
     */
    private void setCachedMovieImageConfiguration(@Nullable final MovieImageConfiguration configuration) {
        lock.lock();
        this.cachedMovieImageConfiguration = configuration;
        QueryPreferences.setMovieImageConfiguration(context, configuration);
        lock.unlock();
    }

    /**
     * Synchronously fetch the {@link MovieImageConfiguration} object from TheMovieDB API and update
     * the cached value. This method is thread safe.
     *
     * @param movieDB the {@link MovieDB} object created by {@link retrofit2.Retrofit}.
     * @return A newly fetched {@link MovieImageConfiguration} object from TheMovieDB API.
     */
    @Nullable
    public MovieImageConfiguration updateMovieImageConfigurationBlocking(@NonNull final MovieDB movieDB) {
        /*
         * thread safety is guaranteed because the only mutation is done using the thread safe method
         * setCachedMovieImageConfiguration().
         */
        MovieImageConfigurationContainer configurationContainer;
        try {
            configurationContainer = movieDB.configuration().execute().body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Network error while updating MovieImageConfiguration", e);
            return null;
        }
        if (configurationContainer == null) {
            return null;
        }
        MovieImageConfiguration newConfiguration = configurationContainer.getImages();
        setCachedMovieImageConfiguration(newConfiguration);
        return cachedMovieImageConfiguration;
    }

    /**
     * Asynchronously fetch the {@link MovieImageConfiguration} object from TheMovieDB API and update
     * the cached value and invoke listener with the result if it is not null. Returns a
     * {@link CompletableFuture} that will be completed with the result.
     * This method is thread safe.
     *
     * @param movieDB  the {@link MovieDB} object created by {@link retrofit2.Retrofit}.
     * @param listener the {@link OnNewMovieImageConfigurationReceivedListener} that will be called
     *                 with the new {@link MovieImageConfiguration} once it is received.
     * @return the {@link CompletableFuture} that will be completed after
     * the new {@link MovieImageConfiguration} is received.
     */
    public CompletableFuture<MovieImageConfiguration> updateMovieImageConfigurationAsync(@NonNull final MovieDB movieDB,
                                                                                         @Nullable final OnNewMovieImageConfigurationReceivedListener listener) {
        final CompletableFuture<MovieImageConfiguration> future = new CompletableFuture<>();
        new AsyncTask<Void, Void, MovieImageConfiguration>() {
            @Override
            @Nullable
            protected MovieImageConfiguration doInBackground(Void... voids) {
                return updateMovieImageConfigurationBlocking(movieDB);
            }

            @Override
            protected void onPostExecute(@Nullable MovieImageConfiguration configuration) {
                if (listener != null && configuration != null) {
                    listener.onMovieImageConfigurationReceived(configuration);
                }
                if (configuration != null) {
                    future.complete(configuration);
                } else {
                    future.cancel(true);
                }

            }
        }.execute();
        return future;
    }

    /**
     * A listener that will execute once a new {@link MovieImageConfiguration} object is received
     */
    public interface OnNewMovieImageConfigurationReceivedListener {
        /**
         * Will execute once a new {@link MovieImageConfiguration} object is received
         *
         * @param configuration the new {@link MovieImageConfiguration} object
         */
        void onMovieImageConfigurationReceived(@NonNull final MovieImageConfiguration configuration);
    }
}
