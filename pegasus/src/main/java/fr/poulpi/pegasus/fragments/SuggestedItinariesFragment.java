package fr.poulpi.pegasus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.poulpi.pegasus.ItinaryDetailsActivity;
import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.SuggestedItinariesActivity;
import fr.poulpi.pegasus.adapters.SuggestedItinaryListAdapter;
import fr.poulpi.pegasus.constants.GoogleAPIConf;
import fr.poulpi.pegasus.interfaces.GooglePlaceAPIInterface;
import fr.poulpi.pegasus.interfaces.NavitiaIoInterface;
import fr.poulpi.pegasus.model.CTPJourney;
import fr.poulpi.pegasus.model.CTPJourneyResponse;
import fr.poulpi.pegasus.model.GoogleAPIDetailsPlace;
import fr.poulpi.pegasus.model.GoogleAPIGeometry;
import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class SuggestedItinariesFragment extends Fragment {

    SuggestedItinaryListAdapter mAdapter;

    public static final String TAG = "SuggestedItinariesFragment";

    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private TextView mFromTv;
    private TextView mToTv;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(getActivity(), ItinaryDetailsActivity.class);
            i.putExtra(ItinaryDetailsActivity.JOURNEY, ((SuggestedItinariesActivity)getActivity()).getJourneys().get(position));
            startActivity(i);
        }
    };

    public static SuggestedItinariesFragment newInstance(Bundle bundle) {
        SuggestedItinariesFragment fragment = new SuggestedItinariesFragment();

        fragment.setArguments(bundle);
        return fragment;
    }

    public SuggestedItinariesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_suggested_itinaries, container, false);

        mListView = (ListView) view.findViewById(R.id.list);
        mAdapter = new SuggestedItinaryListAdapter(getActivity(), new ArrayList<CTPJourney>());
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(view.findViewById(R.id.empty));
        mListView.setItemsCanFocus(true);
        mListView.setOnItemClickListener(onItemClickListener);

        mFromTv = (TextView) view.findViewById(R.id.destination_from_text);
        mToTv = (TextView) view.findViewById(R.id.destination_to_text);
        mFromTv.setText(getArguments().getString(SuggestedItinariesActivity.FROM_NAME));
        mToTv.setText(getArguments().getString(SuggestedItinariesActivity.TO_NAME));

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

    public void refreshFragment(CTPJourneyResponse journeyResponse){
        mAdapter = new SuggestedItinaryListAdapter(getActivity(), journeyResponse.getJourneys());
        mListView.setAdapter(mAdapter);
    }

    public interface OnFragmentInteractionListener {
        public void onSuggestedItinariesFragmentInteraction(Uri uri);
    }

}
