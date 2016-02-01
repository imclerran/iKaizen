package mclerrani.ikaizen;

import android.content.Intent;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageViewerActivity extends AppCompatActivity {

    private final static String EXTRA_FILE_PATH = "mclerrani.ikaizen.FILE_PATH";
    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    DataManager dm = DataManager.getInstance();
    String currentPhotoPath;
    ImageView imgViewer;
    Kaizen kaizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgViewer = (ImageView) findViewById(R.id.imgViewer);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
            if(null == kaizen) {
                finish();
            }
            currentPhotoPath = kaizen.getImageFiles().get(kaizen.getImageFiles().size()-1);
            Log.i("LOG", "PHOTO PATH = " + currentPhotoPath);
        }
        else
            finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!setPic()) {
            Toast toast = Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

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
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        fd = pfd.getFileDescriptor();
        BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; // deprecated

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        imgViewer.setImageBitmap(bitmap);

        try {
            pfd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
