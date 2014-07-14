package fr.poulpi.pegasus.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.poulpi.pegasus.R;

/**
 * Created by pokito on 14/07/2014.
 */
public class ItinaryWalkSection extends RelativeLayout {

    public static final String WALK_TIME = "walk_time";

    private Bundle params;
    private TextView mWalkTimeTw;

    public ItinaryWalkSection(Context context) {
        super(context);
        init();
    }

    public ItinaryWalkSection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItinaryWalkSection(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ItinaryWalkSection newInstance(Context context, Bundle params) {
        ItinaryWalkSection section = new ItinaryWalkSection(context);
        section.setParams(params);
        return section;
    }

    public void setParams(Bundle params) {
        this.params = params;
    }

    public Bundle getParams() {
        return params;
    }

    private void init() {
        inflate(getContext(), R.layout.itinary_walk_section, this);

        mWalkTimeTw = (TextView) findViewById(R.id.walk_time);

        if(getParams() != null){
            if(getParams().containsKey(WALK_TIME)) mWalkTimeTw.setText(getParams().getString(WALK_TIME));
        }
    }
}