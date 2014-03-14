package fr.poulpi.pegasus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.model.OfflineStop;

/**
 * Created by pokito on 09/02/14.
 */
public class StopsAdapter extends ArrayAdapter<OfflineStop> {

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView zip;
    }

    public StopsAdapter(Context context, ArrayList<OfflineStop> offlineStops) {
        super(context, R.layout.item_stop, offlineStops);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        OfflineStop offlineStop = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_stop, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.zip = (TextView) convertView.findViewById(R.id.tvZip);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(offlineStop.name);
        viewHolder.zip.setText(Integer.toString(offlineStop.zip));
        // Return the completed view to render on screen
        return convertView;
    }
}
