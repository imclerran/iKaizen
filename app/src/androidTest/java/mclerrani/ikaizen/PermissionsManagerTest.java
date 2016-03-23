package mclerrani.ikaizen;

import android.os.Build;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

/**
 * Test the PermissionsManager class
 *
 * @author Ian McLerran
 * @version 2/8/16
 */
public class PermissionsManagerTest extends AndroidTestCase {

    /**
     * do necessary initialization before running tests
     *
     * @throws Exception
     */
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * test canMakeSmores() method
     *
     * @throws Exception
     */
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