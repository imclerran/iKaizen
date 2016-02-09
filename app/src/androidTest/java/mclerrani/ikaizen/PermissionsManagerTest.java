package mclerrani.ikaizen;

import android.os.Build;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

/**
 * Created by Ian on 2/6/2016.
 */
public class PermissionsManagerTest extends AndroidTestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testCanMakeSmores() throws Exception {
        boolean api23plus = PermissionsManager.canMakeSmores();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            assertTrue("PermissionsManager.canMakeSmores() did not return true", api23plus);
        }
        else {
            assertFalse("PermissionsManager.canMakeSmores() did not return false", api23plus);
        }
    }
}