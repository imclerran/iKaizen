package mclerrani.ikaizen;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Activity class for displaying the full details of a Kaizen
 *
 * @author Ian McLerran
 * @version 3/14/16
 */
public class KaizenDetailsActivity extends AppCompatActivity {

    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";

    private PreferencesManager pm = PreferencesManager.getInstance(KaizenRecyclerActivity.getContext());
    private DataManager dm = DataManager.getInstance(KaizenRecyclerActivity.getContext());
    private Kaizen kaizen = null;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // check if intent contains EXTRA_KAIZEN_ID
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
        }
        // if failed load a Kaizen, finish the activity
        if (null == kaizen) {
            finish();
        }

        populate(kaizen);
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
        LinearLayout llOwnerLayout = (LinearLayout) findViewById(R.id.llOwnerLayout);
        LinearLayout llDeptLayout = (LinearLayout) findViewById(R.id.llDeptLayout);

        if (pm.isShowOwnerData()) {
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
     * populate the layout with Kaizen data
     *
     * @param kaizen the Kaizen to display
     */
    private void populate(Kaizen kaizen) {
        TextView lblOwner = (TextView) findViewById(R.id.lblOwnerData);
        lblOwner.setText(kaizen.getOwner());
        TextView lblDept = (TextView) findViewById(R.id.lblDepartmentData);
        lblDept.setText(kaizen.getDept());
        TextView lblDate = (TextView) findViewById(R.id.lblDateData);
        lblDate.setText(kaizen.getDateCreatedAsString());
        TextView lblProblemStatement = (TextView) findViewById(R.id.lblProblemStatementData);
        lblProblemStatement.setText(kaizen.getProblemStatement());
        TextView lblOverProduction = (TextView) findViewById(R.id.lblOverProductionData);
        lblOverProduction.setText(kaizen.getOverProductionWaste());
        TextView lblTransportation = (TextView) findViewById(R.id.lblTransportationData);
        lblTransportation.setText(kaizen.getTransportationWaste());
        TextView lblMotion = (TextView) findViewById(R.id.lblMotionData);
        lblMotion.setText(kaizen.getMotionWaste());
        TextView lblWaiting = (TextView) findViewById(R.id.lblWaitingData);
        lblWaiting.setText(kaizen.getWaitingWaste());
        TextView lblProcessing = (TextView) findViewById(R.id.lblProcessingData);
        lblProcessing.setText(kaizen.getProcessingWaste());
        TextView lblInventory = (TextView) findViewById(R.id.lblInventoryData);
        lblInventory.setText(kaizen.getInventoryWaste());
        TextView lblDefects = (TextView) findViewById(R.id.lblDefectsData);
        lblDefects.setText(kaizen.getDefectsWaste());
        TextView lblRootCauses = (TextView) findViewById(R.id.lblRootCausesData);
        lblRootCauses.setText(kaizen.getRootCauses());
        TextView lblTotalWaste = (TextView) findViewById(R.id.lblTotalWasteData);
        lblTotalWaste.setText(String.valueOf(kaizen.getTotalWaste()));
    }

    /**
     * inflate the options menu
     *
     * @param menu -- the menu to inflate
     * @return success or failure
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kaizen_details, menu);
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
            case R.id.action_edit_kaizen:
                editKaizen();
                return true;
            case R.id.action_delete_kaizen:
                deleteKaizen();
                return true;
            case R.id.action_settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * launch the EditKaizenActivity and pass it the current Kaizen id via intent extra
     */
    public void editKaizen() {
        Intent intent = new Intent(this, KaizenEditActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivityForResult(intent, KaizenEditActivity.EDIT_KAIZEN_REQUEST);
    }

    /**
     * flag the current kaizen for deletion
     */
    public void deleteKaizen() {
        kaizen.setDeleteMe(true);
        finish();
    }

    /**
     * launch the settings activity
     */
    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * handle completed activity requests
     *
     * @param requestCode -- the request that has been completed
     * @param resultCode -- the result of the request
     * @param data -- any data returned by the request
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == KaizenEditActivity.EDIT_KAIZEN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(EXTRA_KAIZEN_ID)) {
                    kaizen = dm.getKaizen(data.getIntExtra(EXTRA_KAIZEN_ID, -1));
                }
                populate(kaizen);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * respond to btnImages click events
     *
     * @param view -- the view clicked on
     */
    public void btnImagesOnClick(View view) {
        launchImageGalleryActivity();
    }

    /**
     * launch the ImageGalleryActivity
     */
    public void launchImageGalleryActivity() {
        Intent imageGalleryIntent = new Intent(this, ImageGalleryActivity.class);
        imageGalleryIntent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivity(imageGalleryIntent);
    }

    /**
     * respond to btnSolutions click events
     *
     * @param view the view clicked on
     */
    public void btnSolutionsOnClick(View view) {
        launchSolutionOverviewviewActivity();
    }

    /**
     * launch SolutionOverviewTabbedActivity and pass it the current kaizen using an intent extra
     */
    public void launchSolutionOverviewviewActivity() {
        Intent intent = new Intent(this, SolutionOverviewTabbedActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivity(intent);
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
