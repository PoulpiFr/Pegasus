package fr.poulpi.pegasus.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.adapters.PredictionListAdapter;
import fr.poulpi.pegasus.interfaces.PredictionsCardInterface;
import fr.poulpi.pegasus.layout.PredictionListLayout;
import fr.poulpi.pegasus.model.ApiPredictionsResponse;
import fr.poulpi.pegasus.model.OTPItinerary;
import fr.poulpi.pegasus.model.ResultApiPrediction;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by paul-henri on 3/25/14.
 */
public class ItinaryCard extends Card {

    private PredictionListLayout listLayout;
    private PredictionListAdapter mAdapter = null;
private OTPItinerary itinerary;

    public ItinaryCard(Context context, OTPItinerary itinerary) {
        super(context, R.layout.itinerary_card_inner_content);
        this.itinerary = itinerary;
        init();
    }

    private void init() {

        CardHeader header = new CardHeader(getContext());
        header.setTitle("Le plus long");
        addCardHeader(header);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        String startTime;
        String endTime;

        Date startDate = new Date((long)itinerary.startTime);
        Date endDate = new Date((long)itinerary.endTime);

        DateFormat df = new SimpleDateFormat("HH:mm");

        startTime = df.format(startDate);
        endTime = df.format(endDate);

        ((TextView) view.findViewById(R.id.depart_time)).setText(startTime);
        ((TextView)view.findViewById(R.id.arrival_time)).setText(endTime);

    }
}
