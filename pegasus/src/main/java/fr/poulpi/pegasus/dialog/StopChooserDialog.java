package fr.poulpi.pegasus.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;

import fr.poulpi.pegasus.interfaces.StopSelectionInterface;
import fr.poulpi.pegasus.model.OfflineGraph;
import fr.poulpi.pegasus.model.OfflineStop;

/**
 * Created by pokito on 10/02/14.
 */
public class StopChooserDialog extends DialogFragment {

    static public int DEPARTURE = 0;
    static public int DESTINATION = 1;

    private OfflineGraph mOfflineGraph;
    private int mode;

    public StopChooserDialog(OfflineGraph offlineGraph, int mode){
        this.mOfflineGraph = offlineGraph;
        this.mode = mode;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(!(getTargetFragment() instanceof StopSelectionInterface)){
            throw new ClassCastException("Target fragment don't implement StopSelectionInterface !");
        }

        final ArrayList<String> stopsName = new ArrayList<String>();

        for(OfflineStop offlineStop : mOfflineGraph.data){
            stopsName.add(offlineStop.name);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Station")
                .setItems(stopsName.toArray(new String[stopsName.size()]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        StopSelectionInterface fragment = (StopSelectionInterface) getTargetFragment();

                        if (fragment != null) {
                            if(mode == DEPARTURE) fragment.setDeparture(mOfflineGraph.data.get(which));
                            if(mode == DESTINATION) fragment.setDestination(mOfflineGraph.data.get(which));
                        }
                    }
                });
        return builder.create();
    }
}
