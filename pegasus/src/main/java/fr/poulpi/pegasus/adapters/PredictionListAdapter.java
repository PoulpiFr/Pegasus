package fr.poulpi.pegasus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.cards.ItinarySearchCard;
import fr.poulpi.pegasus.interfaces.PredictionsActivityInterface;
import fr.poulpi.pegasus.model.ResultApiPrediction;

/**
 * Created by paul-henri on 3/20/14.
 */
public class PredictionListAdapter extends ArrayAdapter<ResultApiPrediction> {

    private List<ResultApiPrediction> counts;

    public PredictionListAdapter(Context context, List<ResultApiPrediction> objects) {
        super(context, 0, objects);
        this.counts = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ResultApiPrediction item = getItem(position);

        //Without ViewHolder for demo purpose
        View view = convertView;

        if (view == null) {
            LayoutInflater li =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.prediction_card_item_list, parent, false);
        }

        TextView textView1 = (TextView) view.findViewById(R.id.prediction_card_item_name);
        textView1.setText(item.getDescription());

        view.setTag(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) v.getTag();

                if(getContext() instanceof PredictionsActivityInterface) {

                    int tmp = ((PredictionsActivityInterface) getContext()).getFromToState();
                    int tmp2 =  ItinarySearchCard.FROM;

                    if ( ((PredictionsActivityInterface) getContext()).getFromToState() == ItinarySearchCard.FROM ) {
                        ((PredictionsActivityInterface) getContext()).googleAPISelectFromPrediction(counts.get(pos));
                    } else if (((PredictionsActivityInterface) getContext()).getFromToState() == ItinarySearchCard.TO ){
                        ((PredictionsActivityInterface) getContext()).googleAPISelectToPrediction(counts.get(pos));
                    }

                }

            }
        });

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
