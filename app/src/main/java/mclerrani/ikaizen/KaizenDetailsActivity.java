package mclerrani.ikaizen;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KaizenDetailsActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    private final static String EXTRA_FILE_PATH = "mclerrani.ikaizen.FILE_PATH";

    private PreferencesManager pm = PreferencesManager.getInstance(KaizenListActivity.getContext());
    private DataManager dm = DataManager.getInstance();
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


        if(pm.getShowOwnerData()) {

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
                    kaizen = dm.getKaizen(data.getIntExtra(EXTRA_KAIZEN_ID, -1));
                }
                populate(kaizen);
            }
        }

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if(null != currentPhotoPath) {
                Intent viewImageIntent = new Intent(this, ImageViewerActivity.class);
                viewImageIntent.putExtra(EXTRA_FILE_PATH, currentPhotoPath);
                startActivity(viewImageIntent);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnImagesOnClick(View view) {
        if(null == currentPhotoPath) {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        if(canMakeSmores())
            checkPermissions(Manifest.permission.CAMERA);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("LOG", "createImageFile() failed");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "iKaizen_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        // if on marshmallow or higher,
        // request WRITE_EXTERNAL_STORAGE permissions
        if(canMakeSmores())
            checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        File image = null;
        //-----------------------------------------
        // Code breaks at this line
        //-----------------------------------------
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            //-----------------------------------------
        }
        catch (IOException ex) {
            Log.i("LOG", ex.getMessage());
            throw ex;
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void checkPermissions(String permission) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    // check if android version is >= marshmallow
    private boolean canMakeSmores() {
        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
