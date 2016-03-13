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

public class KaizenDetailsActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    //private final static String EXTRA_FILE_PATH = "mclerrani.ikaizen.FILE_PATH";

    //private PreferencesManager pm = PreferencesManager.getInstance(KaizenListActivity.getContext());
    private PreferencesManager pm = PreferencesManager.getInstance(KaizenRecyclerActivity.getContext());
    private DataManager dm = DataManager.getInstance(KaizenRecyclerActivity.getContext());
    private Kaizen kaizen = null;
    String currentPhotoPath = null;

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
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
        }
        // if intent does not contain EXTRA_KAIZEN
        // get the test kaizen
        else {
            if(null == kaizen) {
                kaizen = Kaizen.getTestKaizen();
                dm.getKaizenList().add(kaizen);
            }
        }

        populate(kaizen);


    }

    @Override
    protected void onResume() {
        super.onResume();
        hideOwnerData();
    }

    public void hideOwnerData() {
        LinearLayout llOwnerLayout = (LinearLayout)findViewById(R.id.llOwnerLayout);
        LinearLayout llDeptLayout = (LinearLayout)findViewById(R.id.llDeptLayout);

        LinearLayout.LayoutParams paramsShow =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsHide =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);


        if(pm.isShowOwnerData()) {

            llOwnerLayout.setLayoutParams(paramsShow);
            llDeptLayout.setLayoutParams(paramsShow);
        }
        else {
            llOwnerLayout.setLayoutParams(paramsHide);
            llDeptLayout.setLayoutParams(paramsHide);
        }
    }



    // populate layout with kaizen data
    private void populate(Kaizen kaizen) {
        TextView lblOwner = (TextView)findViewById(R.id.lblOwnerData);
        lblOwner.setText(kaizen.getOwner());
        TextView lblDept = (TextView)findViewById(R.id.lblDepartmentData);
        lblDept.setText(kaizen.getDept());
        TextView lblDate = (TextView)findViewById(R.id.lblDateData);
        lblDate.setText(kaizen.getDateCreatedAsString());
        TextView lblProblemStatement = (TextView)findViewById(R.id.lblProblemStatementData);
        lblProblemStatement.setText(kaizen.getProblemStatement());
        TextView lblOverProduction = (TextView)findViewById(R.id.lblOverProductionData);
        lblOverProduction.setText(kaizen.getOverProductionWaste());
        TextView lblTransportation = (TextView)findViewById(R.id.lblTransportationData);
        lblTransportation.setText(kaizen.getTransportationWaste());
        TextView lblMotion = (TextView)findViewById(R.id.lblMotionData);
        lblMotion.setText(kaizen.getMotionWaste());
        TextView lblWaiting = (TextView)findViewById(R.id.lblWaitingData);
        lblWaiting.setText(kaizen.getWaitingWaste());
        TextView lblProcessing = (TextView)findViewById(R.id.lblProcessingData);
        lblProcessing.setText(kaizen.getProcessingWaste());
        TextView lblInventory = (TextView)findViewById(R.id.lblInventoryData);
        lblInventory.setText(kaizen.getInventoryWaste());
        TextView lblDefects = (TextView)findViewById(R.id.lblDefectsData);
        lblDefects.setText(kaizen.getDefectsWaste());
        TextView lblRootCauses = (TextView)findViewById(R.id.lblRootCausesData);
        lblRootCauses.setText(kaizen.getRootCauses());
        TextView lblTotalWaste = (TextView)findViewById(R.id.lblTotalWasteData);
        lblTotalWaste.setText(String.valueOf(kaizen.getTotalWaste()));
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
                    kaizen = dm.getKaizen(data.getIntExtra(EXTRA_KAIZEN_ID, -1));
                }
                populate(kaizen);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnImagesOnClick(View view) {
        launchImageGalleryActivity();
    }

    public void launchImageGalleryActivity() {
        Intent imageGalleryIntent = new Intent(this, ImageGalleryActivity.class);
        //Intent imageGalleryIntent = new Intent(this, ImageViewerActivity.class);
        imageGalleryIntent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivity(imageGalleryIntent);
    }

    public void btnSolutionsOnClick(View view) {
        Intent intent = new Intent(this, SolutionOverviewTabbedActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivity(intent);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
