package mclerrani.ikaizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class KaizenDetailsActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "mclerrani.ikaizen.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Populate data
        Kaizen kaizen = Kaizen.getTestKaizen();
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
            case R.id.action_delete_kaizen:
                // delete kaizen
                return true;
            case R.id.action_edit_kaizen:
                Intent intent = new Intent(this, KaizenEditActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "message");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
