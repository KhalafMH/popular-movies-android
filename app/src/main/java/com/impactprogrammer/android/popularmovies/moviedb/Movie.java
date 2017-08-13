package com.impactprogrammer.android.popularmovies.moviedb;

/**
 * An immutable movie object to hold the JSON data returned from TheMovieDB API which contains information
 * about a single movie.
 */

public class Movie {
    private final String id;
    private final String title;
    private final String originalTitle;
    private final double voteAverage;
    private final long voteCount;
    private final double popularity;
    private final String overview;
    private final String releaseDate;
    private final boolean isAdult;
    private final String posterPath;
    private final String backdropPath;

    public Movie(String id,
                 String title,
                 String originalTitle,
                 double voteAverage,
                 long voteCount,
                 double popularity,
                 String overview,
                 String releaseDate,
                 boolean isAdult,
                 String posterPath,
                 String backdropPath) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.popularity = popularity;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isAdult = isAdult;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                ", popularity=" + popularity +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", isAdult=" + isAdult +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (Double.compare(movie.voteAverage, voteAverage) != 0) return false;
        if (voteCount != movie.voteCount) return false;
        if (Double.compare(movie.popularity, popularity) != 0) return false;
        if (isAdult != movie.isAdult) return false;
        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (originalTitle != null ? !originalTitle.equals(movie.originalTitle) : movie.originalTitle != null)
            return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null)
            return false;
        return posterPath != null ? posterPath.equals(movie.posterPath) : movie.posterPath == null && (backdropPath != null ? backdropPath.equals(movie.backdropPath) : movie.backdropPath == null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        temp = Double.doubleToLongBits(voteAverage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (voteCount ^ (voteCount >>> 32));
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (isAdult ? 1 : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        return result;
    }
}
