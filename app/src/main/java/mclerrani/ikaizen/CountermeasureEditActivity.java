package mclerrani.ikaizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;

public class CountermeasureEditActivity extends AppCompatActivity {

    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    public final static String EXTRA_COUNTERMEASURE_POSITION = "mclerrani.ikaizen.COUNTERMEASURE_POSITION";

    public final static int EDIT_COUNTERMEASURE_REQUEST = 4;
    public final static int CREATE_COUNTERMEASURE_REQUEST = 5;

    private Kaizen kaizen;
    private Solution solution;
    private Countermeasure cm;
    private DataManager dm = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countermeasure_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
            solution = kaizen.getSolution();

            if(intent.hasExtra(EXTRA_COUNTERMEASURE_POSITION)) {
                int position = intent.getIntExtra(EXTRA_COUNTERMEASURE_POSITION, -1);
                if(-1 != position) {
                    cm = solution.getPossibleCounterMeasures().get(position);
                }
            }
            else {
                cm = new Countermeasure();
                solution.getPossibleCounterMeasures().add(cm);
            }
        }
        else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_countermeasure_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_delete_countermeasure:
                // TODO: implement delete method
                //deleteCountermeasure(cm);
                return true;
            case R.id.action_settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populate();
    }

    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void populate() {
        EditText txtPreventativeAction = (EditText) findViewById(R.id.txtPreventativeAction);
        txtPreventativeAction.setText(cm.getPreventativeAction());
        EditText txtCostToImplement = (EditText) findViewById(R.id.txtCostToImplement);
        String cost = String.format("%.2f", cm.getCostToImplement());
        txtCostToImplement.setText(cost);
        EditText txtDateWalkedOn = (EditText) findViewById(R.id.txtDateWalkedOn);
        txtDateWalkedOn.setText(cm.getDateWalkedOn());
        CheckBox chkCutMuri = (CheckBox) findViewById(R.id.chkCutMuri);
        chkCutMuri.setChecked(cm.isCutMuri());
        CheckBox chkThreeXBetter = (CheckBox) findViewById(R.id.chkThreeXBetter);
        chkThreeXBetter.setChecked(cm.isThreeXBetter());
        CheckBox chkTruePull = (CheckBox) findViewById(R.id.chkTruePull);
        chkTruePull.setChecked(cm.isTruePull());
    }

    public void save() {
        EditText txtPreventativeAction = (EditText) findViewById(R.id.txtPreventativeAction);
        cm.setPreventativeAction(String.valueOf(txtPreventativeAction.getText()));
        EditText txtCostToImplement = (EditText) findViewById(R.id.txtCostToImplement);
        if(!String.valueOf(txtCostToImplement.getText()).equals(""))
            cm.setCostToImplement(Float.parseFloat(String.valueOf(txtCostToImplement.getText())));
        else
            cm.setCostToImplement(0);
        EditText txtDateWalkedOn = (EditText) findViewById(R.id.txtDateWalkedOn);
        cm.setDateWalkedOn(String.valueOf(txtDateWalkedOn.getText()));
        CheckBox chkCutMuri = (CheckBox) findViewById(R.id.chkCutMuri);
        cm.setCutMuri(chkCutMuri.isChecked());
        CheckBox chkThreeXBetter = (CheckBox) findViewById(R.id.chkThreeXBetter);
        cm.setThreeXBetter(chkThreeXBetter.isChecked());
        CheckBox chkTruePull = (CheckBox) findViewById(R.id.chkTruePull);
        cm.setTruePull(chkTruePull.isChecked());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
