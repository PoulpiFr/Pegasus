package fr.poulpi.pegasus.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import fr.poulpi.pegasus.R;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuggestedItinariesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuggestedItinariesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SuggestedItinariesFragment extends Fragment {

    public static final String TO_REF = "to_ref";
    public static final String FROM_REF = "from_ref";
    public static final String TO_NAME = "to_name";
    public static final String FROM_NAME = "from_name";
    public static final String DATE = "date";

    RestAdapter navitiaRestAdapter;
    RestAdapter googleRestAdapter;

    GoogleAPIGeometry from = null;
    GoogleAPIGeometry to = null;

    SuggestedItinaryListAdapter mAdapter;

    public static final String TAG = "SuggestedItinariesFragment";

    private String mFromRef;
    private String mToRef;
    private String mDate;

    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private TextView mFromTv;
    private TextView mToTv;

    public static SuggestedItinariesFragment newInstance(Bundle bundle) {
        SuggestedItinariesFragment fragment = new SuggestedItinariesFragment();

        fragment.setArguments(bundle);
        return fragment;
    }

    public SuggestedItinariesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFromRef = getArguments().getString(FROM_REF);
            mToRef = getArguments().getString(TO_REF);
            mDate = getArguments().getString(DATE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String API_URL = "http://api.navitia.io/v1/";

        // debug purpose only, to get the messages
        RestAdapter.Log log = new RestAdapter.Log(){
            public void log(String msg){
                System.out.println(msg);
            }
        };

        // Set the navitia time format
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return df.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        });

        Gson gson = gsonBuilder.create();

        // Create a very simple REST adapter which points the GitHub API endpoint.
        navitiaRestAdapter = new RestAdapter.Builder()
                .setLog(log)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        // Demande des coordonnées GPS à partir de la référence Places API
        API_URL = "https://maps.googleapis.com/maps/api/place";

        googleRestAdapter = new RestAdapter.Builder()
                .setLog(log)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        GooglePlaceAPIInterface tmp = googleRestAdapter.create(GooglePlaceAPIInterface.class);
        tmp.details("true",
                GoogleAPIConf.API_KEY,
                getArguments().getString(FROM_REF),
                "fr",
                detailsFromCallback);
        tmp.details("true",
                GoogleAPIConf.API_KEY,
                getArguments().getString(TO_REF),
                "fr",
                detailsToCallback);

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
        //mListView.setOnItemClickListener(onItemClickListener);

        mFromTv = (TextView) view.findViewById(R.id.destination_from_text);
        mToTv = (TextView) view.findViewById(R.id.destination_to_text);
        mFromTv.setText(getArguments().getString(FROM_NAME));
        mToTv.setText(getArguments().getString(TO_NAME));

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

    private void askForItinary(){

        String fromStr = new StringBuilder().append(from.location.lng).append(";").append(from.location.lat).toString();
        String toStr = new StringBuilder().append(to.location.lng).append(";").append(to.location.lat).toString();

        NavitiaIoInterface ws = navitiaRestAdapter.create(NavitiaIoInterface.class);
        ws.journey(fromStr, toStr, getArguments().getString("date"), "departure", itinaryCallback);

    }


    Callback itinaryCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            if(o instanceof CTPJourneyResponse) {
                CTPJourneyResponse journeyResponse = (CTPJourneyResponse) o;
                mAdapter = new SuggestedItinaryListAdapter(getActivity(), journeyResponse.getJourneys());
                mListView.setAdapter(mAdapter);
            }

        }

        @Override
        public void failure(RetrofitError retrofitError) {

            System.out.println(retrofitError);
            retrofitError.printStackTrace();

        }
    };

    Callback detailsFromCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            from = ((GoogleAPIDetailsPlace) o).result.geometry;
            if (to != null){
                askForItinary();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {}
    };

    Callback detailsToCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            to = ((GoogleAPIDetailsPlace) o).result.geometry;
            if (from != null){
                askForItinary();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {}
    };

    public interface OnFragmentInteractionListener {
        public void onSuggestedItinariesFragmentInteraction(Uri uri);
    }

}
