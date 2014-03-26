package fr.poulpi.pegasus.cards;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.interfaces.ItinarySearchCardInterface;
import fr.poulpi.pegasus.interfaces.PredictionsInterface;
import fr.poulpi.pegasus.model.ResultApiPrediction;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by pokito on 15/03/14.
 */

public class ItinarySearchCard extends Card implements ItinarySearchCardInterface {

    private EditText edFrom;
    private EditText edTo;
    private ImageButton btnFromClear;
    private ImageButton btnToClear;
    private ImageButton btnSwitch;

    private ResultApiPrediction from;
    private ResultApiPrediction to;

    public static final int TO = 0;
    public static final int FROM = 1;
    public static final int UNKNOWN = 2;

    private TextWatcher tvFromWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0) btnFromClear.setVisibility(View.VISIBLE);
            else btnFromClear.setVisibility(View.INVISIBLE);

            if(s.length() % 3 == 0 ){
                if(getContext() instanceof PredictionsInterface){
                    ((PredictionsInterface)getContext()).googleAPIRequestPredictions(s.toString());
                }
            }
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

            if( s.length() % 3 == 0 ){
                if(getContext() instanceof PredictionsInterface){
                    ((PredictionsInterface)getContext()).googleAPIRequestPredictions(s.toString());
                }
            }
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
        edFrom = (EditText)view.findViewById(R.id.from);
        edTo = (EditText)view.findViewById(R.id.to);
        btnFromClear = (ImageButton)view.findViewById(R.id.from_clear);
        btnToClear = (ImageButton)view.findViewById(R.id.to_clear);
        btnSwitch = (ImageButton)view.findViewById(R.id.switch_from_to);

        /* Set visibility to clear buttons */
        btnFromClear.setVisibility(View.VISIBLE);
        btnToClear.setVisibility(View.INVISIBLE);

        /* Set textChangedListener (toggle the clear button */
        edFrom.addTextChangedListener(tvFromWatcher);
        edTo.addTextChangedListener(tvToWatcher);

        /* Set btns onClickListeners */
        btnFromClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edFrom.getHint().toString().equals(getContext().getString(R.string.from_gps))){
                    edFrom.setHint(R.string.from);
                }

                edFrom.setText("");
            }
        });

        btnToClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTo.setText("");
            }
        });

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edFrom.removeTextChangedListener(tvFromWatcher);
                edTo.removeTextChangedListener(tvToWatcher);

                /* Switching the text value & hint if we have to */
                CharSequence txtFrom = edFrom.getText();
                CharSequence txtTo = edTo.getText();

                edFrom.setText(txtTo);
                edTo.setText(txtFrom);

                if(txtTo.length() == 0 && edTo.getHint().equals(getContext().getString(R.string.to_gps))) {
                    edFrom.setHint(R.string.from_gps);
                    edTo.setHint(R.string.to);
                    btnFromClear.setVisibility(View.VISIBLE);
                }
                else if(txtFrom.length() == 0 && edFrom.getHint().equals(getContext().getString(R.string.from_gps))) {
                    edTo.setHint(R.string.to_gps);
                    edFrom.setHint(R.string.from);
                    btnToClear.setVisibility(View.VISIBLE);
                }

                edFrom.addTextChangedListener(tvFromWatcher);
                edTo.addTextChangedListener(tvToWatcher);

            }
        });
    }

    @Override
    public void refreshCard(ResultApiPrediction from, ResultApiPrediction to) {

        edFrom.removeTextChangedListener(tvFromWatcher);
        edTo.removeTextChangedListener(tvToWatcher);

        if( from != null){
            edFrom.setText(from.getDescription());
            this.from = from;
            edTo.requestFocus();
        }

        if ( to != null){
            edTo.setText(to.getDescription());
            this.to = to;
        }

        edFrom.addTextChangedListener(tvFromWatcher);
        edTo.addTextChangedListener(tvToWatcher);

    }

    @Override
    public int getFromToState() {

        if(edFrom.hasFocus()) return FROM;
        else if(edTo.hasFocus()) return TO;
        else return UNKNOWN;

    }

    public ResultApiPrediction getFrom() {
        return from;
    }

    public ResultApiPrediction getTo() {
        return to;
    }
}
