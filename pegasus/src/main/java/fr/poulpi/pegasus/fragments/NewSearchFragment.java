package fr.poulpi.pegasus.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;

import fr.poulpi.pegasus.R;

public class NewSearchFragment extends Fragment {

    public static final String TAG = "NewSearchFragment";
    private static final int FROM_EDIT_MODE = 0;
    private static final int TO_EDIT_MODE = 1;
    private static final int DATE_EDIT_MODE = 2;

    private OnFragmentInteractionListener mListener;
    private ViewGroup mMainContainer;
    private ViewGroup mEditContainer;
    private ViewGroup mBtnSearchContainer;
    private ViewGroup mFromContainer;
    private ViewGroup mToContainer;
    private ViewGroup mDateContainer;

    private View mDestinationSeparator;
    private EditText mFromEditText;
    private EditText mToEditText;

    private float mHalfHeight;
    private final Rect mTmpRect = new Rect();
    private int ANIMATION_DURATION = 300;
    private Interpolator ANIMATION_INTERPOLATOR = new AccelerateInterpolator();
    private int mEditMode;

    private void getIntoEditMode(int mode){

        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        PredictionsFragment pf;

        switch (mode){
            case FROM_EDIT_MODE:
                pf = PredictionsFragment.newInstance(PredictionsFragment.FROM);
                ft.replace(R.id.edit_mode_container, pf);
                ft.commit();

                focusOn(mFromContainer, mMainContainer, true);
                fadeOutToBottom(mDateContainer, true);
                fadeOutToBottom(mToContainer, true);
                stickTo(mDestinationSeparator, mFromContainer, true);
                break;

            case TO_EDIT_MODE:
                pf = PredictionsFragment.newInstance(PredictionsFragment.TO);
                ft.replace(R.id.edit_mode_container, pf);
                ft.commit();

                focusOn(mToContainer, mMainContainer, true);
                fadeOutToBottom(mDateContainer, true);
                break;

            case DATE_EDIT_MODE:
                DateFragment df = DateFragment.newInstance();
                ft.replace(R.id.edit_mode_container, df);
                ft.commit();

                focusOn(mDateContainer, mMainContainer, true);
                break;
        }

        mEditMode = mode;
        mEditContainer.setVisibility(View.VISIBLE);

        slideInToTop(mEditContainer, true);
        fadeOutToBottom(mBtnSearchContainer, true);

    }

    private View.OnFocusChangeListener onFromFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) getIntoEditMode(FROM_EDIT_MODE);
        }
    };

    private View.OnFocusChangeListener onToFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) getIntoEditMode(TO_EDIT_MODE);
        }
    };

    private View.OnClickListener mFromOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFromEditText.requestFocus();
        }
    };

    private View.OnClickListener mToOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mToEditText.requestFocus();
        }
    };

    private View.OnClickListener mDateContainerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getIntoEditMode(DATE_EDIT_MODE);
        }
    };

    public static NewSearchFragment newInstance() {
        NewSearchFragment fragment = new NewSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_search, container, false);

        mMainContainer = (ViewGroup) view.findViewById(R.id.form_container);
        mEditContainer = (ViewGroup) view.findViewById(R.id.edit_mode_container);
        mBtnSearchContainer = (ViewGroup) view.findViewById(R.id.search_btn_container);
        mFromContainer = (ViewGroup) view.findViewById(R.id.destination_from_container);
        mToContainer = (ViewGroup) view.findViewById(R.id.destination_to_container);
        mDateContainer = (ViewGroup) view.findViewById(R.id.date_container);
        mDestinationSeparator = (View) view.findViewById(R.id.destination_down_sep);

        mFromEditText = (EditText) view.findViewById(R.id.destination_from_text);
        mToEditText = (EditText) view.findViewById(R.id.destination_to_text);

        mFromContainer.setOnClickListener(mFromOnClickListener);
        mToContainer.setOnClickListener(mToOnClickListener);
        mDateContainer.setOnClickListener(mDateContainerOnClickListener);

        mFromEditText.setOnFocusChangeListener(onFromFocusChange);
        mToEditText.setOnFocusChangeListener(onToFocusChange);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSearchFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        public void onSearchFragmentInteraction(Uri uri);
    }

    private void focusOn(View v, View movableView, boolean animated) {

        v.getDrawingRect(mTmpRect);
        mMainContainer.offsetDescendantRectToMyCoords(v, mTmpRect);

        movableView.animate().
                translationY(-mTmpRect.top).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                start();
    }

    private void fadeOutToBottom(View v, boolean animated) {

        mHalfHeight = getView().getHeight()/2;

        v.animate().
                translationYBy(mHalfHeight).
                alpha(0).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                start();
    }

    private void slideInToTop(View v, boolean animated) {

        mHalfHeight = getView().getHeight()/2;

        v.animate().translationYBy(mHalfHeight).setDuration(0).start();

        v.animate().
                translationY(0).
                alpha(1).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR);
    }

    private void stickTo(View v, View viewToStickTo, boolean animated) {

        Rect mTmpRect2 = new Rect();

        v.getDrawingRect(mTmpRect);
        mMainContainer.offsetDescendantRectToMyCoords(v, mTmpRect);

        /* It works, I don't even know why. Need some sleep ...*/
        mFromContainer.getDrawingRect(mTmpRect2);
        mMainContainer.offsetDescendantRectToMyCoords(mFromContainer, mTmpRect2);

        v.animate().
                translationY(viewToStickTo.getHeight() - mTmpRect.top + mTmpRect2.top).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                start();
    }

}
