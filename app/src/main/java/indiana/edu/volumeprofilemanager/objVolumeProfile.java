package indiana.edu.volumeprofilemanager;

import io.realm.RealmObject;

/**
 * Created by doman on 2/27/2016.
 *
 * This is the class used to generate profile objects. Extedning this class by RealmObject
 * allows me to save it in realm storage. *
 */
public class objVolumeProfile extends RealmObject {

    // Attributes
    private String name;
    private int ringtone;
    private int media;
    private int inCall;
    private int alarm;
    private int notifications;
    private int system;
    private boolean vibrate;

    public objVolumeProfile(String name, int ringtone, int media, int inCall, int alarm, int notifications,
                            int system, boolean vibrate)
    {
        this.name = name;
        this.ringtone = ringtone;
        this.media = media;
        this.inCall = inCall;
        this.alarm = alarm;
        this.notifications = notifications;
        this.system = system;
        this.vibrate = vibrate;
    }

    public objVolumeProfile()
    {}

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public void setSystem(int system) { this.system = system; }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public void setInCall(int inCall) {
        this.inCall = inCall;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public void setRingtone(int ringtone) {
        this.ringtone = ringtone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getRingtone() {
        return ringtone;
    }

    public int getMedia() {
        return media;
    }

    public int getInCall() {
        return inCall;
    }

    public int getAlarm() {
        return alarm;
    }

    public int getNotifications() {
        return notifications;
    }

    public int getSystem() {
        return system;
    }

    public boolean getVibrate() {
        return vibrate;
    }

}
