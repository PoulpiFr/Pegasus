package fr.poulpi.pegasus;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import fr.poulpi.pegasus.fragments.ItinaryDetailsFragment;
import fr.poulpi.pegasus.fragments.NewSearchFragment;
import fr.poulpi.pegasus.model.CTPJourney;

/**
 * Created by pokito on 14/07/2014.
 */
public class ItinaryDetailsActivity extends Activity implements ItinaryDetailsFragment.OnFragmentInteractionListener {

    public static final String JOURNEY = "journey";

    private CTPJourney mJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinary_details);

        if(getIntent().getExtras().containsKey(JOURNEY)){
            mJourney = (CTPJourney) getIntent().getExtras().getSerializable(JOURNEY);
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.content_frame, ItinaryDetailsFragment.newInstance(), NewSearchFragment.TAG)
                    .commit();
        }
    }

    public CTPJourney getJourney() {
        return mJourney;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.itinary_details, menu);
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
    public void onFragmentInteraction(Uri uri) {

    }
}

