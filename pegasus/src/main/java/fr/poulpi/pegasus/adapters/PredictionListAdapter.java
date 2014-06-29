package fr.poulpi.pegasus.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;
import fr.poulpi.pegasus.ratp.LineStyle;
import fr.poulpi.pegasus.view.RATPLineSignView;

/**
 * Created by paul-henri on 3/20/14.
 */
public class PredictionListAdapter extends ArrayAdapter<GoogleAPIResultPrediction> {

    private List<GoogleAPIResultPrediction> counts;

    public PredictionListAdapter(Context context, List<GoogleAPIResultPrediction> objects) {
        super(context, 0, objects);
        this.counts = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GoogleAPIResultPrediction item = getItem(position);

        //Without ViewHolder for demo purpose
        View view = convertView;
        LineHolder lineHolder = null;
        if (view == null) {
            lineHolder = new LineHolder();
            view = View.inflate(getContext(), R.layout.prediction_item_list, null);
            lineHolder.destinationName = (TextView) view.findViewById(R.id.prediction_item_name);
            lineHolder.ratpSign = (RATPLineSignView) view.findViewById(R.id.sign);

            view.setTag(lineHolder);
        } else {
            lineHolder = (LineHolder) view.getTag();
        }

        boolean isSubwayStation = false;
        boolean isRERStation = false;
        Iterator<String> it = item.getTypes().iterator();
        String tmp;

        while (it.hasNext()){

            tmp = it.next();

            if(tmp.equals(getContext().getString(R.string.google_api_subway_station))){
                isSubwayStation = true;
            } else if(tmp.equals(getContext().getString(R.string.google_api_rer_station))){
                isRERStation = true;
            }
        }

        if (isSubwayStation) {
            lineHolder.ratpSign.setMetroLine(LineStyle.METRO);
        } else if (isRERStation){
            lineHolder.ratpSign.setMetroLine(LineStyle.RER);
        } else {
            lineHolder.ratpSign.setMetroLine(LineStyle.BLANK);
        }

        lineHolder.destinationName.setText(item.getDescription());

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

    static class LineHolder {
        public TextView destinationName;
        public RATPLineSignView ratpSign;
    }

}
