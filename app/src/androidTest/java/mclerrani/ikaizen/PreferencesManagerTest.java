package mclerrani.ikaizen;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.TestCase;

/**
 * Created by Ian on 2/3/2016.
 */
public class PreferencesManagerTest extends AndroidTestCase {

    private PreferencesManager pm;
    private SharedPreferences PREFS;

    public void setUp() throws Exception {
        super.setUp();
        pm = PreferencesManager.getInstance(getContext());

        PREFS = getContext().getSharedPreferences(
                "mclerrani.ikaizen",
                Context.MODE_PRIVATE);

    }

    public void testSetGetShowOwnerData() throws Exception {
        pm.setShowOwnerData(false);
        assertFalse("isShowOwnerData() did not return false", pm.isShowOwnerData());
        assertFalse("PREFS => showOwnerData != false", PREFS.getBoolean("showOwnerData", true));
        Log.i("LOG", "testSetGetShowOwnerData() -- PASSED");
    }

    public void testSetGetEnableWelcomeMessage() throws Exception {
        pm.setEnableWelcomeMessage(false);
        assertFalse("isEnableWelcomeMessage() did not return false", pm.isEnableWelcomeMessage());
        assertFalse("PREFS => enableWelcomeMessage != false", PREFS.getBoolean("enableWelcomeMessage", true));
        Log.i("LOG", "testSetGetEnableWelcomeMessage() -- PASSED");
    }
}