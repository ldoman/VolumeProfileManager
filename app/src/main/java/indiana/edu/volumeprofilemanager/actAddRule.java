package indiana.edu.volumeprofilemanager;

import android.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class actAddRule extends AppCompatActivity implements View.OnClickListener, dlgNewRule.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_rule);

        // Check for passed day
        String day = getIntent().getStringExtra("day");

        // If day isn't null set titlebar appropriately
        if (day != null)
        {
            setTitle(day + " Schedule");
        }

        //Add onClickListeners for buttons
        View btnNewRule = findViewById(R.id.btnNewRule);
        btnNewRule.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_add_rule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Remove the settings button on the action bar
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewRule:
                showNoticeDialog();
                //finish();
                break;
        }
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public  void showNoticeDialog ()  {
        // Create an instance of the Dialog fragment and show it
        DialogFragment newFragment = dlgNewRule.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    // The Dialog fragment Receives a Reference to this through the Activity
    // Fragment.onAttach () callback, Which it uses to Call the following Methods
    // defined by the interface NoticeDialogFragment.NoticeDialogListener
    public  void onDialogPositiveClick ( DialogFragment Dialog )  {
        // User Touched the Dialog's Positive button
    }

    public  void onDialogNegativeClick ( DialogFragment Dialog )  {
        // User Dialog's Touched the negative button
    }

}
