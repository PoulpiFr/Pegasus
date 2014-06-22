package fr.poulpi.pegasus.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.SuggestedItinariesActivity;
import fr.poulpi.pegasus.constants.GoogleAPIConf;
import fr.poulpi.pegasus.interfaces.GooglePlaceAPIInterface;
import fr.poulpi.pegasus.model.GoogleAPIPredictionsResponse;
import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewSearchFragment extends Fragment {

    RestAdapter restAdapter;

    public static final String TAG = "NewSearchFragment";

    String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/place";

    private static final int FROM_EDIT_MODE = 0;
    private static final int TO_EDIT_MODE = 1;
    private static final int DATE_EDIT_MODE = 2;
    private static final int NORMAL_MODE = 3;

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
    private float translatedHeight;
    private final Rect mTmpRect = new Rect();
    private int ANIMATION_DURATION = 350;
    private Interpolator ANIMATION_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private int mEditMode;
    private LayerEnablingAnimatorListener mLayerEnablingAnimatorListener;

    private String fromRefKey = "fromRefKey";
    private String toRefKey = "toRefKey";
    private String fromRef = null;
    private String toRef = null;

    private GoogleAPIResultPrediction fromDestination;
    private GoogleAPIResultPrediction toDestination;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        if(fromDestination != null && fromDestination != null) {
            outState.putString(fromRefKey, fromDestination.getReference());
            outState.putString(toRefKey, toDestination.getReference());
        }
    }

    private View.OnClickListener btnSearchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), SuggestedItinariesActivity.class);

            Bundle bundle = new Bundle();
            if(fromRef != null && toRef != null) {
                bundle.putString(SuggestedItinariesActivity.FROM_REF, fromDestination.getReference());
                bundle.putString(SuggestedItinariesActivity.TO_REF, toDestination.getReference());

                DateFormat dfISO8601 = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                Date date = new Date();
                bundle.putString(SuggestedItinariesActivity.DATE, dfISO8601.format(date));

                bundle.putString(SuggestedItinariesActivity.FROM_NAME, fromDestination.getDescription());
                bundle.putString(SuggestedItinariesActivity.TO_NAME, toDestination.getDescription());
                intent.putExtras(bundle);

                startActivity(intent);
            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setMessage(getActivity().getString(R.string.missing_departure_arrival));
                adb.show();
            }

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

        if(savedInstanceState.containsKey(fromRefKey) && savedInstanceState.containsKey(toRefKey)){
            fromRef = savedInstanceState.getString(fromRefKey);
            toRef = savedInstanceState.getString(toRefKey);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // debug purpose only, to get the messages
        RestAdapter.Log log = new RestAdapter.Log(){
            public void log(String msg){
                System.out.println(msg);
            }
        };

        // Create a very simple REST adapter which points the GitHub API endpoint.
        restAdapter = new RestAdapter.Builder()
                //.setLog(log)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(GOOGLE_API_URL)
                .build();

        getFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
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

        mBtnSearchContainer.setOnClickListener(btnSearchOnClickListener);

        mLayerEnablingAnimatorListener = new LayerEnablingAnimatorListener(view);
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

    public void setFromDestination(GoogleAPIResultPrediction fromDestination) {
        this.fromDestination = fromDestination;
        this.fromRef = fromDestination.getReference();
        mFromEditText.setText(fromDestination.getDescription());
    }

    public void setToDestination(GoogleAPIResultPrediction toDestination) {
        this.toDestination = toDestination;
        this.toRef = toDestination.getReference();
        mToEditText.setText(toDestination.getDescription());
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
        public void onSearchFragmentInteraction(Uri uri);
    }

    /*------ This is where we manage the smooth transitions between the different edit modes -----*/
    private void getIntoEditMode(int mode){

        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        PredictionsFragment pf;

        switch (mode){
            case FROM_EDIT_MODE:
                pf = PredictionsFragment.newInstance(PredictionsFragment.FROM);
                ft.replace(R.id.edit_mode_container, pf, PredictionsFragment.TAG);
                ft.addToBackStack(PredictionsFragment.TAG);
                ft.commit();

                focusOn(mFromContainer, mMainContainer, true);
                fadeOutToBottom(mDateContainer, true);
                fadeOutToBottom(mToContainer, true);
                stickTo(mDestinationSeparator, mFromContainer, true);
                break;

            case TO_EDIT_MODE:
                pf = PredictionsFragment.newInstance(PredictionsFragment.TO);
                ft.replace(R.id.edit_mode_container, pf, PredictionsFragment.TAG);
                ft.addToBackStack(PredictionsFragment.TAG);
                ft.commit();

                focusOn(mToContainer, mMainContainer, true);
                fadeOutToBottom(mDateContainer, true);
                break;

            case DATE_EDIT_MODE:
                DateFragment df = DateFragment.newInstance();
                ft.replace(R.id.edit_mode_container, df, DateFragment.TAG);
                ft.addToBackStack(DateFragment.TAG);
                ft.commit();

                focusOn(mDateContainer, mMainContainer, true);
                break;
        }

        mEditMode = mode;
        mEditContainer.setVisibility(View.VISIBLE);

        slideInToTop(mEditContainer, true);
        fadeOutToBottom(mBtnSearchContainer, true);

    }

    private FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            FragmentManager fm = getFragmentManager();

            if (fm != null) {
                int i = fm.getBackStackEntryCount();

                // If we are getting back to the "Normal mode" we have to get the UI back !
                if (i == 0) {

                    switch (mEditMode) {
                        case FROM_EDIT_MODE:
                            mFromEditText.clearFocus();

                            focusOff(mFromContainer, mMainContainer, true);
                            fadeOutToTop(mDateContainer, true);
                            fadeOutToTop(mToContainer, true);
                            stickOut(mDestinationSeparator, mFromContainer, true);
                            break;
                        case TO_EDIT_MODE:
                            mToEditText.clearFocus();

                            focusOff(mToContainer, mMainContainer, true);
                            fadeOutToTop(mDateContainer, true);
                            break;
                        case DATE_EDIT_MODE:
                            focusOff(mDateContainer, mMainContainer, true);
                            break;
                    }

                    mEditMode = NORMAL_MODE;
                    mEditContainer.setVisibility(View.INVISIBLE);

                    //slideInToTop(mEditContainer, true);
                    fadeOutToTop(mBtnSearchContainer, true);
                }
            }
        }
    };

    private View.OnFocusChangeListener onFromFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                getIntoEditMode(FROM_EDIT_MODE);
                ((EditText) v).addTextChangedListener(tvWatcher);
            }
        }
    };

    private View.OnFocusChangeListener onToFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                getIntoEditMode(TO_EDIT_MODE);
                ((EditText) v).addTextChangedListener(tvWatcher);
            }
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

    /*------ This is where the magic Google Places predictions appears (begin with text detection in the edittext then call to the WS and reception of the data (via a beautiful callback) ----*/
    private TextWatcher tvWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s.length() % 2 == 0 || count > 1){
                googleAPIRequestPredictions(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public void googleAPIRequestPredictions(String str){

        // Create an instance of our API interface.
        GooglePlaceAPIInterface ws = restAdapter.create(GooglePlaceAPIInterface.class);

        ws.response("true", GoogleAPIConf.API_KEY,
                GoogleAPIConf.PARIS_CENTER,
                GoogleAPIConf.PARIS_RADIUS,
                GoogleAPIConf.LANG,
                GoogleAPIConf.COMPONENTS,
                GoogleAPIConf.TYPES,
                str,
                predictionsCallback);
    }

    Callback predictionsCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            PredictionsFragment fragment = (PredictionsFragment) getFragmentManager().findFragmentByTag(PredictionsFragment.TAG);
            if(fragment != null) fragment.refreshData((GoogleAPIPredictionsResponse) o);

        }

        @Override
        public void failure(RetrofitError retrofitError) {
            // Failure is not an option ! (I'm kidding : TODO)
        }
    };

    /*------ Animation fonctions (thanks to Cyril Mottier) ------*/

    private void focusOn(View v, View movableView, boolean animated) {

        v.getDrawingRect(mTmpRect);
        mMainContainer.offsetDescendantRectToMyCoords(v, mTmpRect);

        movableView.animate().
                translationY(-mTmpRect.top).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                setListener(mLayerEnablingAnimatorListener).
                start();
    }

    private void focusOff(View v, View movableView, boolean animated) {

        movableView.animate().
                translationY(0).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                setListener(mLayerEnablingAnimatorListener).
                start();
    }

    private void fadeOutToBottom(View v, boolean animated) {

        mHalfHeight = getView().getHeight()/2;

        v.animate().
                translationYBy(mHalfHeight).
                alpha(0).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                setListener(mLayerEnablingAnimatorListener).
                start();
    }

    private void fadeOutToTop(View v, boolean animated) {

        v.animate().
                translationY(0).
                alpha(1).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                setListener(mLayerEnablingAnimatorListener).
                start();
    }

    private void slideInToTop(View v, boolean animated) {

        mHalfHeight = getView().getHeight()/2;

        v.animate().translationYBy(mHalfHeight).setDuration(0).start();

        v.animate().
                translationY(0).
                alpha(1).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setListener(mLayerEnablingAnimatorListener).
                setInterpolator(ANIMATION_INTERPOLATOR).
                start();
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
                setListener(mLayerEnablingAnimatorListener).
                start();
    }

    private void stickOut(View v, View viewToStickTo, boolean animated) {

        Rect mTmpRect2 = new Rect();

        v.getDrawingRect(mTmpRect);
        mMainContainer.offsetDescendantRectToMyCoords(v, mTmpRect);

        /* It works, I don't even know why. Need some sleep ...*/
        mFromContainer.getDrawingRect(mTmpRect2);
        mMainContainer.offsetDescendantRectToMyCoords(mFromContainer, mTmpRect2);

        v.animate().
                translationY(0).
                setDuration(animated ? ANIMATION_DURATION : 0).
                setInterpolator(ANIMATION_INTERPOLATOR).
                setListener(mLayerEnablingAnimatorListener).
                start();
    }

    /*------ Try at hardware layer ------*/
    public class LayerEnablingAnimatorListener extends AnimatorListenerAdapter {

        private View mTargetView;

        private int mLayerType;

        public LayerEnablingAnimatorListener(View targetView) {
            if(targetView != null) {
                mTargetView = targetView;
            }
        }

        public View getTargetView() {
            return mTargetView;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            mLayerType = mTargetView.getLayerType();
            mTargetView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            mTargetView.setLayerType(mLayerType, null);
        }
    }

}
