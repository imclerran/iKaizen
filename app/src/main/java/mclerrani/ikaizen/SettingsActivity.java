package mclerrani.ikaizen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    PreferencesManager pm = PreferencesManager.getInstance(this);
    Switch swcShowOwnerData;
    Switch swcShowWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swcShowOwnerData        = (Switch) findViewById(R.id.swcShowOwnerData);
        swcShowWelcomeMessage   = (Switch) findViewById(R.id.swcShowWelcomeMessage);

        if(pm.isShowOwnerData())
            swcShowOwnerData.setChecked(true);
        else
            swcShowOwnerData.setChecked(false);

        if(pm.isShowWelcomeMessage())
            swcShowWelcomeMessage.setChecked(true);
        else
            swcShowWelcomeMessage.setChecked(false);
    }

    public void swcShowOwnerDataOnClick(View view) {
        if(swcShowOwnerData.isChecked())
            pm.setShowOwnerData(true);
        else
            pm.setShowOwnerData(false);
    }

    public void swcShowWelcomeMessageOnClick(View view) {
        if(swcShowWelcomeMessage.isChecked())
            pm.setShowWelcomeMessage(true);
        else
            pm.setShowWelcomeMessage(false);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
