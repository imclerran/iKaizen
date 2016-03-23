package mclerrani.ikaizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

/**
 * Activity class to handle setting user preferences
 *
 * @author Ian McLerran
 * @version 2/18/16
 */
public class SettingsActivity extends AppCompatActivity {

    PreferencesManager pm = PreferencesManager.getInstance(this);
    Switch swcShowOwnerData;
    Switch swcEnableWelcomeMessage;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swcShowOwnerData        = (Switch) findViewById(R.id.swcShowOwnerData);
        swcEnableWelcomeMessage = (Switch) findViewById(R.id.swcEnableWelcomeMessage);

        if(pm.isShowOwnerData())
            swcShowOwnerData.setChecked(true);
        else
            swcShowOwnerData.setChecked(false);

        if(pm.isEnableWelcomeMessage())
            swcEnableWelcomeMessage.setChecked(true);
        else
            swcEnableWelcomeMessage.setChecked(false);
    }

    /**
     * when swcShowOwnerData switch is clicked,
     * toggle settings appropriately
     *
     * @param view -- the view clicked on
     */
    public void swcShowOwnerDataOnClick(View view) {
        if(swcShowOwnerData.isChecked())
            pm.setShowOwnerData(true);
        else
            pm.setShowOwnerData(false);
    }

    /**
     * when swcShowWelcomeMessage switch is clicked,
     * toggle settings appropriately
     *
     * @param view -- the view clicked on
     */
    public void swcShowWelcomeMessageOnClick(View view) {
        if(swcEnableWelcomeMessage.isChecked())
            pm.setEnableWelcomeMessage(true);
        else
            pm.setEnableWelcomeMessage(false);
    }

    /**
     * navigate up in the app
     *
     * @return always true
     */
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /**
     * launch AboutActivity when settings item is clicked
     *
     * @param view
     */
    public void llAboutOnClick(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
