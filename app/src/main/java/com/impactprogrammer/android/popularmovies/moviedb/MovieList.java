package com.impactprogrammer.android.popularmovies.moviedb;

import java.util.Collections;
import java.util.List;

/**
 * Immutable object representing a JSON response from TheMovieDB API containing a list of movies.
 */

public class MovieList {
    private final long page;
    private final long total_results;
    private final long total_pages;
    private final List<Movie> results;

    public MovieList(long page, long total_results, long total_pages, List<Movie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public long getPage() {
        return page;
    }

    public long getTotalResults() {
        return total_results;
    }

    public long getTotalPages() {
        return total_pages;
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
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", results=" + results +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieList movieList = (MovieList) o;

        if (page != movieList.page) return false;
        if (total_results != movieList.total_results) return false;
        return total_pages == movieList.total_pages && (results != null ? results.equals(movieList.results) : movieList.results == null);

    }

    @Override
    public int hashCode() {
        int result = (int) (page ^ (page >>> 32));
        result = 31 * result + (int) (total_results ^ (total_results >>> 32));
        result = 31 * result + (int) (total_pages ^ (total_pages >>> 32));
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }
}
