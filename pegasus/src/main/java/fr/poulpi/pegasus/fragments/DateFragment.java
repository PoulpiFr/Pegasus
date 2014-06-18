package fr.poulpi.pegasus.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.poulpi.pegasus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 */
public class DateFragment extends Fragment {
    
    public static final String TAG = "DateFragment";

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    
    private OnFragmentInteractionListener mListener;

    public DateFragment() {
        // Required empty public constructor
    }

    public static DateFragment newInstance() {
        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                                 
        View v = inflater.inflate(R.layout.fragment_date, container, false);
        
        mTimePicker = (TimePicker) v.findViewById(R.id.time_picker);
        mDatePicker = (DatePicker) v.findViewById(R.id.date_picker);
        
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (mListener != null) {
                    mListener.onTimeChanged(hourOfDay, minute);
                }
            }
        });
        
        mDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {

            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (mListener != null) {
                    mListener.onDateChanged(year, monthOfYear, dayOfMonth);
                }
            }
        )};
        
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onTimeChanged(int hourOfDay, int minute);
        public void onDateChanged(int year, int monthOfYear, int dayOfMonth);
    }

}
