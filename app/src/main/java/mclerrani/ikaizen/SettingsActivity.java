package mclerrani.ikaizen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void tglDisplayUserInfoOnClick(View view) {
        Switch tglDisplayUserInfo = (Switch)findViewById(R.id.tglDisplayUserInfo);

        if(tglDisplayUserInfo.isChecked())
            ;
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
