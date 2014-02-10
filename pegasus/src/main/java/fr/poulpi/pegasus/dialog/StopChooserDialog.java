package fr.poulpi.pegasus.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.fragments.OfflineFragment;
import fr.poulpi.pegasus.interfaces.StopSelectionInterface;
import fr.poulpi.pegasus.model.Graph;
import fr.poulpi.pegasus.model.Stop;

/**
 * Created by pokito on 10/02/14.
 */
public class StopChooserDialog extends DialogFragment {

    static public int DEPARTURE = 0;
    static public int DESTINATION = 1;

    private Graph mGraph;
    private int mode;

    public StopChooserDialog(Graph graph, int mode){
        this.mGraph = graph;
        this.mode = mode;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(!(getTargetFragment() instanceof StopSelectionInterface)){
            throw new ClassCastException("Target fragment don't implement StopSelectionInterface !");
        }

        final ArrayList<String> stopsName = new ArrayList<String>();

        for(Stop stop : mGraph.data){
            stopsName.add(stop.name);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Station")
                .setItems(stopsName.toArray(new String[stopsName.size()]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        StopSelectionInterface fragment = (StopSelectionInterface) getTargetFragment();

                        if (fragment != null) {
                            if(mode == DEPARTURE) fragment.setDeparture(mGraph.data.get(which));
                            if(mode == DESTINATION) fragment.setDestination(mGraph.data.get(which));
                        }
                    }
                });
        return builder.create();
    }
}
