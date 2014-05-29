package fr.poulpi.pegasus.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.adapters.PredictionListAdapter;
import fr.poulpi.pegasus.model.GoogleAPIPredictionsResponse;
import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;


public class PredictionsFragment extends Fragment {

    public static final String TAG = "PredictionsFragment";
    private OnFragmentInteractionListener mListener;
    private PredictionListAdapter mAdapter;

    private static String MODE = "MODE";
    public static String TO = "TO";
    public static String FROM = "FROM";

    ListView mListView;

    public static PredictionsFragment newInstance(String mode) {
        PredictionsFragment fragment = new PredictionsFragment();
        Bundle args = new Bundle();
        args.putString(MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PredictionsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_predictions, container, false);

        mListView = (ListView) view.findViewById(R.id.list);

        mListView.setAdapter(new PredictionListAdapter(getActivity(), new ArrayList<GoogleAPIResultPrediction>()));
        mListView.setEmptyView(view.findViewById(R.id.empty));
        mListView.setItemsCanFocus(true);
        mListView.setOnItemClickListener(onItemClickListener);

        return view;

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

    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.d("poulpi", "Clickyyyyy");
            mListener.onPredictionsFragmentInteraction(getArguments().getString(MODE), mAdapter.getItem(position));
            getFragmentManager().popBackStack();

        }
    };

    public void refreshData(GoogleAPIPredictionsResponse response) {

        ArrayList<GoogleAPIResultPrediction> list = new ArrayList<GoogleAPIResultPrediction>(response.getPredictions());

        mAdapter = new PredictionListAdapter(getActivity(), list);

        mListView.setAdapter(mAdapter);

    }

    public interface OnFragmentInteractionListener {
        public void onPredictionsFragmentInteraction(String mode, GoogleAPIResultPrediction destination);
    }

}
