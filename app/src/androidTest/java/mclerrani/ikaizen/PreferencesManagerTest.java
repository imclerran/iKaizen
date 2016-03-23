package mclerrani.ikaizen;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.TestCase;

/**
 * Test the PreferencesManager class
 *
 * @author Ian McLerran
 * @version 2/4/16.
 */
public class PreferencesManagerTest extends AndroidTestCase {

    private PreferencesManager pm;
    private SharedPreferences PREFS;

    /**
     * do initialization before tests
     *
     * @throws Exception
     */
    public void setUp() throws Exception {
        super.setUp();
        pm = PreferencesManager.getInstance(getContext());

        PREFS = getContext().getSharedPreferences(
                "mclerrani.ikaizen",
                Context.MODE_PRIVATE);

    }

    /**
     * test the setShowOwnerData() and getShowOwnerData() methods
     *
     * @throws Exception
     */
    public void testSetGetShowOwnerData() throws Exception {
        pm.setShowOwnerData(false);
        assertFalse("isShowOwnerData() did not return false", pm.isShowOwnerData());
        assertFalse("PREFS => showOwnerData != false", PREFS.getBoolean("showOwnerData", true));
        Log.i("LOG", "testSetGetShowOwnerData() -- PASSED");
    }

    /**
     * test the setEnableWelcomeMessage() and getEnableWelcomeMessage() methods
     *
     * @throws Exception
     */
    public void testSetGetEnableWelcomeMessage() throws Exception {
        pm.setEnableWelcomeMessage(false);
        assertFalse("isEnableWelcomeMessage() did not return false", pm.isEnableWelcomeMessage());
        assertFalse("PREFS => enableWelcomeMessage != false", PREFS.getBoolean("enableWelcomeMessage", true));
        Log.i("LOG", "testSetGetEnableWelcomeMessage() -- PASSED");
    }
}