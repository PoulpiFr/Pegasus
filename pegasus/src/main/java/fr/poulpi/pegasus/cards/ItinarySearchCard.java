package fr.poulpi.pegasus.cards;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import fr.poulpi.pegasus.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by pokito on 15/03/14.
 */

public class ItinarySearchCard extends Card {

    private TextView tvFrom;
    private TextView tvTo;
    private ImageButton btnFromClear;
    private ImageButton btnToClear;
    private ImageButton btnSwitch;

    private TextWatcher tvFromWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0) btnFromClear.setVisibility(View.VISIBLE);
            else btnFromClear.setVisibility(View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher tvToWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0) btnToClear.setVisibility(View.VISIBLE);
            else btnToClear.setVisibility(View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public ItinarySearchCard(Context context) {
        super(context, R.layout.itinary_search_card_inner_content);
        init();
    }

    private void init() {
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        /* Find the views */
        tvFrom = (TextView)view.findViewById(R.id.from);
        tvTo = (TextView)view.findViewById(R.id.to);
        btnFromClear = (ImageButton)view.findViewById(R.id.from_clear);
        btnToClear = (ImageButton)view.findViewById(R.id.to_clear);
        btnSwitch = (ImageButton)view.findViewById(R.id.switch_from_to);

        /* Set visibility to clear buttons */
        btnFromClear.setVisibility(View.VISIBLE);
        btnToClear.setVisibility(View.INVISIBLE);

        /* Set textChangedListener (toggle the clear button */
        tvFrom.addTextChangedListener(tvFromWatcher);
        tvTo.addTextChangedListener(tvToWatcher);

        /* Set btns onClickListeners */
        btnFromClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tvFrom.getHint().toString().equals(getContext().getString(R.string.from_gps))){
                    tvFrom.setHint(R.string.from);
                }

                tvFrom.setText("");
            }
        });

        btnToClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTo.setText("");
            }
        });

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Switching the text value & hint if we have to */
                CharSequence txtFrom = tvFrom.getText();
                CharSequence txtTo = tvTo.getText();

                tvFrom.setText(txtTo);
                tvTo.setText(txtFrom);

                if(txtTo.length() == 0 && tvTo.getHint().equals(getContext().getString(R.string.to_gps))) {
                    tvFrom.setHint(R.string.from_gps);
                    tvTo.setHint(R.string.to);
                    btnFromClear.setVisibility(View.VISIBLE);
                }
                else if(txtFrom.length() == 0 && tvFrom.getHint().equals(getContext().getString(R.string.from_gps))) {
                    tvTo.setHint(R.string.to_gps);
                    tvFrom.setHint(R.string.from);
                    btnToClear.setVisibility(View.VISIBLE);
                }

            }
        });
    }

}
