package fr.poulpi.pegasus.fragments;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.cards.IsOfflineSearchCard;
import fr.poulpi.pegasus.cards.ItinarySearchCard;
import fr.poulpi.pegasus.cards.TimeSearchCard;
import fr.poulpi.pegasus.cards.PredictionsListCard;
import fr.poulpi.pegasus.cards.ValidateSearchCard;
import fr.poulpi.pegasus.constants.GoogleAPIConf;
import fr.poulpi.pegasus.interfaces.GooglePlaceAPIInterface;
import fr.poulpi.pegasus.interfaces.ItinarySearchCardInterface;
import fr.poulpi.pegasus.interfaces.OTPFragmentInterface;
import fr.poulpi.pegasus.interfaces.PredictionsCardInterface;
import fr.poulpi.pegasus.interfaces.PredictionsInterface;
import fr.poulpi.pegasus.interfaces.TimeInterface;
import fr.poulpi.pegasus.model.ApiPredictionsResponse;
import fr.poulpi.pegasus.model.ResultApiPrediction;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SearchFragment extends Fragment implements PredictionsInterface, TimeInterface, OTPFragmentInterface {

    RestAdapter restAdapter;

    private ItinarySearchCard itinarySearchCard;
    private IsOfflineSearchCard isOfflineSearchCard;
    private PredictionsListCard predictionsListCard;
    private ValidateSearchCard validateSearchCard;
    private TimeSearchCard timeSearchCard;

    public static final String TAG = "SearchFragment";

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCards();

        String API_URL = "https://maps.googleapis.com/maps/api/place";

        // debug purpose only, to get the messages
        RestAdapter.Log log = new RestAdapter.Log(){
            public void log(String msg){
                System.out.println(msg);
            }
        };

        // Create a very simple REST adapter which points the GitHub API endpoint.
        restAdapter = new RestAdapter.Builder()
                .setLog(log)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

    }

    private void initCards() {

        //Create a Card
        itinarySearchCard = new ItinarySearchCard(getActivity());
        CardView cardView = (CardView) getActivity().findViewById(R.id.itinary_search_card);
        cardView.setCard(itinarySearchCard);

        isOfflineSearchCard = new IsOfflineSearchCard(getActivity());
        cardView = (CardView) getActivity().findViewById(R.id.is_offline_search_card);
        cardView.setCard(isOfflineSearchCard);

        predictionsListCard = new PredictionsListCard(getActivity());
        cardView = (CardView) getActivity().findViewById(R.id.search_autocomplete_card);
        cardView.setCard(predictionsListCard);

        validateSearchCard = new ValidateSearchCard(getActivity());
        validateSearchCard.setBackgroundResourceId(R.drawable.validate_card_selector);
        validateSearchCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ItinariesFragment fragment = ItinariesFragment.newInstance(itinarySearchCard.getFrom(), itinarySearchCard.getTo(), timeSearchCard.getTime());

                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();
            }
        });
        cardView = (CardView) getActivity().findViewById(R.id.validate_search_card);
        cardView.setCard(validateSearchCard);

        timeSearchCard = new TimeSearchCard((getActivity()));
        cardView = (CardView) getActivity().findViewById(R.id.time_search_card);
        cardView.setCard(timeSearchCard);

    }



    Callback predictionsCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            if(predictionsListCard instanceof PredictionsCardInterface){

                predictionsListCard.getCardView().setVisibility(View.VISIBLE);
                isOfflineSearchCard.getCardView().setVisibility(View.GONE);
                validateSearchCard.getCardView().setVisibility(View.GONE);
                timeSearchCard.getCardView().setVisibility(View.GONE);

                ((PredictionsCardInterface) predictionsListCard).refreshCard((ApiPredictionsResponse) o);
            }

        }

        @Override
        public void failure(RetrofitError retrofitError) {

        }
    };

    /* === PredictionsInterface === */

    public void googleAPIRequestPredictions(String str){

        // Create an instance of our API interface.
        GooglePlaceAPIInterface tmp = restAdapter.create(GooglePlaceAPIInterface.class);

        tmp.response("true", GoogleAPIConf.API_KEY, "country:fr", str, predictionsCallback);

    }

    @Override
    public void googleAPISelectFromPrediction(ResultApiPrediction result) {

        predictionsListCard.getCardView().setVisibility(View.GONE);
        isOfflineSearchCard.getCardView().setVisibility(View.VISIBLE);
        validateSearchCard.getCardView().setVisibility(View.VISIBLE);
        timeSearchCard.getCardView().setVisibility(View.VISIBLE);

        if(itinarySearchCard instanceof ItinarySearchCardInterface) {
            ((ItinarySearchCardInterface) itinarySearchCard).refreshCard(result, null);
        }

    }

    @Override
    public void googleAPISelectToPrediction(ResultApiPrediction result) {

        predictionsListCard.getCardView().setVisibility(View.GONE);
        isOfflineSearchCard.getCardView().setVisibility(View.VISIBLE);
        validateSearchCard.getCardView().setVisibility(View.VISIBLE);
        timeSearchCard.getCardView().setVisibility(View.VISIBLE);

        if(itinarySearchCard instanceof ItinarySearchCardInterface) {

            ((ItinarySearchCardInterface) itinarySearchCard).refreshCard(null,result);
        }

    }

    @Override
    public int getFromToState() {

        int result = ItinarySearchCard.UNKNOWN;

        if(itinarySearchCard instanceof ItinarySearchCardInterface){
            result = ((ItinarySearchCardInterface) itinarySearchCard).getFromToState();
        }

        return result;

    }

    @Override
    public void setTime(int hourOfDay, int minute) {

        timeSearchCard.setTime(hourOfDay, minute);

    }
    @Override
    public ResultApiPrediction getFrom() {
        return itinarySearchCard.getFrom();
    }

    @Override
    public ResultApiPrediction getTo() {

        return itinarySearchCard.getTo();

    }
}
