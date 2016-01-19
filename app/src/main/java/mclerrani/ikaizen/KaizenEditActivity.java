package mclerrani.ikaizen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class KaizenEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Kaizen kaizen = Kaizen.getTestKaizen();
        setHints(kaizen);
    }

    private void setHints(Kaizen kaizen) {
        EditText txtOwner = (EditText)findViewById(R.id.txtOwner);
        txtOwner.setHint(kaizen.getOwner());
        EditText txtDept = (EditText)findViewById(R.id.txtDept);
        txtDept.setHint(kaizen.getDept());
        EditText txtDate = (EditText)findViewById(R.id.txtDate);
        txtDate.setHint(kaizen.getDateCreatedAsString());
        EditText txtProblemStatement = (EditText)findViewById(R.id.txtProblemStatement);
        txtProblemStatement.setHint(kaizen.getProblemStatement());
        EditText txtOverProduction = (EditText)findViewById(R.id.txtOverProduction);
        txtOverProduction.setHint(kaizen.getOverProductionWaste());
        EditText txtTransportation = (EditText)findViewById(R.id.txtTransportation);
        txtTransportation.setHint(kaizen.getTransportationWaste());
        EditText txtMotion = (EditText)findViewById(R.id.txtMotion);
        txtMotion.setHint(kaizen.getMotionWaste());
        EditText txtWaiting = (EditText)findViewById(R.id.txtWaiting);
        txtWaiting.setHint(kaizen.getWaitingWaste());
        EditText txtProcessing = (EditText)findViewById(R.id.txtProcessing);
        txtProcessing.setHint(kaizen.getProcessingWaste());
        EditText txtInventory = (EditText)findViewById(R.id.txtInventory);
        txtInventory.setHint(kaizen.getInventoryWaste());
        EditText txtDefects = (EditText)findViewById(R.id.txtDefects);
        txtDefects.setHint(kaizen.getDefectsWaste());
        EditText txtRootCauses = (EditText)findViewById(R.id.txtRootCauses);
        txtRootCauses.setHint(kaizen.getRootCauses());
        EditText txtTotalWaste = (EditText)findViewById(R.id.txtTotalWaste);
        txtTotalWaste.setHint(String.valueOf(kaizen.getTotalWaste()));
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
