package fr.poulpi.pegasus.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.ratp.LineStyle;

public class ItinaryTransportSection extends RelativeLayout {

    private Bundle params;

    public static final String LINE_TYPE = "line_type";
    public static final String LINE = "line";

    public static final String DEPARTURE_TIME = "departure_time";
    public static final String ARRIVAL_TIME = "arrival_time";

    public static final String DEPARTURE_PLACE = "departure_place";
    public static final String ARRIVAL_PLACE = "arrival_place";

    public static final String DIRECTION = "direction";
    public static final String TRANSPORT_TIME = "transport_time";

    public static final String IS_CORRESPONDANCE = "is_correspondance";

    private TextView mDepartureTimeTw;
    private TextView mArrivalTimeTw;
    private TextView mDeparturePlaceTw;
    private TextView mArrivalPlaceTw;
    private TextView mDirectionTw;
    private TextView mTransportTimeTw;
    private RATPLineSignView mSignMode;
    private RATPLineSignView mSignNumber;
    private RATPLineView mLineView;
    private View mGrayArrival;

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

        mSignMode = (RATPLineSignView) findViewById(R.id.ratp_sign_mode);
        mSignNumber = (RATPLineSignView) findViewById(R.id.ratp_sign_number);
        mLineView = (RATPLineView) findViewById(R.id.transport_line_view);

        mDepartureTimeTw = (TextView) findViewById(R.id.departure_time);
        mArrivalTimeTw = (TextView) findViewById(R.id.arrival_time);

        mDeparturePlaceTw = (TextView) findViewById(R.id.departure_place);
        mArrivalPlaceTw = (TextView) findViewById(R.id.arrival_place);

        mDirectionTw = (TextView) findViewById(R.id.direction);
        mTransportTimeTw = (TextView) findViewById(R.id.transport_time);

        mGrayArrival = (View) findViewById(R.id.arrival_gray);

        if(getParams() != null){

            if(getParams().containsKey(LINE_TYPE)) {
                mSignMode.setMetroLine(getParams().getInt(LINE_TYPE));
            }

            if(getParams().containsKey(LINE)){
                mSignNumber.setLine(getParams().getString(LINE));
                mLineView.setLine(getParams().getString(LINE));
            }

            if(getParams().containsKey(DEPARTURE_TIME)) mDepartureTimeTw.setText(getParams().getString(DEPARTURE_TIME));
            if(getParams().containsKey(ARRIVAL_TIME)) mArrivalTimeTw.setText(getParams().getString(ARRIVAL_TIME));

            if(getParams().containsKey(DEPARTURE_PLACE)) mDeparturePlaceTw.setText(getParams().getString(DEPARTURE_PLACE));
            if(getParams().containsKey(ARRIVAL_PLACE)) mArrivalPlaceTw.setText(getParams().getString(ARRIVAL_PLACE));

            if(getParams().containsKey(DIRECTION)) mDirectionTw.setText(getParams().getString(DIRECTION));
            if(getParams().containsKey(TRANSPORT_TIME)) mTransportTimeTw.setText(getParams().getString(TRANSPORT_TIME));

            if(getParams().containsKey(IS_CORRESPONDANCE) && getParams().getBoolean(IS_CORRESPONDANCE)) mGrayArrival.setVisibility(VISIBLE);
        }
    }
}
