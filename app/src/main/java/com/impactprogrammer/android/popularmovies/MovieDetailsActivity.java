package com.impactprogrammer.android.popularmovies;

import android.support.v4.app.Fragment;

/**
 * This activity will host a {@link Fragment} that will show details about a single movie.
 */
public class MovieDetailsActivity extends SingleFragmentActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Fragment createFragment() {
        return MovieDetailsFragment.newInstance();
    }
}
