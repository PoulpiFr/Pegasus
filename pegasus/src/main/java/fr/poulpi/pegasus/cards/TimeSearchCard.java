package fr.poulpi.pegasus.cards;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.dialog.TimePickerFragment;
import fr.poulpi.pegasus.interfaces.TimeInterface;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by pokito on 21/03/14.
 */
public class TimeSearchCard extends Card implements TimeInterface {

    EditText edTime;

    public TimeSearchCard(Context context) {
        super(context, R.layout.time_search_card_inner_content);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_time_search_card);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        edTime = (EditText) view.findViewById(R.id.ed_time_search_card);

        Calendar c = Calendar.getInstance();

        String time = new StringBuilder().append(c.get(Calendar.HOUR_OF_DAY)).append(":").append(c.get(Calendar.MINUTE)).toString();

        edTime.setText(time);

        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(((Activity)getContext()).getFragmentManager(), "timePicker");

            }
        });
    }



    @Override
    public void setTime(int hourOfDay, int minute) {

        String time = new StringBuilder().append(hourOfDay).append(":").append(minute).toString();

        edTime.setText(time);

    }
}
