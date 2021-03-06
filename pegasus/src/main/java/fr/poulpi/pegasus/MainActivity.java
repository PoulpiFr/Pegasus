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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import fr.poulpi.pegasus.adapters.DrawerItemAdapter;
import fr.poulpi.pegasus.fragments.DateFragment;
import fr.poulpi.pegasus.fragments.ItinaryPreferenceFragment;
import fr.poulpi.pegasus.fragments.MetroMapFragment;
import fr.poulpi.pegasus.fragments.NewSearchFragment;
import fr.poulpi.pegasus.fragments.PredictionsFragment;
import fr.poulpi.pegasus.model.GoogleAPIResultPrediction;

public class MainActivity extends Activity implements
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
        //getMenuInflater().inflate(R.menu.routing, menu);
        return true;
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {

        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment;
        if(position == 0) {
            fragment = NewSearchFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
        else if(position == 1) {
            fragment = MetroMapFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
        else if (position == 2){
            fragment = ItinaryPreferenceFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

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
    public void onTimeChanged(int hourOfDay, int minute){
        ((NewSearchFragment)getFragmentManager().findFragmentByTag(NewSearchFragment.TAG)).updateTimeInDateText(hourOfDay, minute);
    }

    @Override
    public void onDateChanged(int year, int monthOfYear, int dayOfMonth){
        ((NewSearchFragment)getFragmentManager().findFragmentByTag(NewSearchFragment.TAG)).updateDateInDateText(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onPredictionsFragmentInteraction(String mode, GoogleAPIResultPrediction destination) {

        if(mode.equals(PredictionsFragment.FROM)){
            ((NewSearchFragment)getFragmentManager().findFragmentByTag(NewSearchFragment.TAG)).setFromDestination(destination);
        } else if (mode.equals(PredictionsFragment.TO)){
            ((NewSearchFragment)getFragmentManager().findFragmentByTag(NewSearchFragment.TAG)).setToDestination(destination);
        }

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

}
