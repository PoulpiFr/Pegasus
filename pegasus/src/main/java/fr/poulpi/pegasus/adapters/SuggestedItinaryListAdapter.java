package fr.poulpi.pegasus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.model.CTPJourney;
import fr.poulpi.pegasus.model.CTPSection;
import fr.poulpi.pegasus.ratp.LineStyle;
import fr.poulpi.pegasus.view.RATPLineSignView;

/**
 * Created by pokito on 30/05/2014.
 */
public class SuggestedItinaryListAdapter extends ArrayAdapter<CTPJourney> {

    private List<CTPJourney> counts;

    public SuggestedItinaryListAdapter(Context context, List<CTPJourney> objects) {
        super(context, 0, objects);
        this.counts = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CTPJourney item = getItem(position);

        //Without ViewHolder for demo purpose
        View view = convertView;
        JourneyHolder journeyHolder = null;
        if (view == null) {
            journeyHolder = new JourneyHolder();
            view = View.inflate(getContext(), R.layout.suggested_itinaries_item, null);

            journeyHolder.typeOfJourney = (TextView) view.findViewById(R.id.suggested_itinary_description);
            journeyHolder.totalDuration = (TextView) view.findViewById(R.id.suggested_itinary_whole_time);
            journeyHolder.walkingDuration = (TextView) view.findViewById(R.id.suggested_itinary_walking_time);
            journeyHolder.timeOfDeparture = (TextView) view.findViewById(R.id.suggested_itinary_departure_time);
            journeyHolder.timeOfArrival = (TextView) view.findViewById(R.id.suggested_itinary_arrival_time);

            journeyHolder.sign1 = (RATPLineSignView) view.findViewById(R.id.suggested_itinary_ratp_sign_1);
            journeyHolder.sign2 = (RATPLineSignView) view.findViewById(R.id.suggested_itinary_ratp_sign_2);
            journeyHolder.sign3 = (RATPLineSignView) view.findViewById(R.id.suggested_itinary_ratp_sign_3);
            journeyHolder.sign4 = (RATPLineSignView) view.findViewById(R.id.suggested_itinary_ratp_sign_4);
            journeyHolder.sign5 = (RATPLineSignView) view.findViewById(R.id.suggested_itinary_ratp_sign_5);
            journeyHolder.sign6 = (RATPLineSignView) view.findViewById(R.id.suggested_itinary_ratp_sign_6);

            view.setTag(journeyHolder);
        } else {
            journeyHolder = (JourneyHolder) view.getTag();
        }

        /*------ Journey description ------*/
        if(item.getType().equals(getContext().getString(R.string.navitia_api_rapid))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.rapid));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_comfort))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.comfort));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_healthy))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.healthy));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_best))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.best));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_fastest))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.fastest));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_no_train))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.no_train));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_less_fallback_walk))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.less_fallback_walk));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_less_fallback_bike))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.less_fallback_bike));
        } else if(item.getType().equals(getContext().getString(R.string.navitia_api_walking))){
            journeyHolder.typeOfJourney.setText(getContext().getString(R.string.walking));
        }

        /*------ Journey total duration ------*/
        journeyHolder.totalDuration.setText(item.getDuration()/60 + " min");

        /*------ Journey departure and arrival time ------*/
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        journeyHolder.timeOfDeparture.setText(dateFormat.format(item.getDeparture_date_time()));
        journeyHolder.timeOfArrival.setText(dateFormat.format(item.getArrival_date_time()));

        int walkingDuration = 0;
        int nbOfSigns = 0;
        Iterator<CTPSection> it = item.getSections().iterator();
        CTPSection tmp;
        RATPLineSignView currentSign = null;
        while (it.hasNext()){
            tmp = it.next();
            if(tmp.getMode() != null && tmp.getMode().equals(getContext().getString(R.string.navitia_api_walking))){
                walkingDuration += tmp.getDuration();
            }
            if(tmp.getType().equals(getContext().getString(R.string.navitia_api_public_transport))){

                if(nbOfSigns == 0){
                    nbOfSigns++;
                    currentSign = journeyHolder.sign1;
                } else if(nbOfSigns == 1){
                    nbOfSigns++;
                    currentSign = journeyHolder.sign2;
                } else if(nbOfSigns == 2){
                    nbOfSigns++;
                    currentSign = journeyHolder.sign3;
                } else if(nbOfSigns == 3){
                    nbOfSigns++;
                    currentSign = journeyHolder.sign4;
                } else if(nbOfSigns == 4){
                    nbOfSigns++;
                    currentSign = journeyHolder.sign5;
                } else if(nbOfSigns == 5) {
                    nbOfSigns++;
                    currentSign = journeyHolder.sign6;
                }

                currentSign.setVisibility(View.VISIBLE);

                String label = tmp.getDisplay_informations().getLabel();

                currentSign.setLine(label);
            }
        }

        journeyHolder.walkingDuration.setText(walkingDuration / 60 + getContext().getString(R.string.short_minute));

        return view;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount(){
        return counts.size();
    }

    static class JourneyHolder {
        public TextView typeOfJourney;
        public TextView totalDuration;
        public TextView walkingDuration;
        public TextView timeOfDeparture;
        public TextView timeOfArrival;

        public RATPLineSignView sign1;
        public RATPLineSignView sign2;
        public RATPLineSignView sign3;
        public RATPLineSignView sign4;
        public RATPLineSignView sign5;
        public RATPLineSignView sign6;
    }

}
