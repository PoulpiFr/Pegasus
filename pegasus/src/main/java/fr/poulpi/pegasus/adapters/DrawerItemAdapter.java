package fr.poulpi.pegasus.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.poulpi.pegasus.R;

/**
 * Created by pokito on 14/03/14.
 */
public class DrawerItemAdapter extends ArrayAdapter<String> {

    public DrawerItemAdapter(Context context, ArrayList<String> drawerCats) {
        super(context, R.layout.drawer_list_item, drawerCats);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String name = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_list_item, null);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        // Populate the data into the template view using the data object
        tvName.setText(name);

        // Return the completed view to render on screen
        return convertView;
    }
}