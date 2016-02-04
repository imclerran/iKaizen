package mclerrani.ikaizen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ian on 1/28/2016.
 */
public class PreferencesManager {
    private static PreferencesManager pm;
    private final SharedPreferences PREFS;
    private boolean showOwnerData;
    private boolean enableWelcomeMessage;

    public static PreferencesManager getInstance(Context context) {
        if(null == pm)
            pm = new PreferencesManager(context);
        return pm;
    }

    private PreferencesManager(Context context) {
        PREFS = context.getSharedPreferences(
                "mclerrani.ikaizen",
                Context.MODE_PRIVATE);

        showOwnerData = PREFS.getBoolean("showOwnerData", true);
        enableWelcomeMessage = PREFS.getBoolean("enableWelcomeMessage", true);
    }

    public boolean isShowOwnerData() {
        return showOwnerData;
    }

    public void setShowOwnerData(boolean showOwnerData) {
        this.showOwnerData = showOwnerData;
        PREFS.edit().putBoolean("showOwnerData", showOwnerData).apply();
    }

    public boolean isEnableWelcomeMessage() {
        return enableWelcomeMessage;
    }

    public void setEnableWelcomeMessage(boolean enableWelcomeMessage) {
        this.enableWelcomeMessage = enableWelcomeMessage;
        PREFS.edit().putBoolean("enableWelcomeMessage", enableWelcomeMessage).apply();
    }
}
