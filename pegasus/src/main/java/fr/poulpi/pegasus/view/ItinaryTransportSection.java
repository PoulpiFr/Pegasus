package fr.poulpi.pegasus.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.poulpi.pegasus.R;

public class ItinaryTransportSection extends RelativeLayout {


    private Bundle params;

    public static final String METRO_LINE = "metro_line";

    public static final String DEPARTURE_TIME = "departure_time";
    public static final String ARRIVAL_TIME = "arrival_time";

    public static final String DEPARTURE_PLACE = "departure_place";
    public static final String ARRIVAL_PLACE = "arrival_place";

    public static final String DIRECTION = "direction";
    public static final String TRANSPORT_TIME = "transport_time";

    private TextView mDepartureTimeTw;
    private TextView mArrivalTimeTw;
    private TextView mDeparturePlaceTw;
    private TextView mArrivalPlaceTw;
    private TextView mDirectionTw;
    private TextView mTransportTimeTw;

    public ItinaryTransportSection(Context context) {
        super(context);
    }

    public ItinaryTransportSection(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItinaryTransportSection(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static ItinaryTransportSection newInstance(Context context, Bundle params) {
        ItinaryTransportSection section = new ItinaryTransportSection(context);
        section.setParams(params);
        if(params != null) Log.d("Poulpi", "newInstance params is NOT null");
        return section;
    }

    public void setParams(Bundle params) {
        this.params = params;
        init();
    }

    public Bundle getParams() {
        return params;
    }

    private void init() {
        inflate(getContext(), R.layout.itinary_transport_section, this);

        mDepartureTimeTw = (TextView) findViewById(R.id.departure_time);
        mArrivalTimeTw = (TextView) findViewById(R.id.arrival_time);

        mDeparturePlaceTw = (TextView) findViewById(R.id.departure_place);
        mArrivalPlaceTw = (TextView) findViewById(R.id.arrival_place);

        mDirectionTw = (TextView) findViewById(R.id.direction);
        mTransportTimeTw = (TextView) findViewById(R.id.transport_time);

        if(getParams() != null){

            if(getParams().containsKey(DEPARTURE_TIME)) mDepartureTimeTw.setText(getParams().getString(DEPARTURE_TIME));
            if(getParams().containsKey(ARRIVAL_TIME)) mArrivalTimeTw.setText(getParams().getString(ARRIVAL_TIME));

            if(getParams().containsKey(DEPARTURE_PLACE)) mDeparturePlaceTw.setText(getParams().getString(DEPARTURE_PLACE));
            if(getParams().containsKey(ARRIVAL_PLACE)) mArrivalPlaceTw.setText(getParams().getString(ARRIVAL_PLACE));

            if(getParams().containsKey(DIRECTION)) mDirectionTw.setText(getParams().getString(DIRECTION));
            if(getParams().containsKey(TRANSPORT_TIME)) mTransportTimeTw.setText(getParams().getString(TRANSPORT_TIME));
        }
    }
}
