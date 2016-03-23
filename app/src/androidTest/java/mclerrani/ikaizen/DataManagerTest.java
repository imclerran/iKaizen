package mclerrani.ikaizen;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Test the DataManager class
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class DataManagerTest extends AndroidTestCase {

    private DataManager dm;
    private Kaizen k;

    /**
     * do necessary initialization before tests
     *
     * @// FIXME: 3/14/2016 Tests do not work with asynchronous tasks
     *
     * @throws Exception
     */
    public void setUp() throws Exception {
        dm = DataManager.getInstance(getContext());
        assertNotNull("DataManager.getInstance() returned null", dm);
        k = new Kaizen();
        dm.insertKaizen(k);
        int size = dm.getKaizenList().size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
    }

    /**
     * test the getKaizenList() method
     *
     * @throws Exception
     */
    public void testGetKaizenList() throws Exception {
        ArrayList<Kaizen> testList = dm.getKaizenList();
        assertNotNull("DataManager.getKaizenList() returned null", testList);
        int size = testList.size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
        System.out.println("testGetKaizenList - PASSED");
    }

    /**
     * test getKaizen() method for a valid Kaizen
     *
     * @throws Exception
     */
    public void testValidGetKaizen() throws Exception {
        int size = dm.getKaizenList().size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
        k = dm.getKaizen(k.getItemID());
        assertNotNull("DataManager.getKaizen() returned null", k);
        System.out.println("testValidGetKaizen - PASSED");
    }

    public void testInvalidGetKaizen() throws Exception {
        int size = dm.getKaizenList().size();
        Kaizen k = dm.getKaizen(1000000000);
        assertNull("DataManager.getKaizen() returned not null", k);
        System.out.println("testInvalidGetKaizen - PASSED");
    }
}