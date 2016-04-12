package indiana.edu.volumeprofilemanager;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements frgProfiles.OnFragmentInteractionListener, frgSchedule.OnFragmentInteractionListener, frgSettings.OnFragmentInteractionListener{

    // Tab bar
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set context for sharedpreferences
        Constants.setContext(this);

        // Initialize AudioManager in Constants
        Constants.audio = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        // Setup tabs
        mTabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        // Add fragments to TabHost
        mTabHost.addTab(mTabHost.newTabSpec("profiles_tab").setIndicator("Profiles"),
                frgProfiles.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("schedule_tab").setIndicator("Schedule"),
                frgSchedule.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("settings_tab").setIndicator("Settings"),
                frgSettings.class, null);

        // Edit tabs font size
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(15);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Remove the settings button on the action bar
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    // TabHost fragment interaction listener
    public void onProfileFragmentInteraction(String str){
        //you can leave it empty
    }

    // TabHost fragment interaction listener
    public void onScheduleFragmentInteraction(String str){
        //you can leave it empty
    }

    // TabHost fragment interaction listener
    public void onSettingsFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    // Allow for other activities to set current tab
    public void switchTab(int tab){
        mTabHost.setCurrentTab(tab);
    }

}
