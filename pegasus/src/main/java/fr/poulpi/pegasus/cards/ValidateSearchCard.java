package fr.poulpi.pegasus.cards;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.poulpi.pegasus.R;
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

        /* Set card OnCLickListener */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Do stuff
            }
        });
    }

}
