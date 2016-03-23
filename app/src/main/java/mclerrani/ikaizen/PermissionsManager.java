package mclerrani.ikaizen;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * A class for handling permission requests on Android 6.0 and above
 *
 * @author Ian McLerran
 * @version 2/8/16
 */
public class PermissionsManager {

    private static final int MY_PERMISSIONS_REQUEST = 1;

    /**
     * request a permission if not already granted
     *
     * @param permission -- the requested permission
     * @param activity -- the activity requesting the permission
     */
    public static void checkPermissions(String permission, Activity activity) {
        // check if the app already has the requested permission
        if (ContextCompat.checkSelfPermission(activity.getBaseContext(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {
            }
            else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        MY_PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                // TODO: add the callback.
                // no callback is probable cause of crash on first permission request
            }
        }
    }

    /**
     * check whether android version is marshmallow or higher
     *
     * @return true if android version is strictly greater than 5.1.x
     */
    public static boolean canMakeSmores() {
        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
}
