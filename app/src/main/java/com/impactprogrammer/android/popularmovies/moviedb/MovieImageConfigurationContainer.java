package com.impactprogrammer.android.popularmovies.moviedb;

/**
 * The root JSON object obtained from TheMovieDB API configuration request,
 * it contains the image configuration object. This class is immutable.
 */

public class MovieImageConfigurationContainer {
    private final MovieImageConfiguration images;

    public MovieImageConfigurationContainer(MovieImageConfiguration images) {
        this.images = images;
    }

    public MovieImageConfiguration getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "MovieImageConfigurationContainer{" +
                "images=" + images +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieImageConfigurationContainer that = (MovieImageConfigurationContainer) o;

        return images != null ? images.equals(that.images) : that.images == null;

    }

    @Override
    public int hashCode() {
        return images != null ? images.hashCode() : 0;
    }
}
