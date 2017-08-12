package com.impactprogrammer.android.popularmovies;

import com.impactprogrammer.android.popularmovies.moviedb.MovieImageConfigurationContainer;
import com.impactprogrammer.android.popularmovies.moviedb.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This interface will be used with Retrofit to interact with the API of TheMovieDB.org
 */

public interface MovieDB {
    /**
     * Fetches a {@link MovieList} from TheMovieDB.org API containing a list of movies sorted by sortBy.
     *
     * @param sortBy the query parameter used to specify sort order in requests to TheMovieDB.org.
     *               Valid examples are: "vote_average.desc" and "popularity.desc"
     * @return A {@link Call} that fetches a {@link MovieList} from TheMovieDB.org
     */
    @GET("discover/movie?api_key=" + BuildConfig.MOVIE_DB_API_KEY)
    Call<MovieList> getMovies(@Query("sort_by") String sortBy);

    /**
     * Fetches a {@link MovieImageConfigurationContainer} from TheMovieDB.org containing the image
     * configuration object.
     *
     * @return A {@link Call} that fetches a {@link MovieImageConfigurationContainer} from TheMovieDB.org
     */
    @GET("configuration?api_key=" + BuildConfig.MOVIE_DB_API_KEY)
    Call<MovieImageConfigurationContainer> configuration();
}
