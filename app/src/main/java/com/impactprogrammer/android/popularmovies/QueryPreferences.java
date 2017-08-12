package com.impactprogrammer.android.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.impactprogrammer.android.popularmovies.moviedb.MovieImageConfiguration;
import com.impactprogrammer.android.popularmovies.moviedb.MovieList;
import com.impactprogrammer.android.popularmovies.moviedb.MovieUtils;

/**
 * A singleton that will be responsible for all transactions with the {@link SharedPreferences}.
 */

public class QueryPreferences {
    private static final String KEY_CACHED_MOVIE_IMAGE_CONFIGURATION = "CACHED_MOVIE_IMAGE_CONFIGURATION";
    private static final String KEY_MOVIE_LIST_BY_RATING = "MOVIE_LIST_BY_RATING";
    private static final String KEY_MOVIE_LIST_BY_POPULARITY = "MOVIE_LIST_BY_POPULARITY";
    private static final String KEY_SORT_ORDER = "SORT_ORDER";

    private static int sortOrder = -1;

    /**
     * retrieve the sort order from persistent storage
     *
     * @param context the calling context
     * @return the sort order stored on disk or SORT_ORDER_UNDEFINED.
     */
    static int getSortOrder(Context context) {
        if (sortOrder == -1) {
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            sortOrder = sharedPreferences.getInt(KEY_SORT_ORDER, MovieUtils.SORT_ORDER_BY_POPULARITY);
        }
        return sortOrder;
    }

    /**
     * store the specified sortOrder in persistent storage
     *
     * @param context   the calling context
     * @param sortOrder the sort order to be persisted to disk
     */
    static void setSortOrder(Context context, int sortOrder) {
        QueryPreferences.sortOrder = sortOrder;
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit()
                .putInt(KEY_SORT_ORDER, sortOrder)
                .apply();
    }

    /**
     * retrieve the {@link MovieImageConfiguration} object from persistent storage
     *
     * @param context the calling context
     * @return the {@link MovieImageConfiguration} object stored on disk
     */
    @Nullable
    public static MovieImageConfiguration getMovieImageConfiguration(Context context) {
        return new Gson().fromJson(getSharedPreferences(context)
                .getString(KEY_CACHED_MOVIE_IMAGE_CONFIGURATION, null), MovieImageConfiguration.class);
    }

    /**
     * store the {@link MovieImageConfiguration} object into persistent storage
     *
     * @param context       the calling context
     * @param configuration the {@link MovieImageConfiguration} object to be stored on disk
     */
    public static void setMovieImageConfiguration(Context context, @Nullable MovieImageConfiguration configuration) {
        getSharedPreferences(context).edit()
                .putString(KEY_CACHED_MOVIE_IMAGE_CONFIGURATION, new Gson().toJson(configuration))
                .apply();
    }

    /**
     * retrieve the {@link MovieList} object that is sorted by rating from persistent storage
     *
     * @param context the calling context
     * @return the {@link MovieList} sorted by rating object stored on disk
     */
    @Nullable
    public static MovieList getMovieListByRating(Context context) {
        return new Gson().fromJson(
                getSharedPreferences(context).getString(KEY_MOVIE_LIST_BY_RATING, null),
                MovieList.class
        );
    }

    /**
     * retrieve the {@link MovieList} object that is sorted by popularity from persistent storage
     *
     * @param context the calling context
     * @return the {@link MovieList} sorted by popularity object stored on disk
     */
    @Nullable
    public static MovieList getMovieListByPopularity(Context context) {
        return new Gson().fromJson(
                getSharedPreferences(context).getString(KEY_MOVIE_LIST_BY_POPULARITY, null),
                MovieList.class
        );
    }

    /**
     * store the {@link MovieList} sorted by rating object into persistent storage
     *
     * @param context   the calling context
     * @param movieList the {@link MovieList} sorted by rating object to be stored on disk
     */
    public static void setMovieListByRating(Context context, @Nullable MovieList movieList) {
        getSharedPreferences(context).edit()
                .putString(KEY_MOVIE_LIST_BY_RATING, new Gson().toJson(movieList))
                .apply();
    }

    /**
     * store the {@link MovieList} sorted by popularity object into persistent storage
     *
     * @param context   the calling context
     * @param movieList the {@link MovieList} sorted by popularity object to be stored on disk
     */
    public static void setMovieListByPopularity(Context context, @Nullable MovieList movieList) {
        getSharedPreferences(context).edit()
                .putString(KEY_MOVIE_LIST_BY_POPULARITY, new Gson().toJson(movieList))
                .apply();
    }

    /**
     * get the default {@link SharedPreferences} object that will be used by this class.
     *
     * @param context the calling context
     * @return the default {@link SharedPreferences} object
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        Context applicationContext = context.getApplicationContext();
        return applicationContext.getSharedPreferences(applicationContext.getPackageName(), 0);
    }
}
