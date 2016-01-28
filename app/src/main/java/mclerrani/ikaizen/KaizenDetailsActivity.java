package mclerrani.ikaizen;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class KaizenDetailsActivity extends AppCompatActivity {
    public final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";

    private DataManager dataManager = DataManager.getDataManager();
    private Kaizen kaizen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // check if intent contains EXTRA_KAIZEN
        // if it does, deserialize
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dataManager.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
        }
        // if intent does not contain EXTRA_KAIZEN
        // get the test kaizen
        else {
            if(null == kaizen) {
                kaizen = Kaizen.getTestKaizen();
                dataManager.getKaizenList().add(kaizen);
            }
        }

        populate(kaizen);
    }



    // populate layout with kaizen data
    private void populate(Kaizen kaizen) {
        EditText txtOwner = (EditText)findViewById(R.id.txtOwner);
        txtOwner.setText(kaizen.getOwner());
        EditText txtDept = (EditText)findViewById(R.id.txtDept);
        txtDept.setText(kaizen.getDept());
        EditText txtDate = (EditText)findViewById(R.id.txtDate);
        txtDate.setText(kaizen.getDateCreatedAsString());
        EditText txtProblemStatement = (EditText)findViewById(R.id.txtProblemStatement);
        txtProblemStatement.setText(kaizen.getProblemStatement());
        EditText txtOverProduction = (EditText)findViewById(R.id.txtOverProduction);
        txtOverProduction.setText(kaizen.getOverProductionWaste());
        EditText txtTransportation = (EditText)findViewById(R.id.txtTransportation);
        txtTransportation.setText(kaizen.getTransportationWaste());
        EditText txtMotion = (EditText)findViewById(R.id.txtMotion);
        txtMotion.setText(kaizen.getMotionWaste());
        EditText txtWaiting = (EditText)findViewById(R.id.txtWaiting);
        txtWaiting.setText(kaizen.getWaitingWaste());
        EditText txtProcessing = (EditText)findViewById(R.id.txtProcessing);
        txtProcessing.setText(kaizen.getProcessingWaste());
        EditText txtInventory = (EditText)findViewById(R.id.txtInventory);
        txtInventory.setText(kaizen.getInventoryWaste());
        EditText txtDefects = (EditText)findViewById(R.id.txtDefects);
        txtDefects.setText(kaizen.getDefectsWaste());
        EditText txtRootCauses = (EditText)findViewById(R.id.txtRootCauses);
        txtRootCauses.setText(kaizen.getRootCauses());
        EditText txtTotalWaste = (EditText)findViewById(R.id.txtTotalWaste);
        txtTotalWaste.setText(String.valueOf(kaizen.getTotalWaste()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kaizen_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_edit_kaizen:
                editKaizen();
                return true;
            case R.id.action_delete_kaizen:
                deleteKaizen(kaizen);
                return true;
            case R.id.action_settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void editKaizen() {
        Intent intent = new Intent(this, KaizenEditActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivityForResult(intent, KaizenEditActivity.EDIT_KAIZEN_REQUEST);
    }

    public void deleteKaizen(Kaizen k) {
        k.setDeleteMe(true);
        finish();
    }

    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == KaizenEditActivity.EDIT_KAIZEN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if(data.hasExtra(EXTRA_KAIZEN_ID)) {
                    kaizen = dataManager.getKaizen(data.getIntExtra(EXTRA_KAIZEN_ID, -1));
                }
                populate(kaizen);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
