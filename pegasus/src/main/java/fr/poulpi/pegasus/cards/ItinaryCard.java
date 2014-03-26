package fr.poulpi.pegasus.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.adapters.PredictionListAdapter;
import fr.poulpi.pegasus.interfaces.PredictionsCardInterface;
import fr.poulpi.pegasus.layout.PredictionListLayout;
import fr.poulpi.pegasus.model.ApiPredictionsResponse;
import fr.poulpi.pegasus.model.ResultApiPrediction;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by paul-henri on 3/25/14.
 */
public class ItinaryCard extends Card {

    private PredictionListLayout listLayout;
    private PredictionListAdapter mAdapter = null;

    public ItinaryCard(Context context) {
        super(context, R.layout.itinerary_card_inner_content);
        init();
    }

    private void init() {

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

    }
}
