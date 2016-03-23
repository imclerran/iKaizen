package mclerrani.ikaizen;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;

/**
 * Data class to used to store information about an image file in the filesystem
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class ImageFile {

    // meta data:
    private int itemID;
    private boolean deleteMe;

    // primary data:
    private String path;
    private Bitmap thumbnail;
    private float aspectRatio;

    /**
     * default constructor
     */
    public ImageFile() {
        path = null;
        deleteMe = false;
    }

    /**
     * constructor
     *
     * @param path -- the path of the image stored in the filesystem
     */
    public ImageFile(String path) {
        this.path = path;
        deleteMe = false;
    }

    // getters and setters:

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public Bitmap getThumbnail() { return thumbnail; }
    public void setThumbnail(Bitmap thumbnail) { this.thumbnail = thumbnail; }

    public float getAspectRatio() { return aspectRatio; }
    public void setAspectRatio(float aspectRatio) { this.aspectRatio = aspectRatio; }

    public boolean isDeleteMe() { return isDeleteMe(); }
    public void setDeleteMe(boolean deleteMe) { this.deleteMe = deleteMe; }

    /**
     * delete a file in the file system
     *
     * @param filePath -- the path to the file to delete
     * @return success or failure
     */
    public static boolean deleteFile(final String filePath) {
        String path;
        String prefix = filePath.substring(0, 5);

        if(prefix.equals("file:")) {
            path = filePath.substring(5);
        }
        else { path = filePath; }

        File toDelete = new File(path);
        if(!toDelete.exists()) {
            return false;
        }

        toDelete.delete();
        return true;
    }
}
