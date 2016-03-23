package mclerrani.ikaizen;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Activity class for viewing a single image
 *
 * @author Ian McLerran
 * @version 2/16/16
 */
public class ImageViewerActivity extends AppCompatActivity {

    private final static String EXTRA_IMAGE_ID = "mclerrani.ikaizen.IMAGE_ID";
    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    DataManager dm;
    String currentPhotoPath;
    ImageView imgViewer;
    Kaizen kaizen;
    ImageFile image;
    boolean imageDeleted = false;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = DataManager.getInstance(getApplicationContext());
        imgViewer = (ImageView) findViewById(R.id.imgViewer);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
            if (null == kaizen) {
                finish();
            }
            if (intent.hasExtra(EXTRA_IMAGE_ID)) {
                image = kaizen.getImage(intent.getIntExtra(EXTRA_IMAGE_ID, -1));
            }
            if (null != image) {
                currentPhotoPath = image.getPath();
            } else finish();
        } else
            finish();
    }

    /**
     * inflate the options menu
     *
     * @param menu -- the menu to inflate
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_viewer, menu);
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
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_delete_image:
                deleteImage();
                return true;
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
     * remove the image from the database and delete it from the filesystem
     */
    public void deleteImage() {
        String path = image.getPath();
        dm.deleteImageFile(image, kaizen);
        kaizen.getImageFiles().remove(image);
        ImageGalleryActivity.getRecyclerAdapter().notifyDataSetChanged();
        if (ImageFile.deleteFile(path)) {
            imageDeleted = true;
        }
        finish();
    }


    /**
     * load the image when the window gains focus
     *
     * @param hasFocus -- window has focus?
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus & !imageDeleted) {
            if (!setPic()) {
                Toast toast = Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        }
    }

    /**
     * load the image from the filesystem and set the ImageView to display it
     *
     * @return success or failure
     */
    private boolean setPic() {
        // Get the dimensions of the View
        int targetW = imgViewer.getWidth();
        int targetH = imgViewer.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        File imgFile = new File(currentPhotoPath);

        Uri uri = Uri.parse(currentPhotoPath);
        FileDescriptor fd = null;
        ParcelFileDescriptor pfd = null;
        try {
            pfd = getContentResolver().openFileDescriptor(uri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        fd = pfd.getFileDescriptor();
        BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


        // Determine how much to scale down the image
        int scaleFactor = Math.max(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; // deprecated

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        imgViewer.setImageBitmap(bitmap);

        try {
            pfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
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
