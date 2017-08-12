package com.impactprogrammer.android.popularmovies.moviedb;

/**
 * An immutable movie object to hold the JSON data returned from TheMovieDB API which contains information
 * about a single movie.
 */

public class Movie {
    private final String id;
    private final String title;
    private final String original_title;
    private final double vote_average;
    private final long vote_count;
    private final double popularity;
    private final String overview;
    private final String release_date;
    private final boolean isAdult;
    private final String poster_path;
    private final String backdrop_path;

    public Movie(String id,
                 String title,
                 String original_title,
                 double vote_average,
                 long vote_count,
                 double popularity,
                 String overview,
                 String release_date,
                 boolean isAdult,
                 String poster_path,
                 String backdrop_path) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.popularity = popularity;
        this.overview = overview;
        this.release_date = release_date;
        this.isAdult = isAdult;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public long getVoteCount() {
        return vote_count;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", original_title='" + original_title + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", popularity=" + popularity +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", isAdult=" + isAdult +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (Double.compare(movie.vote_average, vote_average) != 0) return false;
        if (vote_count != movie.vote_count) return false;
        if (Double.compare(movie.popularity, popularity) != 0) return false;
        if (isAdult != movie.isAdult) return false;
        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (original_title != null ? !original_title.equals(movie.original_title) : movie.original_title != null)
            return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        if (release_date != null ? !release_date.equals(movie.release_date) : movie.release_date != null)
            return false;
        return poster_path != null ? poster_path.equals(movie.poster_path) : movie.poster_path == null && (backdrop_path != null ? backdrop_path.equals(movie.backdrop_path) : movie.backdrop_path == null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (original_title != null ? original_title.hashCode() : 0);
        temp = Double.doubleToLongBits(vote_average);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (vote_count ^ (vote_count >>> 32));
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        result = 31 * result + (isAdult ? 1 : 0);
        result = 31 * result + (poster_path != null ? poster_path.hashCode() : 0);
        result = 31 * result + (backdrop_path != null ? backdrop_path.hashCode() : 0);
        return result;
    }
}
