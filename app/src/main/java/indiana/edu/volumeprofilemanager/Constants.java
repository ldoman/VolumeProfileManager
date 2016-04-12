package indiana.edu.volumeprofilemanager;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;

import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by doman on 2/29/2016.
 *
 *  This class is used to store values and functions that are of use throughout the whole app.
 *  All interfacing with Realm to store volume profile objects and schedule objects will be
 *  done inside here.
 */
public class Constants
{
    // Context of app
    static Context appContext = null;

    //Audiomanager
    static AudioManager audio;

    // Realm vars
    static RealmConfiguration realmConfig;
    static Realm realm;

    // Local cache of profiles
    static ArrayList<objVolumeProfile> profiles = new ArrayList<>();

    // Request codes
    final static int REQUEST_EDIT_PROFILE = 0;
    final static int REQUEST_NEW_PROFILE = 0;

    // Set the context variable for the mainActivity so it is accessible throughout the app
    public static void setContext(Context con)
    {
        appContext = con;
        realmConfig = new RealmConfiguration.Builder(appContext).build();
        realm = Realm.getInstance(realmConfig);
    }

    // Retrieves the list of user profiles from realm storage
    public static ArrayList<objVolumeProfile> getProfiles()
    {
        if (appContext != null && realmConfig != null && realm != null)
        {
            RealmResults<objVolumeProfile> tempResults = realm.where(objVolumeProfile.class).findAll();
            ArrayList<objVolumeProfile> rtnResults = new ArrayList<>();

            rtnResults.addAll(tempResults.subList(0, tempResults.size()));

            // Update local list
            profiles.clear();
            profiles.addAll(tempResults.subList(0, tempResults.size()));

            return rtnResults;
        }
        else
        {
            return null;
        }
    }

    // Retrieves the list with profiles names
    public static ArrayList<String> getProfileStrings()
    {
        ArrayList<objVolumeProfile> temp = getProfiles();
        ArrayList<String> profileStrings = new ArrayList<>();

        if (temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                profileStrings.add(temp.get(i).getName());
            }
        }

        return profileStrings;
    }

    // Save a single profile via realm
    public static void saveProfile(objVolumeProfile newProf)
    {
        if (appContext != null && realmConfig != null && realm != null)
        {
            realm.beginTransaction();
            realm.copyToRealm(newProf);
            realm.commitTransaction();
        }
    }

    // Edit the values of a realm object already in storage
    public static void updateProfile(objVolumeProfile prof)
    {
        if (appContext != null && realmConfig != null && realm != null)
        {
            RealmResults<objVolumeProfile> profs = realm.where(objVolumeProfile.class).equalTo("name", prof.getName()).findAll();
            realm.beginTransaction();
            objVolumeProfile temp = profs.get(0);
            temp.setRingtone(prof.getRingtone());
            temp.setMedia(prof.getMedia());
            temp.setAlarm(prof.getAlarm());
            temp.setInCall(prof.getInCall());
            temp.setNotifications(prof.getNotifications());
            temp.setSystem(prof.getSystem());
            temp.setVibrate(prof.getVibrate());
            realm.commitTransaction();
        }
    }

    // Uses AudioManager to activate a given volume profile object
    public static void enableProfile(objVolumeProfile prof)
    {
        // Assign stream volumes
        audio.setStreamVolume(AudioManager.STREAM_RING, prof.getRingtone(), AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, prof.getMedia(), AudioManager.FLAG_ALLOW_RINGER_MODES);
        audio.setStreamVolume(AudioManager.STREAM_ALARM, prof.getAlarm(), AudioManager.FLAG_ALLOW_RINGER_MODES);
        audio.setStreamVolume(AudioManager.STREAM_VOICE_CALL, prof.getInCall(), AudioManager.FLAG_ALLOW_RINGER_MODES);
        audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, prof.getNotifications(), AudioManager.FLAG_ALLOW_RINGER_MODES);
        audio.setStreamVolume(AudioManager.STREAM_SYSTEM, prof.getSystem(), AudioManager.FLAG_ALLOW_RINGER_MODES);

        //Settings.System.putInt(appContext.getContentResolver(), Settings.System.VIBRATE_WHEN_RINGING, prof.getVibrate() ? 1 : 0);

        // Assign vibrate settings
        if (prof.getVibrate()) {
            if(audio.getStreamVolume(AudioManager.STREAM_RING) == 0){
                audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
            else{
                Settings.System.putInt(appContext.getContentResolver(), Settings.System.VIBRATE_WHEN_RINGING, 1);
            }
//            Settings.System.putInt(appContext.getContentResolver(), Settings.System.VIBRATE_WHEN_RINGING, 1);
//            audio.setVibrateSetting(audio.VIBRATE_TYPE_RINGER, audio.VIBRATE_SETTING_ON);
//            audio.setVibrateSetting(audio.VIBRATE_TYPE_NOTIFICATION, audio.VIBRATE_SETTING_ON);
        }
        else{
            Settings.System.putInt(appContext.getContentResolver(), Settings.System.VIBRATE_WHEN_RINGING, 0);

            if(audio.getStreamVolume(AudioManager.STREAM_RING) == 0){
                audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }
    }

    // Removes profiles from realm storage with the passed name argument
    public static void deleteProfile(String profName)
    {
        RealmResults<objVolumeProfile> profs = realm.where(objVolumeProfile.class).equalTo("name", profName).findAll();
        realm.beginTransaction();
        profs.removeLast();
        realm.commitTransaction();
    }

    // Returns profile from cache with the specified name
    public static objVolumeProfile getProfile(String name)
    {
        for (int i = 0; i < profiles.size(); i++)
        {
            if (profiles.get(i).getName().equalsIgnoreCase(name))
            {
                return profiles.get(i);
            }
        }

        return null;
    }

    // Get the schedule objects from storage and create them if not found
    public static ArrayList<objSchedule> getSchedules()
    {
        RealmResults<objSchedule> schedulesResults = realm.where(objSchedule.class).findAll();

        if (schedulesResults.size() == 0)
        {
            makeSchedules();
            return getSchedules();
        }

        ArrayList<objSchedule>  schedules = new ArrayList<>();
        schedules.addAll(schedulesResults.subList(0, schedulesResults.size()));

        return schedules;
    }

    // Retrieves the list with profiles names
    public static ArrayList<String> getScheduleStrings()
    {
        ArrayList<objSchedule> temp = getSchedules();
        ArrayList<String> scheduleStrings = new ArrayList<>();

        if (temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                scheduleStrings.add(temp.get(i).getName());
            }
        }

        return scheduleStrings;
    }

    // Make the seven schedule objects
    public static void makeSchedules()
    {
        realm.beginTransaction();
        objSchedule mon = realm.createObject(objSchedule.class);
        mon.setName("Monday");

        objSchedule tues = realm.createObject(objSchedule.class);
        tues.setName("Tuesday");

        objSchedule wed = realm.createObject(objSchedule.class);
        wed.setName("Wednesday");

        objSchedule thur = realm.createObject(objSchedule.class);
        thur.setName("Thursday");

        objSchedule fri = realm.createObject(objSchedule.class);
        fri.setName("Friday");

        objSchedule sat = realm.createObject(objSchedule.class);
        sat.setName("Saturday");

        objSchedule sun = realm.createObject(objSchedule.class);
        sun.setName("Sunday");

        realm.commitTransaction();

//        // Perform asynchronously to save UI thread processing
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm bgRealm) {
//                objSchedule mon = realm.createObject(objSchedule.class);
//                mon.setName("Monday");
//
//                objSchedule tues = realm.createObject(objSchedule.class);
//                tues.setName("Tuesday");
//
//                objSchedule wed = realm.createObject(objSchedule.class);
//                wed.setName("Wednesday");
//
//                objSchedule thur = realm.createObject(objSchedule.class);
//                thur.setName("Thursday");
//
//                objSchedule fri = realm.createObject(objSchedule.class);
//                fri.setName("Friday");
//
//                objSchedule sat = realm.createObject(objSchedule.class);
//                sat.setName("Saturday");
//
//                objSchedule sun = realm.createObject(objSchedule.class);
//                sun.setName("Sunday");
//            }
//        }, new Realm.Transaction.Callback() {
//            @Override
//            public void onSuccess() {
//            }
//
//            @Override
//            public void onError(Exception e) {
//                // transaction is automatically rolled-back, do any cleanup here
//            }
//        });
    }


}
