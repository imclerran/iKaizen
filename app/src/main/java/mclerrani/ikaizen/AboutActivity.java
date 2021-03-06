package mclerrani.ikaizen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Activity class for the about (credits) screen
 *
 * @author Ian McLerran
 * @version 2/18/16
 */
public class AboutActivity extends AppCompatActivity {

    /**
     * Android onCreate() method
     * @param savedInstanceState -- the app state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Navigate up in the app
     * @return true if navigation successful
     */
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
