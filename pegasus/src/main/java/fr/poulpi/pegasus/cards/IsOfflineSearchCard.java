package fr.poulpi.pegasus.cards;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.poulpi.pegasus.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by pokito on 15/03/14.
 */

public class IsOfflineSearchCard extends Card {

    private TextView tvStatus;

    public IsOfflineSearchCard(Context context) {
        super(context, R.layout.is_offline_search_card_inner_content);
        init();
    }

    private void init() {
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        /* Find the views */
        tvStatus = (TextView)view.findViewById(R.id.offline_status);

        /* Set text color */
        tvStatus.setTextColor(Color.DKGRAY);

        /* Set card OnCLickListener */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvStatus.getText().equals(getContext().getString(R.string.offline_mode_disabled))){
                    tvStatus.setText(R.string.offline_mode_enabled);
                    tvStatus.setTextColor(Color.GREEN);
                } else {
                    tvStatus.setText(R.string.offline_mode_disabled);
                    tvStatus.setTextColor(Color.DKGRAY);
                }
            }
        });
    }

}
