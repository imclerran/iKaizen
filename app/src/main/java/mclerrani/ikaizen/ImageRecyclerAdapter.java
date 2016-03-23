package mclerrani.ikaizen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * a custom extension of the RecyclerView.Adapter class to display Images in a RecyclerView
 *
 * @author Ian McLerran
 * @version 3/14/16
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder> {

    private ArrayList<ImageFile> imageList;

    /**
     * constructor
     *
     * @param imageList the list of images to display in the RecyclerView
     */
    public ImageRecyclerAdapter(ArrayList<ImageFile> imageList) { this.imageList = imageList; }

    /**
     * called when a new object is added to the recycler. Inflates a view using the designated layout
     *
     * @param parent -- the containing view group
     * @param viewType -- used if displaying more view type in the recycler
     * @return a ViewHolder containing the newly inflated view
     */
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View imageView = LayoutInflater.from(context).inflate(R.layout.image_thumbnail, parent, false);

        final ImageViewHolder vh = new ImageViewHolder(imageView);

        ViewTreeObserver observer = vh.itemView.getViewTreeObserver();

        // Trigger an event at the pre-draw step.
        // This will allow us to generate a thumbnail image
        // and set the itemView height after the itemView width has been measured
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ImageFile image = vh.getImage();
                int targetWidth = vh.itemView.getMeasuredWidth();

                // if a thumbnail has not been generated, do so.
                if (null == image.getThumbnail()) {
                    setThumb(image, targetWidth);
                    vh.setAspectRatio(image.getAspectRatio());
                }

                // if the width of the of view, or the aspect ratio of the image has changed
                // store the new dimensions in the ViewHolder, then calculate and set the view height
                if (targetWidth != vh.getCurrentWidth() || image.getAspectRatio() != vh.getAspectRatio()) {
                    vh.setCurrentWidth(targetWidth);
                    vh.setAspectRatio(image.getAspectRatio());
                    int targetHeight = (int) ((float) targetWidth / image.getAspectRatio());
                    vh.imgThumbnail.setLayoutParams(new FrameLayout.LayoutParams(targetWidth, targetHeight));
                }
                vh.imgThumbnail.setImageBitmap(image.getThumbnail());
                return true;
            }
        });

        return vh;
    }

    /**
     * bind a ViewHolder to an ImageFile
     *
     * @param holder -- the viewholder to use
     * @param position -- the position of the viewholder
     */
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageFile image = imageList.get(position);
        if(null != image && null != holder.getImage()) {
            // if the ImageFile bound to the ViewHolder has changed,
            // set aspect ratio to 0 to force recalculating the view height
            if (image.getItemID() != holder.getImage().getItemID()) {
                holder.setAspectRatio(0.0f);
            }
        }
        holder.setImage(image);
    }



    /**
     * load an image from the filesystem and generate a thumbnail
     *
     * @param image -- the image on the filesystem to decode
     * @param targetWidth -- the desired width of the thumbnail
     * @return success or failure
     */
    private boolean setThumb(ImageFile image, int targetWidth) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        Uri uri = Uri.parse(image.getPath());
        FileDescriptor fd = null;
        ParcelFileDescriptor pfd = null;
        try {
            pfd = KaizenRecyclerActivity.getContext().getContentResolver().openFileDescriptor(uri, "r");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        fd = pfd.getFileDescriptor();
        BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        float aspectRatio = (float)photoW/(float)photoH;
        image.setAspectRatio(aspectRatio);

        // Determine how much to scale down the image
        int scaleFactor = photoW/targetWidth;

        // Decode the image file into a Bitmap approximately the target width
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; // deprecated

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        image.setThumbnail(bitmap);

        try {
            pfd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * get the number of images stored in the adapter
     *
     * @return the number of items
     */
    @Override
    public int getItemCount() {
        return imageList.size();
    }

    /**
     * get an ImageFile at the specified position
     *
     * @param position the position of the requested image
     * @return the requested ImageFile
     */
    public ImageFile getImage(int position) {
        if(0 <= position && imageList.size() > position)
            return imageList.get(position);
        else return null;
    }

    /**
     * A ViewHolder class containing information about a single view item in the RecyclerView
     */
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imgThumbnail;

        private ImageFile image;
        float aspectRatio;
        int currentWidth = 0;

        /**
         * constructor
         *
         * @param itemView -- an inflated view associated with one ImageFile item
         */
        public ImageViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
        }

        // getters and setters:

        public void setImage(ImageFile image) { this.image = image; }
        public ImageFile getImage() { return image; }

        public float getAspectRatio() { return aspectRatio; }
        public void setAspectRatio(float aspectRatio) { this.aspectRatio = aspectRatio; }

        public int getCurrentWidth() { return currentWidth; }
        public void setCurrentWidth(int currentWidth) { this.currentWidth = currentWidth; }
    }
}
