package indiana.edu.volumeprofilemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

public class actDefineProfile extends AppCompatActivity implements View.OnClickListener {

    //Audiomanager
    AudioManager audio;

    // Seekbars
    SeekBar ringtone;
    SeekBar media;
    SeekBar alarm;
    SeekBar inCall;
    SeekBar notif;
    SeekBar system;

    // Text input
    EditText profName;

    // Checkbox
    CheckBox vibrate;

    // Flag to check if updating a profile and not creating
    boolean updateFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_profile);

        //Add onClickListeners for buttons
        View btnSave = findViewById(R.id.btnSaveProfile);
        btnSave.setOnClickListener(this);

        View btnCancel = findViewById(R.id.btnCancelProfile);
        btnCancel.setOnClickListener(this);

        // Assign seekbars to vars
        ringtone = (SeekBar)findViewById(R.id.seekBarRingtone);
        media = (SeekBar)findViewById(R.id.seekBarMedia);
        alarm = (SeekBar)findViewById(R.id.seekBarAlarm);
        inCall = (SeekBar)findViewById(R.id.seekBarInCall);
        notif = (SeekBar)findViewById(R.id.seekBarNotifications);
        system = (SeekBar)findViewById(R.id.seekBarSystem);

        // Assign edittext to var
        profName = (EditText)findViewById(R.id.inputProfName);

        // Assign checkbox to var
        vibrate = (CheckBox)findViewById(R.id.chkVibrate);

        // Initialize audiomanager
        audio = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        // Set seekbars max value to device's max volume
        setupSeekBars();

        // Check if passed profile
        String prof = getIntent().getStringExtra("profile");
        if (prof != null)
        {
            loadProfile(Constants.getProfile(prof));
            profName.setEnabled(false);
            updateFlag = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_define_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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

    // Process button clicks
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSaveProfile:
                if(updateFlag){
                    Constants.updateProfile(makeProfile());
                }
                else {
                    saveProfile();
                }
                finish();
                break;
            case R.id.btnCancelProfile:
                finish();
                break;

        }

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    // Make obj from current values
    public objVolumeProfile makeProfile()
    {
        objVolumeProfile newProf = new objVolumeProfile(profName.getText().toString(),
                ringtone.getProgress(),
                media.getProgress(),
                alarm.getProgress(),
                inCall.getProgress(),
                notif.getProgress(),
                system.getProgress(),
                vibrate.isChecked());

        return newProf;
    }

    // Store profile in sharedprefs
    public void saveProfile()
    {
        Constants.saveProfile(makeProfile());
    }

    // Setup to seekBars to have the correct steps for current phone's audio options
    public void setupSeekBars()
    {
        ringtone.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_RING));
        media.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        alarm.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_ALARM));
        inCall.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
        notif.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
        system.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
    }

    // Set seekbar positions for the passed profile's values
    public void loadProfile(objVolumeProfile prof)
    {
        profName.setText(prof.getName());
        ringtone.setProgress(prof.getRingtone());
        media.setProgress(prof.getMedia());
        alarm.setProgress(prof.getAlarm());
        inCall.setProgress(prof.getInCall());
        notif.setProgress(prof.getNotifications());
        system.setProgress(prof.getSystem());
        vibrate.setChecked(prof.getVibrate());
    }
}
