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
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by paul-henri on 3/21/14.
 */
public class PredictionsListCard extends Card implements PredictionsCardInterface {

    private PredictionListLayout listLayout;
    private PredictionListAdapter mAdapter = null;

    public PredictionsListCard(Context context) {
        super(context, R.layout.predictions_list_card_inner_content);
        init();
    }

    private void init() {

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

    }

    @Override
    public void refreshCard(ApiPredictionsResponse response) {

        ArrayList<ResultApiPrediction> list = new ArrayList<ResultApiPrediction>(response.getPredictions());

        listLayout = (PredictionListLayout) getCardView().findViewById(R.id.predictions_card_inner_list);

        mAdapter = new PredictionListAdapter(getContext(), list);

        listLayout.setAdapter(mAdapter);

    }
}
