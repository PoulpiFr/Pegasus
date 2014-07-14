package fr.poulpi.pegasus.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import fr.poulpi.pegasus.ItinaryDetailsActivity;
import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.model.CTPJourney;
import fr.poulpi.pegasus.model.CTPSection;
import fr.poulpi.pegasus.view.ItinaryTransportSection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItinaryDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItinaryDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ItinaryDetailsFragment extends Fragment {

    private CTPJourney mJourney;

    private OnFragmentInteractionListener mListener;

    public static ItinaryDetailsFragment newInstance() {
        ItinaryDetailsFragment fragment = new ItinaryDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ItinaryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJourney = ((ItinaryDetailsActivity)getActivity()).getJourney();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_itinary_details, container, false);

        LinearLayout ll = (LinearLayout) v;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Iterator<CTPSection> it = mJourney.getSections().iterator();

        while (it.hasNext()) {
            CTPSection tempSection = it.next();

            if(tempSection.getType() != null && tempSection.getType().equals(getString(R.string.navitia_api_public_transport))) {
                Bundle bundle = new Bundle();
                bundle.putString(ItinaryTransportSection.DEPARTURE_TIME, sdf.format(tempSection.getDeparture_date_time()));
                bundle.putString(ItinaryTransportSection.ARRIVAL_TIME, sdf.format(tempSection.getArrival_date_time()));

                bundle.putString(ItinaryTransportSection.DEPARTURE_PLACE, tempSection.getFrom().getName());
                bundle.putString(ItinaryTransportSection.ARRIVAL_PLACE, tempSection.getTo().getName());

                bundle.putString(ItinaryTransportSection.DIRECTION, "dir. " + tempSection.getDisplay_informations().getDirection());
                bundle.putString(ItinaryTransportSection.TRANSPORT_TIME, (tempSection.getDuration() / 60) + " min");

                bundle.putString(ItinaryTransportSection.METRO_LINE, tempSection.getDisplay_informations().getLabel());

                ll.addView(ItinaryTransportSection.newInstance(getActivity(), bundle));
            }
        }
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
