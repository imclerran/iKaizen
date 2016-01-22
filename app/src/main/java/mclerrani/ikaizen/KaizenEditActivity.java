package mclerrani.ikaizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class KaizenEditActivity extends AppCompatActivity {
    public final static String EXTRA_KAIZEN = "mclerrani.ikaizen.KAIZEN";
    public final static int EDIT_KAIZEN_REQUEST = 1;
    private Kaizen kaizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN)) {
            String extra = intent.getStringExtra(EXTRA_KAIZEN);
            if(extra != "") {
                kaizen = Kaizen.fromJson(extra);
            }
            else
                kaizen = new Kaizen();
        }
        else {
            kaizen = new Kaizen();

            //setHints(kaizen);
        }
        populate(kaizen);
    }

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

    public void btnSaveKaizenOnClick(View view) {
        saveKaizen();
    }

    private void saveKaizen() {
        // get user inputs and store in Kaizen object
        EditText txtOwner = (EditText)findViewById(R.id.txtOwner);
        kaizen.setOwner(String.valueOf(txtOwner.getText()));
        EditText txtDept = (EditText)findViewById(R.id.txtDept);
        kaizen.setDept(String.valueOf(txtDept.getText()));
        kaizen.updateDateModified();
        EditText txtProblemStatement = (EditText)findViewById(R.id.txtProblemStatement);
        kaizen.setProblemStatement(String.valueOf(txtProblemStatement.getText()));
        EditText txtOverProduction = (EditText)findViewById(R.id.txtOverProduction);
        kaizen.setOverProductionWaste(String.valueOf(txtOverProduction.getText()));
        EditText txtTransportation = (EditText)findViewById(R.id.txtTransportation);
        kaizen.setTransportationWaste(String.valueOf(txtTransportation.getText()));
        EditText txtMotion = (EditText)findViewById(R.id.txtMotion);
        kaizen.setMotionWaste(String.valueOf(txtMotion.getText()));
        EditText txtWaiting = (EditText)findViewById(R.id.txtWaiting);
        kaizen.setWaitingWaste(String.valueOf(txtWaiting.getText()));
        EditText txtProcessing = (EditText)findViewById(R.id.txtProcessing);
        kaizen.setProcessingWaste(String.valueOf(txtProcessing.getText()));
        EditText txtInventory = (EditText)findViewById(R.id.txtInventory);
        kaizen.setInventoryWaste(String.valueOf(txtInventory.getText()));
        EditText txtDefects = (EditText)findViewById(R.id.txtDefects);
        kaizen.setDefectsWaste(String.valueOf(txtDefects.getText()));
        EditText txtRootCauses = (EditText)findViewById(R.id.txtRootCauses);
        kaizen.setRootCauses(String.valueOf(txtRootCauses.getText()));
        EditText txtTotalWaste = (EditText)findViewById(R.id.txtTotalWaste);
        if(!String.valueOf(txtTotalWaste.getText()).equals(""))
            kaizen.setTotalWaste(Integer.parseInt(String.valueOf(txtTotalWaste.getText())));
        else
            kaizen.setTotalWaste(0);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_KAIZEN, kaizen.toJson());
        setResult(RESULT_OK, intent);
        finish();
    }

    public boolean onSupportNavigateUp() {
        saveKaizen();
        onBackPressed();
        return true;
    }
}
