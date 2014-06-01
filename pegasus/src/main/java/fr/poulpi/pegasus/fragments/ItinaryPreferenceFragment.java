package fr.poulpi.pegasus.fragments;

/**
 * Created by paul-henri on 3/26/14.
 */

import android.os.Bundle;
import android.preference.PreferenceFragment;
import fr.poulpi.pegasus.R;

public class ItinaryPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.itinary_preferences);
    }

    public static ItinaryPreferenceFragment newInstance() {

        ItinaryPreferenceFragment fragment = new ItinaryPreferenceFragment();
        return fragment;

    }
}