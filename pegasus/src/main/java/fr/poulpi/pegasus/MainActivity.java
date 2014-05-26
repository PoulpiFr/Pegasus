package fr.poulpi.pegasus;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import fr.poulpi.pegasus.adapters.DrawerItemAdapter;
import fr.poulpi.pegasus.cards.ItinarySearchCard;
import fr.poulpi.pegasus.fragments.DateFragment;
import fr.poulpi.pegasus.fragments.ItinaryPreferenceFragment;
import fr.poulpi.pegasus.fragments.MetroMapFragment;
import fr.poulpi.pegasus.fragments.NewSearchFragment;
import fr.poulpi.pegasus.fragments.OfflineFragment;
import fr.poulpi.pegasus.fragments.PredictionsFragment;
import fr.poulpi.pegasus.fragments.SearchFragment;
import fr.poulpi.pegasus.fragments.StopFragment;
import fr.poulpi.pegasus.interfaces.OTPActivityInterface;
import fr.poulpi.pegasus.interfaces.OTPFragmentInterface;
import fr.poulpi.pegasus.interfaces.PredictionsInterface;
import fr.poulpi.pegasus.model.ResultApiPrediction;
import fr.poulpi.pegasus.interfaces.TimeInterface;

public class MainActivity extends Activity implements
        StopFragment.OnFragmentInteractionListener,
        TimeInterface,
        PredictionsInterface,
        OTPActivityInterface,
        NewSearchFragment.OnFragmentInteractionListener,
        DateFragment.OnFragmentInteractionListener,
        PredictionsFragment.OnFragmentInteractionListener {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private String mDrawerTitle = "Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlanetTitles = getResources().getStringArray(R.array.screens_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerItemAdapter(this, new ArrayList<String>(Arrays.asList(mPlanetTitles))));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.content_frame, NewSearchFragment.newInstance(), NewSearchFragment.TAG)
                    .commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_navigation_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.routing, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Integer id) {

    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {

        Fragment fragment;
        if(position == 0) {
            fragment = NewSearchFragment.newInstance();
        }
        else if(position == 1) {
            fragment = MetroMapFragment.newInstance();
        }
        else if (position == 2){
            fragment = DateFragment.newInstance();
        }
        else if(position == 4){
                fragment = ItinaryPreferenceFragment.newInstance();
        } else {
           fragment = new OfflineFragment();
        }
        // Create a new fragment and specify the planet to show based on position

        Bundle args = new Bundle();
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    public void onSearchFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDateFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPredictionsFragmentInteraction(String id) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /* === PredictionsActivityInterface === */
    @Override
    public void googleAPIRequestPredictions(String str) {

        Fragment tmp = getFragmentManager().findFragmentByTag(SearchFragment.TAG);

        if(tmp instanceof PredictionsInterface){
            ((PredictionsInterface)tmp).googleAPIRequestPredictions(str);
        }

    }

    @Override
    public void googleAPISelectFromPrediction(ResultApiPrediction result) {

        Fragment tmp = getFragmentManager().findFragmentByTag(SearchFragment.TAG);

        if(tmp instanceof PredictionsInterface){
            ((PredictionsInterface)tmp).googleAPISelectFromPrediction(result);
        }

    }

    @Override
    public void googleAPISelectToPrediction(ResultApiPrediction result) {

        Fragment tmp = getFragmentManager().findFragmentByTag(SearchFragment.TAG);

        if(tmp instanceof PredictionsInterface){
            ((PredictionsInterface)tmp).googleAPISelectToPrediction(result);
        }

    }

    @Override
    public int getFromToState() {

        int result = ItinarySearchCard.UNKNOWN;

        Fragment tmp = getFragmentManager().findFragmentByTag(SearchFragment.TAG);

        if(tmp instanceof PredictionsInterface){
            result = ((PredictionsInterface)tmp).getFromToState();
        }

        return result;
    }

    /* === OTPActivityInterface === */
    @Override
    public void getFromTo() {

        ResultApiPrediction from = null;
        ResultApiPrediction to = null;

        Fragment tmp = getFragmentManager().findFragmentByTag(SearchFragment.TAG);

        if(tmp instanceof OTPFragmentInterface){
            from = ((OTPFragmentInterface)tmp).getFrom();
            to = ((OTPFragmentInterface)tmp).getTo();
        }

        if( from.reference!= null && to.reference!= null ) {

            Log.d("ph", "from " + from.reference);
            Log.d("ph", "to " + to.reference );

        }
    }

    @Override
    public void setTime(int hourOfDay, int minute) {

        Fragment tmp = getFragmentManager().findFragmentByTag(SearchFragment.TAG);

        if(tmp instanceof PredictionsInterface){
            ((TimeInterface)tmp).setTime(hourOfDay, minute);
        }

    }
}
