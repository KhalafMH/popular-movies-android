package com.impactprogrammer.android.popularmovies.moviedb;

import java.util.Collections;
import java.util.List;

/**
 * An immutable object to hold the JSON configuration information related to images on TheMovieDB API.
 */

public class MovieImageConfiguration {
    private final String base_url;
    private final String secure_base_url;
    private final List<String> backdrop_sizes;
    private final List<String> poster_sizes;
    private final List<String> logo_sizes;
    private final List<String> profile_sizes;
    private final List<String> still_sizes;

    public MovieImageConfiguration(
            String base_url,
            String secure_base_url,
            List<String> backdrop_sizes,
            List<String> poster_sizes,
            List<String> logo_sizes,
            List<String> profile_sizes,
            List<String> still_sizes) {
        this.base_url = base_url;
        this.secure_base_url = secure_base_url;
        this.backdrop_sizes = backdrop_sizes;
        this.poster_sizes = poster_sizes;
        this.logo_sizes = logo_sizes;
        this.profile_sizes = profile_sizes;
        this.still_sizes = still_sizes;
    }

    public String getBaseUrl() {
        return base_url;
    }

    public String getSecureBaseUrl() {
        return secure_base_url;
    }

    /**
     * @return unmodifiable list of backdrop_sizes
     */
    public List<String> getBackdropSizes() {
        return Collections.unmodifiableList(backdrop_sizes);
    }

    /**
     * @return unmodifiable list of poster_sizes
     */
    public List<String> getPosterSizes() {
        return Collections.unmodifiableList(poster_sizes);
    }

    /**
     * @return unmodifiable list of logo_sizes
     */
    public List<String> getLogoSizes() {
        return Collections.unmodifiableList(logo_sizes);
    }

    /**
     * @return unmodifiable list of profile_sizes
     */
    public List<String> getProfileSizes() {
        return Collections.unmodifiableList(profile_sizes);
    }

    /**
     * @return unmodifiable list of still_sizes
     */
    public List<String> getStillSizes() {
        return Collections.unmodifiableList(still_sizes);
    }

    @Override
    public String toString() {
        return "MovieImageConfiguration{" +
                "base_url='" + base_url + '\'' +
                ", secure_base_url='" + secure_base_url + '\'' +
                ", backdrop_sizes=" + backdrop_sizes +
                ", poster_sizes=" + poster_sizes +
                ", logo_sizes=" + logo_sizes +
                ", profile_sizes=" + profile_sizes +
                ", still_sizes=" + still_sizes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieImageConfiguration that = (MovieImageConfiguration) o;

        if (base_url != null ? !base_url.equals(that.base_url) : that.base_url != null)
            return false;
        if (secure_base_url != null ? !secure_base_url.equals(that.secure_base_url) : that.secure_base_url != null)
            return false;
        if (backdrop_sizes != null ? !backdrop_sizes.equals(that.backdrop_sizes) : that.backdrop_sizes != null)
            return false;
        if (poster_sizes != null ? !poster_sizes.equals(that.poster_sizes) : that.poster_sizes != null)
            return false;
        if (logo_sizes != null ? !logo_sizes.equals(that.logo_sizes) : that.logo_sizes != null)
            return false;
        return profile_sizes != null ? profile_sizes.equals(that.profile_sizes) : that.profile_sizes == null && (still_sizes != null ? still_sizes.equals(that.still_sizes) : that.still_sizes == null);

    }

    @Override
    public int hashCode() {
        int result = base_url != null ? base_url.hashCode() : 0;
        result = 31 * result + (secure_base_url != null ? secure_base_url.hashCode() : 0);
        result = 31 * result + (backdrop_sizes != null ? backdrop_sizes.hashCode() : 0);
        result = 31 * result + (poster_sizes != null ? poster_sizes.hashCode() : 0);
        result = 31 * result + (logo_sizes != null ? logo_sizes.hashCode() : 0);
        result = 31 * result + (profile_sizes != null ? profile_sizes.hashCode() : 0);
        result = 31 * result + (still_sizes != null ? still_sizes.hashCode() : 0);
        return result;
    }
}
