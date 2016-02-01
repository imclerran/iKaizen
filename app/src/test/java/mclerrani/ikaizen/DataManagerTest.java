package mclerrani.ikaizen;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Ian on 1/31/2016.
 */
public class DataManagerTest {
    private DataManager dm;

    @Before
    public void setUp() throws Exception {
        dm = DataManager.getInstance();
        assertNotNull("DataManager.getInstance() returned null", dm);
        dm.getKaizenList().add(new Kaizen());
        int size = dm.getKaizenList().size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
    }

    @Test
    public void testGetKaizenList() throws Exception {
        ArrayList<Kaizen> testList = dm.getKaizenList();
        assertNotNull("DataManager.getKaizenList() returned null", testList);
        int size = testList.size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
        System.out.println("testGetKaizenList - PASSED");
    }

    @Test
    public void testValidGetKaizen() throws Exception {
        int size = dm.getKaizenList().size();
        assertTrue("DataManager.getKaizenList().size() < 1", 1 <= size);
        Kaizen k = dm.getKaizen(size - 1);
        assertNotNull("DataManager.getKaizen() returned null", k);
        System.out.println("testValidGetKaizen - PASSED");
    }

    @Test
    public void testInvalidGetKaizen() throws Exception {
        int size = dm.getKaizenList().size();
        Kaizen k = dm.getKaizen(size);
        assertNull("DataManager.getKaizen() returned not null", k);
        System.out.println("testInvalidGetKaizen - PASSED");
    }
}