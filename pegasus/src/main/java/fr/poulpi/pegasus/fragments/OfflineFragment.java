package fr.poulpi.pegasus.fragments;



import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.dialog.StopChooserDialog;
import fr.poulpi.pegasus.dijkstra.DijkstraCalc;
import fr.poulpi.pegasus.interfaces.StopSelectionInterface;
import fr.poulpi.pegasus.model.OfflineGraph;
import fr.poulpi.pegasus.model.OfflineStop;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link OfflineFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class OfflineFragment extends Fragment implements StopSelectionInterface {

    static public String TAG = "OfflineFragment";

    OfflineGraph mOfflineGraph;

    private OfflineStop mDeparture = null;
    private OfflineStop mDestination = null;
    private EditText mDepartureEditText;
    private EditText mDestinationEditText;

    public static OfflineFragment newInstance() {
        OfflineFragment fragment = new OfflineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OfflineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectMapper mapper = new ObjectMapper();

        File file = new File(getActivity().getExternalFilesDir(null), "graph.json");
        mOfflineGraph = null;
        try {
            mOfflineGraph = mapper.readValue(file, OfflineGraph.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline, container, false);

        mDepartureEditText = (EditText) view.findViewById(R.id.departure);
        mDestinationEditText = (EditText) view.findViewById(R.id.destination);

        mDepartureEditText.setOnClickListener(departureEditTextOnClickListener);
        mDestinationEditText.setOnClickListener(destinationEditTextOnClickListener);

        ((Button)view.findViewById(R.id.btn_compute)).setOnClickListener(computeBtnOnClickListener);
        return view;

    }

    private View.OnClickListener departureEditTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DialogFragment dialogFrag = new StopChooserDialog(mOfflineGraph, StopChooserDialog.DEPARTURE);
            dialogFrag.setTargetFragment(OfflineFragment.this, 0);
            dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");

        }
    };

    private View.OnClickListener destinationEditTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DialogFragment dialogFrag = new StopChooserDialog(mOfflineGraph, StopChooserDialog.DESTINATION);
            dialogFrag.setTargetFragment(OfflineFragment.this, 0);
            dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");

        }
    };

    private View.OnClickListener computeBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DijkstraCalc dijkstra = new DijkstraCalc(mOfflineGraph);
            dijkstra.execute(mDeparture);

            LinkedList<OfflineStop> path = dijkstra.getPath(mDestination);

            String tmp = "";
            for(OfflineStop offlineStop : path){
                tmp += offlineStop.name + "\n";
            }

            AlertDialog.Builder alb = new AlertDialog.Builder(getActivity());
            alb.setMessage(tmp);
            alb.create().show();
        }
    };

    @Override
    public void setDestination(OfflineStop offlineStop) {
        mDestination = offlineStop;
        mDestinationEditText.setText(offlineStop.name);
    }

    @Override
    public void setDeparture(OfflineStop offlineStop) {
        mDeparture = offlineStop;
        mDepartureEditText.setText(offlineStop.name);
    }
}
