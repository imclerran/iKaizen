package mclerrani.ikaizen;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

/**
 * Activity class to allow user input of Solution data
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class SolutionEditActivity extends AppCompatActivity {

    public final static int EDIT_SOLUTION_REQUEST = 3;
    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";

    private DataManager dm;
    private Kaizen kaizen;
    private Solution solution;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = DataManager.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
            solution = kaizen.getSolution();
        }
        else {
            finish();
        }

        populate();
    }

    /**
     * inflate the opstions menu
     *
     * @param menu -- the menu to inflate
     * @return the inflated menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * respond to the user selection from the options menu
     *
     * @param item -- the item selected from the options menu
     * @return success or failure
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * launch the settings activity
     */
    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * populate the layout with Solution data
     */
    public void populate() {
        EditText txtTodaysFix           = (EditText) findViewById(R.id.txtTodaysFix);
        txtTodaysFix.setText(solution.getTodaysFix());
        EditText txtEstimatedSavings    = (EditText) findViewById(R.id.txtEstimatedSavings);
        String savings = String.format("%.2f", solution.getEstimatedSavings());
        txtEstimatedSavings.setText(savings);
        TextView lblImprovementsData    = (TextView) findViewById(R.id.lblImprovementsData);
        lblImprovementsData.setText(solution.getImprovements());
        TextView txtDateSolved      = (TextView) findViewById(R.id.txtDateSolved);
        txtDateSolved.setText(solution.getDateSolvedAsString());
        EditText txtSignedOffBy         = (EditText) findViewById(R.id.txtSignedOffBy);
        txtSignedOffBy.setText(solution.getSignedOffBy());
        ImageButton btnFeelsEmoji       = (ImageButton) findViewById(R.id.btnFeelsEmoji);
        btnFeelsEmoji.setImageResource(solution.getSolvedEmote());
    }

    /**
     * save user input to the Solution object
     */
    public void save() {
        EditText txtTodaysFix           = (EditText) findViewById(R.id.txtTodaysFix);
        solution.setTodaysFix(String.valueOf(txtTodaysFix.getText()));
        EditText txtEstimatedSavings    = (EditText) findViewById(R.id.txtEstimatedSavings);
        solution.setEstimatedSavings(Float.parseFloat(String.valueOf(txtEstimatedSavings.getText())));
        EditText txtDateSolved          = (EditText) findViewById(R.id.txtDateSolved);
        String dateSolvedString = String.valueOf(txtDateSolved.getText());
        DateTime dateSolved = extractDateFromString(dateSolvedString);
        if(null == dateSolved && !dateSolvedString.contains("N/A") && 0 != dateSolvedString.length())
            Toast.makeText(this, "Could not interpret Date Solved -- INVALID FORMAT", Toast.LENGTH_SHORT).show();
        else
            solution.setDateSolved(dateSolved);
        EditText txtSignedOffBy         = (EditText) findViewById(R.id.txtSignedOffBy);
        solution.setSignedOffBy(String.valueOf(txtSignedOffBy.getText()));

        dm.updateSolution(solution, kaizen);
    }

    /**
     * convert a date stored as a string into a DateTime object
     *
     * @param dateStr the string containing date information
     * @return a new DateTime object
     */
    private DateTime extractDateFromString(final String dateStr) {
        // extract month
        String tempStr = dateStr;
        int month;
        int md = tempStr.indexOf('/');
        if(-1 == md) return null;
        String monthStr = tempStr.substring(0, md);
        if(0 == monthStr.length() || 2 < monthStr.length()) return null;
        try {
            month = Integer.parseInt(monthStr);
        } catch (Exception e) {
            Log.i("ERROR", "SolutionEditActivity: Could not parse monthStr");
            return null;
        }

        // extract day
        tempStr = dateStr.substring(md+1);
        int day;
        int dy = tempStr.indexOf('/');
        if(-1 == dy) return null;
        String dayStr = tempStr.substring(0, dy);
        if(0 == dayStr.length() || 2 < dayStr.length()) return null;
        try {
            day = Integer.parseInt(dayStr);
        } catch (Exception e){
            Log.i("ERROR", "SolutionEditActivity: Could not parse dayStr");
            return null;
        }

        // extract year
        int year;
        String yearStr = tempStr.substring(dy+1);
        if(2 == yearStr.length())
            yearStr = "20" + yearStr;
        if(4 != yearStr.length()) return null;
        try {
            year = Integer.parseInt(yearStr);
        } catch (Exception e) {
            Log.i("ERROR", "SolutionEditActivity: Could not parse yearStr");
            return null;
        }

        return new DateTime(year, month, day, 0, 0);
    }

    /**
     * when the user clicks the emoji button, launch the emoji picker activity
     *
     * @param view -- the view clicked on
     */
    public void btnFeelsEmojiOnClick(View view) {
        Intent intent = new Intent(this, EmojiPickerActivity.class);
        startActivityForResult(intent, EmojiPickerActivity.REQUEST_EMOJI_ID);
    }

    /**
     * handle completed activity requests
     *
     * @param requestCode -- the request which completed
     * @param resultCode -- success or failure of the request
     * @param data -- any data returned by the requested activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == EmojiPickerActivity.REQUEST_EMOJI_ID) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int emojiId = -1;
                if(data.hasExtra(EmojiPickerActivity.RESULT_EMOJI_ID)) {
                    emojiId = (int)data.getLongExtra(EmojiPickerActivity.RESULT_EMOJI_ID, -1);
                }

                // if an emoji was chosen, store it in the solution, and show it on the button
                ImageButton btnEmojiFeels = (ImageButton) findViewById(R.id.btnFeelsEmoji);
                if(-1 != emojiId) {
                    solution.setSolvedEmote(emojiId);
                    btnEmojiFeels.setImageResource(emojiId);
                }
            }
        }
    }

    /**
     * navigate up in the app
     * save user input at this time
     *
     * @return always true
     */
    public boolean onSupportNavigateUp() {
        onBackPressed();
        save();
        return true;
    }
}
