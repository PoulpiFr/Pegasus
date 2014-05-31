package fr.poulpi.pegasus.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.SuggestedItinariesActivity;
import fr.poulpi.pegasus.model.CTPJourney;
import fr.poulpi.pegasus.model.CTPSection;

public class SuggestedItinariesGoogleMapFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private GoogleMap googleMap;

    public static SuggestedItinariesGoogleMapFragment newInstance() {
        SuggestedItinariesGoogleMapFragment fragment = new SuggestedItinariesGoogleMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SuggestedItinariesGoogleMapFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_suggested_itinaries_google_map, container, false);

        SupportMapFragment fm = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);

        LatLng parisLatLng = new LatLng(48.8566140, 2.3522219);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(parisLatLng, 11);

        googleMap = fm.getMap();
        googleMap.moveCamera(cameraUpdate);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getActivity() instanceof SuggestedItinariesActivity.journeysProvider) {
                ArrayList<CTPJourney> list = ((SuggestedItinariesActivity.journeysProvider)getActivity()).getJourneys();

                Iterator<CTPJourney> itJourney = list.iterator();
                Iterator<CTPSection> itSection;
                while(itJourney.hasNext()){
                    itSection = itJourney.next().getSections().iterator();
                    while (itSection.hasNext()){
                        itSection.next().get
                    }
                }
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

public interface OnFragmentInteractionListener {
    public void onSuggestedItinariesGoogleMapFragmentInteraction(Uri uri);
}

}
