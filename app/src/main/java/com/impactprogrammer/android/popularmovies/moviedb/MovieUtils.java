package com.impactprogrammer.android.popularmovies.moviedb;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.impactprogrammer.android.popularmovies.BuildConfig;

import java.util.List;

/**
 * Utility class that provides methods to help with requests to TheMovieDB API.
 */

public final class MovieUtils {
    public static final int SORT_ORDER_UNDEFINED = -1;
    public static final int SORT_ORDER_BY_RATING = 0;
    public static final int SORT_ORDER_BY_POPULARITY = 1;

    private MovieUtils() {
    }

    /**
     * Returns a {@link Uri} that can be used to get the poster associated with the supplied
     * {@link Movie} object
     *
     * @param movie    the {@link Movie} object for which a poster {@link Uri} is requested
     * @param sizeHint the desired minimum image width in pixels. The returned {@link Uri} may point
     *                 to an image with a smaller size if the desired size is not available
     * @return a {@link Uri} with the location of the request poster image
     */
    @Nullable
    public static Uri getPosterUri(Context context, @Nullable Movie movie, int sizeHint) {
        MovieImageConfiguration movieImageConfiguration =
                MovieImageConfigurationManager.getInstance(context).getCachedMovieImageConfiguration();
        if (movieImageConfiguration == null) {
            return null;
        }
        List<String> sizeList = movieImageConfiguration.getPosterSizes();
        String posterSize = sizeList.get(0);

        // sizeNumber: for a size of w500, sizeNumber is 500
        // this loop will find the smallest size of the image which is at least sizeHint
        for (int i = 0, sizeNumber = 0; i < sizeList.size() && sizeNumber < sizeHint; ++i) {
            posterSize = sizeList.get(i);
            sizeNumber = Integer.parseInt(posterSize.substring(1));
        }
        return Uri.parse(movieImageConfiguration.getSecureBaseUrl() + posterSize
                + (movie != null ? movie.getPosterPath() : ""))
                .buildUpon()
                .appendQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                .build();
    }

    /**
     * Returns the sort_by query parameter string to be appended to the movie list request {@link Uri}
     * which corresponds to sortOrder
     *
     * @param sortOrder the sort order for which the corresponding sort_by query parameter will be returned.
     *                  Must be one of the SORT_BY constants defined in {@link MovieUtils}.
     * @return the sort_by query parameter string corresponding to sortOrder
     */
    @Nullable
    public static String getSortByQueryParameter(int sortOrder) {
        final String sortBy;
        if (sortOrder == SORT_ORDER_BY_RATING) {
            sortBy = "vote_average.desc";
        } else if (sortOrder == SORT_ORDER_BY_POPULARITY) {
            sortBy = "popularity.desc";
        } else {
            throw new AssertionError("unknown sort order");
        }
        return sortBy;
    }
}
