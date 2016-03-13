package mclerrani.ikaizen;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;

/**
 * Created by imcle on 3/1/2016.
 */
public class ImageFile {
    private String path;
    private int itemID;
    private Bitmap thumbnail;
    private float aspectRatio;
    private static int count;

    public ImageFile() {
        path = null;
    }

    public ImageFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public static boolean deleteFile(final String deletePath) {
        String path;
        String prefix = deletePath.substring(0, 5);

        if(prefix.equals("file:")) {
            path = deletePath.substring(5);
        }
        else { path = deletePath; }

        File toDelete = new File(path);
        if(!toDelete.exists()) {
            return false;
        }

        toDelete.delete();
        return true;
    }
}
