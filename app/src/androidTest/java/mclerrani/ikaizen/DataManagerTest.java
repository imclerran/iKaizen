package mclerrani.ikaizen;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Ian on 2/6/2016.
 */
public class DataManagerTest extends AndroidTestCase {

    private DataManager dm;

    public void setUp() throws Exception {
        dm = DataManager.getInstance(getContext());
        assertNotNull("DataManager.getInstance() returned null", dm);
        dm.getKaizenList().add(new Kaizen());
        int size = dm.getKaizenList().size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
    }

    public void testGetKaizenList() throws Exception {
        ArrayList<Kaizen> testList = dm.getKaizenList();
        assertNotNull("DataManager.getKaizenList() returned null", testList);
        int size = testList.size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
        System.out.println("testGetKaizenList - PASSED");
    }

    public void testValidGetKaizen() throws Exception {
        int size = dm.getKaizenList().size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
        Kaizen k = dm.getKaizen(size - 1);
        assertNotNull("DataManager.getKaizen() returned null", k);
        System.out.println("testValidGetKaizen - PASSED");
    }

    public void testInvalidGetKaizen() throws Exception {
        int size = dm.getKaizenList().size();
        Kaizen k = dm.getKaizen(size);
        assertNull("DataManager.getKaizen() returned not null", k);
        System.out.println("testInvalidGetKaizen - PASSED");
    }
}