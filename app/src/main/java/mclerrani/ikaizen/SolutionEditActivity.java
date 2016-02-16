package mclerrani.ikaizen;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SolutionEditActivity extends AppCompatActivity {

    public final static int EDIT_SOLUTION_REQUEST = 3;

    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";

    private DataManager dm = DataManager.getInstance();
    private Kaizen kaizen;
    private Solution solution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    public void populate() {
        EditText txtTodaysFix           = (EditText) findViewById(R.id.txtTodaysFix);
        txtTodaysFix.setText(solution.getTodaysFix());
        EditText txtEstimatedSavings    = (EditText) findViewById(R.id.txtEstimatedSavings);
        String savings = String.format("%.2f", solution.getEstimatedSavings());
        txtEstimatedSavings.setText(savings);
        TextView lblImprovementsData    = (TextView) findViewById(R.id.lblImprovementsData);
        TextView lblDateSolvedData      = (TextView) findViewById(R.id.lblDateSolvedData);
        EditText txtSignedOffBy         = (EditText) findViewById(R.id.txtSignedOffBy);
        ImageButton btnFeelsEmoji       = (ImageButton) findViewById(R.id.btnFeelsEmoji);
        btnFeelsEmoji.setImageResource(solution.getSolvedEmote());
    }

    public void save() {
        EditText txtTodaysFix           = (EditText) findViewById(R.id.txtTodaysFix);
        solution.setTodaysFix(String.valueOf(txtTodaysFix.getText()));
        EditText txtEstimatedSavings    = (EditText) findViewById(R.id.txtEstimatedSavings);
        solution.setEstimatedSavings(Float.parseFloat(String.valueOf(txtEstimatedSavings.getText())));
        EditText txtSignedOffBy         = (EditText) findViewById(R.id.txtSignedOffBy);
        solution.setSignedOffBy(String.valueOf(txtSignedOffBy.getText()));
    }

    public void btnFeelsEmojiOnClick(View view) {
        Intent intent = new Intent(this, EmojiPickerActivity.class);
        startActivityForResult(intent, EmojiPickerActivity.REQUEST_EMOJI_ID);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

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

                ImageButton btnEmojiFeels = (ImageButton) findViewById(R.id.btnFeelsEmoji);
                if(-1 != emojiId) {
                    solution.setSolvedEmote(emojiId);
                    btnEmojiFeels.setImageResource(emojiId);
                    //btnEmojiFeels.setImageDrawable(getResources().getDrawable(emojiId));
                }
            }
        }

    }
}
