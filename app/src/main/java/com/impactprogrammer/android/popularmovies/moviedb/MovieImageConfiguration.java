package com.impactprogrammer.android.popularmovies.moviedb;

import java.util.Collections;
import java.util.List;

/**
 * An immutable object to hold the JSON configuration information related to images on TheMovieDB API.
 */

public class MovieImageConfiguration {
    private final String baseUrl;
    private final String secureBaseUrl;
    private final List<String> backdropSizes;
    private final List<String> posterSizes;
    private final List<String> logoSizes;
    private final List<String> profileSizes;
    private final List<String> stillSizes;

    public MovieImageConfiguration(
            String baseUrl,
            String secureBaseUrl,
            List<String> backdropSizes,
            List<String> posterSizes,
            List<String> logoSizes,
            List<String> profileSizes,
            List<String> stillSizes) {
        this.baseUrl = baseUrl;
        this.secureBaseUrl = secureBaseUrl;
        this.backdropSizes = backdropSizes;
        this.posterSizes = posterSizes;
        this.logoSizes = logoSizes;
        this.profileSizes = profileSizes;
        this.stillSizes = stillSizes;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    /**
     * @return unmodifiable list of backdropSizes
     */
    public List<String> getBackdropSizes() {
        return Collections.unmodifiableList(backdropSizes);
    }

    /**
     * @return unmodifiable list of posterSizes
     */
    public List<String> getPosterSizes() {
        return Collections.unmodifiableList(posterSizes);
    }

    /**
     * @return unmodifiable list of logoSizes
     */
    public List<String> getLogoSizes() {
        return Collections.unmodifiableList(logoSizes);
    }

    /**
     * @return unmodifiable list of profileSizes
     */
    public List<String> getProfileSizes() {
        return Collections.unmodifiableList(profileSizes);
    }

    /**
     * @return unmodifiable list of stillSizes
     */
    public List<String> getStillSizes() {
        return Collections.unmodifiableList(stillSizes);
    }

    @Override
    public String toString() {
        return "MovieImageConfiguration{" +
                "baseUrl='" + baseUrl + '\'' +
                ", secureBaseUrl='" + secureBaseUrl + '\'' +
                ", backdropSizes=" + backdropSizes +
                ", posterSizes=" + posterSizes +
                ", logoSizes=" + logoSizes +
                ", profileSizes=" + profileSizes +
                ", stillSizes=" + stillSizes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieImageConfiguration that = (MovieImageConfiguration) o;

        if (baseUrl != null ? !baseUrl.equals(that.baseUrl) : that.baseUrl != null)
            return false;
        if (secureBaseUrl != null ? !secureBaseUrl.equals(that.secureBaseUrl) : that.secureBaseUrl != null)
            return false;
        if (backdropSizes != null ? !backdropSizes.equals(that.backdropSizes) : that.backdropSizes != null)
            return false;
        if (posterSizes != null ? !posterSizes.equals(that.posterSizes) : that.posterSizes != null)
            return false;
        if (logoSizes != null ? !logoSizes.equals(that.logoSizes) : that.logoSizes != null)
            return false;
        return profileSizes != null ? profileSizes.equals(that.profileSizes) : that.profileSizes == null && (stillSizes != null ? stillSizes.equals(that.stillSizes) : that.stillSizes == null);

    }

    @Override
    public int hashCode() {
        int result = baseUrl != null ? baseUrl.hashCode() : 0;
        result = 31 * result + (secureBaseUrl != null ? secureBaseUrl.hashCode() : 0);
        result = 31 * result + (backdropSizes != null ? backdropSizes.hashCode() : 0);
        result = 31 * result + (posterSizes != null ? posterSizes.hashCode() : 0);
        result = 31 * result + (logoSizes != null ? logoSizes.hashCode() : 0);
        result = 31 * result + (profileSizes != null ? profileSizes.hashCode() : 0);
        result = 31 * result + (stillSizes != null ? stillSizes.hashCode() : 0);
        return result;
    }
}
