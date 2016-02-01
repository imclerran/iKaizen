package mclerrani.ikaizen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class ImageViewerActivity extends AppCompatActivity {

    private final static String EXTRA_FILE_PATH = "mclerrani.ikaizen.FILE_PATH";
    String currentPhotoPath;
    ImageView imgViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgViewer = (ImageView) findViewById(R.id.imgViewer);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_FILE_PATH)) {
            currentPhotoPath = intent.getStringExtra(EXTRA_FILE_PATH);
            Log.i("LOG", "PHOTO PATH = " + currentPhotoPath);
        }
        else
            finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPic();
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgViewer.getWidth();
        int targetH = imgViewer.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        // crashes here ^ "divide by zero"

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imgViewer.setImageBitmap(bitmap);
    }

}
