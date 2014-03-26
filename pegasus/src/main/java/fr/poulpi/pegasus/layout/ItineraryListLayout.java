package fr.poulpi.pegasus.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import fr.poulpi.pegasus.adapters.PredictionListAdapter;

/**
 * Created by paul-henri on 3/25/14.
 */
public class ItineraryListLayout extends LinearLayout {

    private PredictionListAdapter mList;
    private View view;

    public ItineraryListLayout(Context context) {
        super(context);
    }

    public ItineraryListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItineraryListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(PredictionListAdapter list) {
        this.removeAllViews();
        this.mList = list;
        setOrientation(VERTICAL);

        //Populate list
        if (mList != null) {
            for (int i = 0; i < mList.getCount(); i++) {
                view = mList.getView(i, null, null);
                this.addView(view);
            }
        }
    }

}
