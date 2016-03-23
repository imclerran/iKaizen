package mclerrani.ikaizen;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Activity class for an image gallery
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class ImageGalleryActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 3;
    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    private final static String EXTRA_IMAGE_ID = "mclerrani.ikaizen.IMAGE_ID";

    private ArrayList<ImageFile> imageList;
    private static ImageRecyclerAdapter recAdapter;
    private RecyclerView recImageList;
    private StaggeredGridLayoutManager sglm;
    private DataManager dm = DataManager.getInstance(KaizenRecyclerActivity.getContext());
    private Kaizen kaizen;
    String newPhotoPath = null;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // restore saved instance state
        if(null != savedInstanceState) {
            newPhotoPath = savedInstanceState.getString("newPhotoPath");
        }

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
        }
        else finish();

        // find your recycler view, allow it to scale in size
        recImageList = (RecyclerView) findViewById(R.id.recImageList);
        recImageList.setHasFixedSize(false);

        // create a list, like you would with your datamanager
        // create an instance of your custom recycler adapter
        imageList = kaizen.getImageFiles();
        recAdapter = new ImageRecyclerAdapter(imageList);
        recImageList.setAdapter(recAdapter);

        // the layout manager tells the recycler how to display its children
        int orientation = this.getResources().getConfiguration().orientation;
        if(Configuration.ORIENTATION_PORTRAIT == orientation) {
            sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        if(Configuration.ORIENTATION_LANDSCAPE == orientation) {
            sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        recImageList.setLayoutManager(sglm);


        //----------------------------------------------------------
        // Handling click events:
        // Gesture detector allows the recycler to
        // differentiate between scroll actions, and other events.
        // we use the listener method onSingleTapUp()
        // to determine if the user has clicked an item.
        final GestureDetector gestureDetector =
                new GestureDetector(ImageGalleryActivity.this,
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onSingleTapUp(MotionEvent e) {
                                return true;
                            }
                        });

        // we now add a touch listener to the recycler view
        recImageList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                ImageFile image = recAdapter.getImage(rv.getChildAdapterPosition(child));

                // the onTouchEvent() method will return true if onSingleTapUp() returns true
                if(null != child && gestureDetector.onTouchEvent(e)) {
                    launchImageViewerActivity(image);
                    return true;
                }
                return false;
            }

            // OnItemTouchListener requires that we override these methods, but we do not need them
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
        // End handling click events

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    /**
     * Android lifecycle onSavedInstanceState() method
     *
     * @param outState -- the bundle to save the current application state to
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("newPhotoPath", newPhotoPath);
    }

    /**
     * Android lifecycle onCreateOptionsMenu() method
     *
     * @param menu -- the menu to inflate
     * @return
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
     * @param item -- the menu item selectedk
     * @return success or failure
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
     * launch the ImageViewerActivity to view the selected image
     *
     * @param image -- the image to view
     */
    public void launchImageViewerActivity(ImageFile image) {
        Intent viewImageIntent = new Intent(this, ImageViewerActivity.class);
        viewImageIntent.putExtra(EXTRA_IMAGE_ID, image.getItemID());
        viewImageIntent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivity(viewImageIntent);
    }

    /**
     * use an implicit intent to launch the camera and take a picture
     */
    private void dispatchTakePictureIntent() {
        // if android ver. >= marshmallow, check camera permissions
        if(PermissionsManager.canMakeSmores())
            PermissionsManager.checkPermissions(Manifest.permission.CAMERA, this);

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

    /**
     * create a file in external storage to hold the captured image
     *
     * @return the new file
     * @throws IOException -- throw exception if file cannot be created
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "iKaizen_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString()+"/iKaizen");
        storageDir.mkdirs();

        // if on marshmallow or higher,
        // request WRITE_EXTERNAL_STORAGE permissions
        if(PermissionsManager.canMakeSmores())
            PermissionsManager.checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, this);

        File image = null;

        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }
        catch (IOException ex) {
            Log.i("LOG", ex.getMessage());
            throw ex;
        }

        // Save a file: path for use with ACTION_VIEW intents
        newPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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
        if(REQUEST_IMAGE_CAPTURE == requestCode) {
            if(RESULT_OK == resultCode) {
                if (null != newPhotoPath) {
                    ImageFile image = new ImageFile(newPhotoPath);
                    dm.insertImageFile(image, kaizen);
                    launchImageViewerActivity(image);
                }
            }
            else {
                if(null != newPhotoPath) {
                    ImageFile.deleteFile(newPhotoPath);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    /**
     * allow outside access to the recycler adapter
     *
     * @return a reference to the RecyclerAdapter
     */
    public static ImageRecyclerAdapter getRecyclerAdapter() {
        return recAdapter;
    }
}
