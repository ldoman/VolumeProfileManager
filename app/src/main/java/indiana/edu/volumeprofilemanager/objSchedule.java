package indiana.edu.volumeprofilemanager;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmObject;

/**
 * Created by doman on 2/27/2016.
 */
public class objSchedule extends RealmObject {

    //private HashMap map;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public objSchedule(String name)
    {
        //this.map = new HashMap();
        this.name = name;

    }

    public objSchedule()
    {}

//    public HashMap getMap() {
//        return map;
//    }
//
//    public void setMap(HashMap map) {
//        this.map = map;
//    }
}
