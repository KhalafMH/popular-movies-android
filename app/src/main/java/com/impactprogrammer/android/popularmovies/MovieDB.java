package com.impactprogrammer.android.popularmovies;

import com.impactprogrammer.android.popularmovies.moviedb.MovieImageConfigurationContainer;
import com.impactprogrammer.android.popularmovies.moviedb.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * This interface will be used with Retrofit to interact with the API of TheMovieDB.org
 */

public interface MovieDB {
    /**
     * Fetches a {@link MovieList} from TheMovieDB.org API containing a list of movies sorted by sortBy.
     *
     * @param sortBy the path parameter used to specify sort order in requests to TheMovieDB.org.
     * @return A {@link Call} that fetches a {@link MovieList} from TheMovieDB.org
     */
    @GET("movie/{sortBy}?api_key=" + BuildConfig.MOVIE_DB_API_KEY)
    Call<MovieList> getMovies(@Path("sortBy") String sortBy);

    /**
     * Fetches a {@link MovieImageConfigurationContainer} from TheMovieDB.org containing the image
     * configuration object.
     *
     * @return A {@link Call} that fetches a {@link MovieImageConfigurationContainer} from TheMovieDB.org
     */
    @GET("configuration?api_key=" + BuildConfig.MOVIE_DB_API_KEY)
    Call<MovieImageConfigurationContainer> configuration();
}
