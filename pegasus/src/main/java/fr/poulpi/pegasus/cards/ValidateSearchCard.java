package fr.poulpi.pegasus.cards;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.fragments.ItinaryFragment;
import fr.poulpi.pegasus.interfaces.OTPActivityInterface;
import fr.poulpi.pegasus.model.ResultApiPrediction;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by pokito on 15/03/14.
 */

public class ValidateSearchCard extends Card {

    private TextView tvStatus;

    public ValidateSearchCard(Context context) {
        super(context, R.layout.validate_search_card_inner_content);
        init();
    }

    private void init() {
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

    }

}
