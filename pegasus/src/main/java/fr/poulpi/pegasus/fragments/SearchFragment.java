package fr.poulpi.pegasus.fragments;



import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.cards.IsOfflineSearchCard;
import fr.poulpi.pegasus.cards.ItinarySearchCard;
import fr.poulpi.pegasus.cards.ValidateSearchCard;
import it.gmariotti.cardslib.library.view.CardView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SearchFragment extends Fragment {

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
    }

    private void initCards() {

        //Create a Card
        ItinarySearchCard card= new ItinarySearchCard(getActivity());
        CardView cardView = (CardView) getActivity().findViewById(R.id.itinary_search_card);
        cardView.setCard(card);

        IsOfflineSearchCard card2 = new IsOfflineSearchCard(getActivity());
        cardView = (CardView) getActivity().findViewById(R.id.is_offline_search_card);
        cardView.setCard(card2);

        ValidateSearchCard card3 = new ValidateSearchCard(getActivity());
        card3.setBackgroundResourceId(R.drawable.validate_card_selector);
        cardView = (CardView) getActivity().findViewById(R.id.validate_search_card);
        cardView.setCard(card3);

    }

}
