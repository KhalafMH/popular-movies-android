package com.impactprogrammer.android.popularmovies;

import android.support.v4.app.Fragment;

/**
 * Hosts a {@link MovieListFragment} that shows a list of the most popular or highest rated movies.
 */
public class MovieListActivity extends SingleFragmentActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Fragment createFragment() {
        return MovieListFragment.newInstance();
    }
}
