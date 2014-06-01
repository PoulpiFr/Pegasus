package fr.poulpi.pegasus.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.poulpi.pegasus.R;

/**
 * Created by paul-henri on 3/26/14.
 */

public class AProposFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    public static AProposFragment newInstance() {

        AProposFragment fragment = new AProposFragment();
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_propos, container, false);
    }

}
