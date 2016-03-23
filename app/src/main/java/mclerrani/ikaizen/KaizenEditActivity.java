package mclerrani.ikaizen;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * An activity class to edit a given kaizen
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class KaizenEditActivity extends AppCompatActivity {
    public final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    public final static int EDIT_KAIZEN_REQUEST = 1;
    public final static int CREATE_KAIZEN_REQUEST = 2;

    private DataManager dm;
    private PreferencesManager pm;
    private Kaizen kaizen;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pm = PreferencesManager.getInstance(getApplicationContext());
        dm = DataManager.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
        }
        else {
            kaizen = new Kaizen();
            dm.insertKaizen(kaizen);
            String owner = getUserName();
            if(null != owner)
                kaizen.setOwner(owner);
        }

        populate(kaizen);
    }

    /**
     * inflate the options menu
     *
     * @param menu the menu to inflate
     * @return success or failure
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

        switch (item.getItemId()) {
            case R.id.action_settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Android lifecycle onResume() method
     * call method to hide/show owner info at this time
     */
    @Override
    protected void onResume() {
        super.onResume();
        hideOwnerData();
    }

    /**
     * hide or show the owner information according to user preferences
     */
    public void hideOwnerData() {
        LinearLayout llOwnerLayout = (LinearLayout)findViewById(R.id.llOwnerLayout);
        LinearLayout llDeptLayout = (LinearLayout)findViewById(R.id.llDeptLayout);

        if(pm.isShowOwnerData()) {
            LinearLayout.LayoutParams paramsShow =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                  LinearLayout.LayoutParams.WRAP_CONTENT);
            llOwnerLayout.setLayoutParams(paramsShow);
            llDeptLayout.setLayoutParams(paramsShow);
        }
        else {
            LinearLayout.LayoutParams paramsHide =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            llOwnerLayout.setLayoutParams(paramsHide);
            llDeptLayout.setLayoutParams(paramsHide);
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
     * populate the layout with Kaizen data
     *
     * @param kaizen the Kaizen to display
     */
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

    /**
     * respond to btnSaveKaizen click events
     *
     * @param view the view clicked on
     */
    public void btnSaveKaizenOnClick(View view) {
        saveKaizen();
    }

    /**
     * save user input to the kaizen object
     */
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

        dm.updateKaizen(kaizen);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * navigate up in the app
     * save user input at this time
     *
     * @return true if navigation successful
     */
    public boolean onSupportNavigateUp() {
        saveKaizen();
        onBackPressed();
        return true;
    }

    /**
     * get the username from device contacts
     *
     * @return the username formatted as "FirstName LastInitial"
     */
    public String getUserName() {

        if(PermissionsManager.canMakeSmores()) {
            PermissionsManager.checkPermissions(Manifest.permission.READ_CONTACTS, this);
        }

        String fullName = null;
        Cursor c = this.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        int count = c.getCount();
        String[] columnNames = c.getColumnNames();
        boolean b = c.moveToFirst();
        int position = c.getPosition();
        if (count == 1 && position == 0) {
            for (int j = 0; j < columnNames.length; j++) {
                String columnName = columnNames[j];
                String columnValue = c.getString(c.getColumnIndex(columnName));

                Log.i("LOG", "columnNames[" + String.valueOf(j) + "] = " + columnName);
                //Use the values
                if(ContactsContract.Profile.DISPLAY_NAME == columnName);
                fullName = columnValue;
                break;
            }
        }
        c.close();

        String firstNLastI = null;
        if(null != fullName) {
            String nameTokens[] = fullName.split("\\s+");

            String firstName = nameTokens[0];
            int nTokens = nameTokens.length;
            String lastName = nameTokens[nTokens - 1];
            String lastInitial = lastName.substring(0, 1);
            firstNLastI = firstName + " " + lastInitial;
        }


        return firstNLastI;
    }
}