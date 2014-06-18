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

                try {
                    if (label.equals("1")) {
                        currentSign.setMetroLine(RATPLineSignView.M1);
                    } else if (label.equals("2")) {
                        currentSign.setMetroLine(RATPLineSignView.M2);
                    } else if (label.equals("3")) {
                        currentSign.setMetroLine(RATPLineSignView.M3);
                    } else if (label.equals("4")) {
                        currentSign.setMetroLine(RATPLineSignView.M4);
                    } else if (label.equals("5")) {
                        currentSign.setMetroLine(RATPLineSignView.M5);
                    } else if (label.equals("6")) {
                        currentSign.setMetroLine(RATPLineSignView.M6);
                    } else if (label.equals("7")) {
                        currentSign.setMetroLine(RATPLineSignView.M7);
                    } else if (label.equals("8")) {
                        currentSign.setMetroLine(RATPLineSignView.M8);
                    } else if (label.equals("9")) {
                        currentSign.setMetroLine(RATPLineSignView.M9);
                    } else if (label.equals("10")) {
                        currentSign.setMetroLine(RATPLineSignView.M10);
                    } else if (label.equals("11")) {
                        currentSign.setMetroLine(RATPLineSignView.M11);
                    } else if (label.equals("12")) {
                        currentSign.setMetroLine(RATPLineSignView.M12);
                    } else if (label.equals("13")) {
                        currentSign.setMetroLine(RATPLineSignView.M13);
                    } else if (label.equals("14")) {
                        currentSign.setMetroLine(RATPLineSignView.M14);
                    } else if (label.equals("A")) {
                        currentSign.setMetroLine(RATPLineSignView.RA);
                    } else if (label.equals("B")) {
                        currentSign.setMetroLine(RATPLineSignView.RB);
                    } else if (label.equals("C")) {
                        currentSign.setMetroLine(RATPLineSignView.RC);
                    } else if (label.equals("D")) {
                        currentSign.setMetroLine(RATPLineSignView.RD);
                    } else if (label.equals("E")) {
                        currentSign.setMetroLine(RATPLineSignView.RE);
                    } else if (label.equals("J")) {
                        currentSign.setMetroLine(RATPLineSignView.RJ);
                    } else if (label.equals("K")) {
                        currentSign.setMetroLine(RATPLineSignView.RK);
                    } else if (label.equals("L")) {
                        currentSign.setMetroLine(RATPLineSignView.RL);
                    } else if (label.equals("N")) {
                        currentSign.setMetroLine(RATPLineSignView.RN);
                    } else if (label.equals("P")) {
                        currentSign.setMetroLine(RATPLineSignView.RP);
                    } else if (label.equals("R")) {
                        currentSign.setMetroLine(RATPLineSignView.RR);
                    } else if (label.equals("U")) {
                        currentSign.setMetroLine(RATPLineSignView.RU);
                    } else if (label.startsWith("N")){
                        currentSign.setBusLine(label);
                    } else if (Integer.valueOf(label) >= 20) {
                        currentSign.setBusLine(label);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        journeyHolder.walkingDuration.setText(walkingDuration/60 + " min");


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
