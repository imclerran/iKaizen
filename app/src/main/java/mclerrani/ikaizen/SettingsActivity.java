package mclerrani.ikaizen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    PreferencesManager pm = PreferencesManager.getInstance(this);
    Switch tglShowOwnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tglShowOwnerData = (Switch)findViewById(R.id.tglShowOwnerData);

        if(pm.getShowOwnerData())
            tglShowOwnerData.setChecked(true);
        else
            tglShowOwnerData.setChecked(false);
    }

    public void tglShowOwnerDataOnClick(View view) {
        if(tglShowOwnerData.isChecked())
            pm.setShowOwnerData(true);
        else
            pm.setShowOwnerData(false);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
