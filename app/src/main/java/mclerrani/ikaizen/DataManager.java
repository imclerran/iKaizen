package mclerrani.ikaizen;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ian on 1/21/2016.
 */
public class DataManager {

    private static DataManager dataManager;
    private ArrayList<Kaizen> kaizenList;
    //private int nextListId;

    private DataManager() {
        kaizenList = new ArrayList<>();
    }

    // get the data manager
    // if it doesn't exist, instantiate it
    public static DataManager getDataManager() {
        if(null == dataManager) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public ArrayList<Kaizen> getKaizenList() {
        Collections.sort(kaizenList);
        return kaizenList;
    }

    public Kaizen getKaizen(int id) {
        int i = 0;
        while(id != kaizenList.get(i).getItemID()) {
            i++;
        }
        return kaizenList.get(i);
    }

    /*public Kaizen newKaizen() {
        Kaizen k = new Kaizen();
        kaizenList.add(k);
        return k;
    }*/

    /*public boolean deleteKaizen(int id) {
        Kaizen k = getKaizen(id);
        if(null == k)
            return false;

       kaizenList.remove(k);
        return true;
    }*/
}
