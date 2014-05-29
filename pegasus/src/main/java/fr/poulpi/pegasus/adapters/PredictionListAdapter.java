package fr.poulpi.pegasus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;

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

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.prediction_item_list, null);
        }

        TextView textView1 = (TextView) view.findViewById(R.id.prediction_item_name);
        textView1.setText(item.getDescription());

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

}
