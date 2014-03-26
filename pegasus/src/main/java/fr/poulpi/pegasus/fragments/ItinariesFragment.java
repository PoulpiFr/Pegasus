package fr.poulpi.pegasus.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.cards.ItinaryCard;
import fr.poulpi.pegasus.constants.GoogleAPIConf;
import fr.poulpi.pegasus.interfaces.GooglePlaceAPIInterface;
import fr.poulpi.pegasus.interfaces.OTPOpenTripPlanner;
import fr.poulpi.pegasus.model.GoogleAPIDetailsPlace;
import fr.poulpi.pegasus.model.GoogleAPIGeometry;
import fr.poulpi.pegasus.model.OTPItinerary;
import fr.poulpi.pegasus.model.OTPResponse;
import fr.poulpi.pegasus.model.ResultApiPrediction;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by paul-henri on 3/25/14.
 */
public class ItinariesFragment extends Fragment {

    RestAdapter otpRestAdapter;
    RestAdapter googleRestAdapter;
    ItinaryCard itinaryCard;

    GoogleAPIGeometry from = null;
    GoogleAPIGeometry to = null;

    public static final String TAG = "ItinariesFragment";
    private ProgressDialog pd;

    public static ItinariesFragment newInstance(ResultApiPrediction from, ResultApiPrediction to, String date) {
        ItinariesFragment fragment = new ItinariesFragment();
        Bundle args = new Bundle();
        args.putString("from_ref", from.reference);
        args.putString("to_ref", to.reference);
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

        String API_URL = "http://under-apps.com:8080/otp-rest-servlet/ws/routers/default";

        // debug purpose only, to get the messages
        RestAdapter.Log log = new RestAdapter.Log(){
            public void log(String msg){
                System.out.println(msg);
            }
        };

        // Create a very simple REST adapter which points the GitHub API endpoint.
        otpRestAdapter = new RestAdapter.Builder()
                .setLog(log)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

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

    private void initCards(OTPResponse otpResponse) {

        ArrayList<Card> cards = new ArrayList<Card>();

        Iterator<OTPItinerary> it = otpResponse.plan.itineraries.iterator();

        while (it.hasNext()){
            ItinaryCard card = new ItinaryCard(getActivity(), it.next());
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        CardListView listView = (CardListView) getView().findViewById(R.id.itinaries_list);

        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }

        pd.dismiss();

    }

    private void askForOTPItinary(){

        String fromStr = new StringBuilder().append(from.location.lat).append(",").append(from.location.lng).toString();
        String toStr = new StringBuilder().append(to.location.lat).append(",").append(to.location.lng).toString();

        OTPOpenTripPlanner ws = otpRestAdapter.create(OTPOpenTripPlanner.class);
        ws.response(fromStr, toStr, getArguments().getString("date"), itinaryCallback);

    }


    Callback itinaryCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            OTPResponse otpResponse = (OTPResponse) o;

            initCards(otpResponse);

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
                askForOTPItinary();
            }

        }

        @Override
        public void failure(RetrofitError retrofitError) {

        }
    };

    Callback detailsToCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            to = ((GoogleAPIDetailsPlace) o).result.geometry;

            if (from != null){
                askForOTPItinary();
            }

        }

        @Override
        public void failure(RetrofitError retrofitError) {

        }
    };

}
