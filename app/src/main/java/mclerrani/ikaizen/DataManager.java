package mclerrani.ikaizen;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ian on 1/21/2016.
 */
public class DataManager { /* SINGLETON */

    private static DataManager dm;
    private ArrayList<Kaizen> kaizenList;
    private ArrayList<Countermeasure> countermeasureList;
    //private int nextListId;

    private DataManager() {
        kaizenList          = new ArrayList<>();
        countermeasureList  = new ArrayList<>();
    }

    // get the data manager
    // if it doesn't exist, instantiate it
    public static DataManager getInstance() {
        if(null == dm) {
            dm = new DataManager();
        }
        return dm;
    }

    public void sortKaizenList() { Collections.sort(kaizenList); }


    public ArrayList<Kaizen> getKaizenList() {
        //Collections.sort(kaizenList);
        return kaizenList;
    }

    public ArrayList<Countermeasure> getCountermeasureList() {
        return countermeasureList;
    }

    public Kaizen getKaizen(int id) {
        if(id < 0)
            return null;

        int i = 0;
        int size = kaizenList.size();
        while(id != kaizenList.get(i).getItemID()) {
            i++;
            if(size <= i)
                return null;
        }
        return kaizenList.get(i);
    }
}