package com.impactprogrammer.android.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * An activity that holds a single fragment.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private Fragment mFragment;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        mFragment = fm.findFragmentById(R.id.fragmentContainer);
        if (mFragment == null) {
            mFragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, mFragment)
                    .commit();
        }
    }

    /**
     * Create a fragment to be hosted by this activity.
     *
     * @return A {@link Fragment}.
     */
    protected abstract Fragment createFragment();
}
