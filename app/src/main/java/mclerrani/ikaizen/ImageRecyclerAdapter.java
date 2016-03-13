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
 * Created by imcle on 3/5/2016.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder> {

    private ArrayList<ImageFile> imageList;

    public ImageRecyclerAdapter(ArrayList<ImageFile> imageList) { this.imageList = imageList; }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View imageView = LayoutInflater.from(context).inflate(R.layout.image_thumbnail, parent, false);

        final ImageViewHolder vh = new ImageViewHolder(imageView);

        ViewTreeObserver observer = vh.itemView.getViewTreeObserver();

        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ImageFile image = vh.getImage();
                int targetWidth = vh.itemView.getMeasuredWidth();
                if (null == image.getThumbnail()) {
                    setPic(image, vh, targetWidth);
                }

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

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageFile image = imageList.get(position);
        if(null != image && null != holder.getImage()) {
            if (image.getItemID() != holder.getImage().getItemID()) {
                holder.setAspectRatio(0.0f);
            }
        }
        holder.setImage(image);
    }

    // TODO: needs optimization
    // try storing thumbnail and aspect ratio in viewholder
    // then set view layout params according to aspect ratio
    // no need to recreate bitmaps everytime gallery is redrawn
    private boolean setPic(ImageFile image, ImageViewHolder holder, int targetWidth) {
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
        int targetHeight = (int)((float)targetWidth/aspectRatio);
        image.setAspectRatio(aspectRatio);
        holder.setAspectRatio(aspectRatio);

        // Determine how much to scale down the image
        int scaleFactor = photoW/targetWidth;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; // deprecated

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, bmOptions);
        //Bitmap thumb = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
        //holder.imgThumbnail.setImageBitmap(thumb);
        image.setThumbnail(bitmap);

        try {
            pfd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imgThumbnail;

        private ImageFile image;
        float aspectRatio;
        int currentWidth = 0;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
        }

        public void setImage(ImageFile image) { this.image = image; }
        public ImageFile getImage() { return image; }

        public float getAspectRatio() { return aspectRatio; }
        public void setAspectRatio(float aspectRatio) { this.aspectRatio = aspectRatio; }

        public int getCurrentWidth() { return currentWidth; }
        public void setCurrentWidth(int currentWidth) { this.currentWidth = currentWidth; }
    }

    public ImageFile getImage(int position) {
        if(0 <= position && imageList.size() > position)
            return imageList.get(position);
        else return null;
    }
}
