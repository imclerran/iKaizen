package mclerrani.ikaizen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * A singleton class for managing user app preferences
 *
 * @author Ian McLerran
 * @version 2/4/16
 */
public class PreferencesManager {
    private static PreferencesManager pm;
    private final SharedPreferences PREFS;
    private boolean showOwnerData;
    private boolean enableWelcomeMessage;

    /**
     * create a new PreferencesManager if it has not yet been instantiated
     * return a reference to the PreferencesManager
     *
     * @param context -- the application context
     * @return a reference to the PreferencesManager
     */
    public static PreferencesManager getInstance(Context context) {
        if(null == pm)
            pm = new PreferencesManager(context);
        return pm;
    }

    /**
     * constructor
     *
     * @param context -- the application context
     */
    private PreferencesManager(Context context) {
        PREFS = context.getSharedPreferences(
                "mclerrani.ikaizen",
                Context.MODE_PRIVATE);

        showOwnerData = PREFS.getBoolean("showOwnerData", true);
        enableWelcomeMessage = PREFS.getBoolean("enableWelcomeMessage", true);
    }

    // getters and setters:

    public boolean isShowOwnerData() { return showOwnerData; }

    public void setShowOwnerData(boolean showOwnerData) {
        this.showOwnerData = showOwnerData;
        // Store the new value in the SharedPreferences file
        PREFS.edit().putBoolean("showOwnerData", showOwnerData).apply();
    }

    public boolean isEnableWelcomeMessage() { return enableWelcomeMessage; }

    public void setEnableWelcomeMessage(boolean enableWelcomeMessage) {
        this.enableWelcomeMessage = enableWelcomeMessage;
        // Store the new value in the SharedPreferences file
        PREFS.edit().putBoolean("enableWelcomeMessage", enableWelcomeMessage).apply();
    }
}
