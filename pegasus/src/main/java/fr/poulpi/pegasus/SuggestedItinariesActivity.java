package fr.poulpi.pegasus;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import android.app.ActionBar;
import android.app.Application;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.poulpi.pegasus.adapters.SuggestedItinaryListAdapter;
import fr.poulpi.pegasus.constants.GoogleAPIConf;
import fr.poulpi.pegasus.fragments.SuggestedItinariesFragment;
import fr.poulpi.pegasus.fragments.SuggestedItinariesGoogleMapFragment;
import fr.poulpi.pegasus.interfaces.GooglePlaceAPIInterface;
import fr.poulpi.pegasus.interfaces.NavitiaIoInterface;
import fr.poulpi.pegasus.model.CTPJourney;
import fr.poulpi.pegasus.model.CTPJourneyResponse;
import fr.poulpi.pegasus.model.GoogleAPIDetailsPlace;
import fr.poulpi.pegasus.model.GoogleAPIGeometry;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class SuggestedItinariesActivity extends FragmentActivity implements ActionBar.TabListener,
        SuggestedItinariesFragment.OnFragmentInteractionListener,
        SuggestedItinariesGoogleMapFragment.OnFragmentInteractionListener,
SuggestedItinariesActivity.journeysProvider{

    public static final String TO_REF = "to_ref";
    public static final String FROM_REF = "from_ref";
    public static final String TO_NAME = "to_name";
    public static final String FROM_NAME = "from_name";
    public static final String DATE = "date";

    SectionsPagerAdapter mSectionsPagerAdapter;

    RestAdapter navitiaRestAdapter;
    RestAdapter googleRestAdapter;

    GoogleAPIGeometry from = null;
    GoogleAPIGeometry to = null;

    ViewPager mViewPager;
    private ArrayList<CTPJourney> mJourneys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_itinaries);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        String API_URL = "http://api.navitia.io/v1/";

        // debug purpose only, to get the messages
        RestAdapter.Log log = new RestAdapter.Log(){
            public void log(String msg){
                System.out.println(msg);
            }
        };

        // Set the navitia time format
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return df.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        });

        Gson gson = gsonBuilder.create();

        // Create a very simple REST adapter which points the GitHub API endpoint.
        navitiaRestAdapter = new RestAdapter.Builder()
                .setLog(log)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        // Demande des coordonnées GPS à partir de la référence Places API
        API_URL = "https://maps.googleapis.com/maps/api/place";

        googleRestAdapter = new RestAdapter.Builder()
                .setLog(log)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        Bundle bundle = getIntent().getExtras();

        GooglePlaceAPIInterface tmp = googleRestAdapter.create(GooglePlaceAPIInterface.class);
        tmp.details("true",
                GoogleAPIConf.API_KEY,
                bundle.getString(FROM_REF),
                "fr",
                detailsFromCallback);
        tmp.details("true",
                GoogleAPIConf.API_KEY,
                bundle.getString(TO_REF),
                "fr",
                detailsToCallback);
    }

    private void askForItinary(){

        String fromStr = new StringBuilder().append(from.location.lng).append(";").append(from.location.lat).toString();
        String toStr = new StringBuilder().append(to.location.lng).append(";").append(to.location.lat).toString();

        NavitiaIoInterface ws = navitiaRestAdapter.create(NavitiaIoInterface.class);
        ws.journey(fromStr, toStr, getIntent().getExtras().getString(DATE), "departure", itinaryCallback);

    }


    Callback itinaryCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            if(o instanceof CTPJourneyResponse) {
                CTPJourneyResponse journeyResponse = (CTPJourneyResponse) o;
                if(journeyResponse.getJourneys() != null) {
                    mJourneys = journeyResponse.getJourneys();
                    ((SuggestedItinariesFragment)mSectionsPagerAdapter.getItem(0)).refreshFragment(journeyResponse);
                }
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {

            System.out.println(retrofitError);
            retrofitError.printStackTrace();

        }
    };

    Callback detailsFromCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            from = ((GoogleAPIDetailsPlace) o).result.geometry;
            if (to != null){
                askForItinary();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {}
    };

    Callback detailsToCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            to = ((GoogleAPIDetailsPlace) o).result.geometry;
            if (from != null){
                askForItinary();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {}
    };

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.suggested_itinaries, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuggestedItinariesFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSuggestedItinariesGoogleMapFragmentInteraction(Uri uri) {

    }

    @Override
    public ArrayList<CTPJourney> getJourneys() {
        return mJourneys;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = getIntent().getExtras();
            Fragment frag = null;
            String name;

            switch (position){
                case 0:
                    name = makeFragmentName(R.id.pager, position);
                    frag = getSupportFragmentManager().findFragmentByTag(name);
                    if (frag == null)
                        frag = SuggestedItinariesFragment.newInstance(bundle);
                    break;
                case 1:
                    name = makeFragmentName(R.id.pager, position);
                    frag = getSupportFragmentManager().findFragmentByTag(name);
                    if (frag == null)
                        frag = SuggestedItinariesGoogleMapFragment.newInstance();
                    break;
            }

            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.suggested_itinaries_text_section).toUpperCase(l);
                case 1:
                    return getString(R.string.suggested_itinaries_map_section).toUpperCase(l);
            }
            return null;
        }

        private String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
        }

    }

    public interface journeysProvider{
        public ArrayList<CTPJourney> getJourneys();
    }
}
