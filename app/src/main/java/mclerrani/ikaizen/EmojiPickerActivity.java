package mclerrani.ikaizen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Activity class to handle an emoji request
 *
 * @author Ian McLerran
 * @version 2/16/16
 */
public class EmojiPickerActivity extends AppCompatActivity {

    static final String RESULT_EMOJI_ID = "emojiId";
    static final int REQUEST_EMOJI_ID = 6;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * navigate up in the app
     *
     * @return true if navigation successful
     */
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
