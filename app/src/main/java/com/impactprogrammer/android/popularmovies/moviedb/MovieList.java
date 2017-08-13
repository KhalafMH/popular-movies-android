package com.impactprogrammer.android.popularmovies.moviedb;

import java.util.Collections;
import java.util.List;

/**
 * Immutable object representing a JSON response from TheMovieDB API containing a list of movies.
 */

public class MovieList {
    private final long page;
    private final long totalResults;
    private final long totalPages;
    private final List<Movie> results;

    public MovieList(long page, long totalResults, long totalPages, List<Movie> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public long getPage() {
        return page;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    /**
     * @return unmodifiable list of results
     */
    public List<Movie> getResults() {
        return Collections.unmodifiableList(results);
    }

    @Override
    public String toString() {
        return "MovieList{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + results +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieList movieList = (MovieList) o;

        if (page != movieList.page) return false;
        if (totalResults != movieList.totalResults) return false;
        return totalPages == movieList.totalPages && (results != null ? results.equals(movieList.results) : movieList.results == null);

    }

    @Override
    public int hashCode() {
        int result = (int) (page ^ (page >>> 32));
        result = 31 * result + (int) (totalResults ^ (totalResults >>> 32));
        result = 31 * result + (int) (totalPages ^ (totalPages >>> 32));
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }
}
