package fr.poulpi.pegasus.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Date;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.cards.ItinaryCard;
import fr.poulpi.pegasus.constants.GoogleAPIConf;
import fr.poulpi.pegasus.interfaces.GooglePlaceAPIInterface;
import fr.poulpi.pegasus.interfaces.NavitiaIoInterface;
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
 * Created by paul-henri on 3/25/14.
 */
public class ItinariesFragment extends Fragment {

    RestAdapter navitiaRestAdapter;
    RestAdapter googleRestAdapter;
    ItinaryCard itinaryCard;

    GoogleAPIGeometry from = null;
    GoogleAPIGeometry to = null;

    public static final String TAG = "ItinariesFragment";
    private ProgressDialog pd;

    public static ItinariesFragment newInstance(GoogleAPIResultPrediction from, GoogleAPIResultPrediction to, String date) {
        ItinariesFragment fragment = new ItinariesFragment();
        Bundle args = new Bundle();
        args.putString("from_ref", from.getReference());
        args.putString("to_ref", to.getReference());
        args.putString("date", date);
        fragment.setArguments(args);
        return fragment;
    }

    public ItinariesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Chargement en cours ...");
        pd.setMessage("Veuillez attendre");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_itineraries_list, container, false);
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
                getArguments().getString("from_ref"),
                "fr",
                detailsFromCallback);
        tmp.details("true",
                GoogleAPIConf.API_KEY,
                getArguments().getString("to_ref"),
                "fr",
                detailsToCallback);

    }

    private void initCards(CTPJourneyResponse journeyResponse) {

        Log.d(TAG, "" + journeyResponse.getJourneys().size());
        pd.dismiss();

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
                initCards(journeyResponse);
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

}
