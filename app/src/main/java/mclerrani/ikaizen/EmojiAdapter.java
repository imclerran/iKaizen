package mclerrani.ikaizen;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * An adapter class for displaying a GridView of selectable emoji
 *
 * @author Ian McLerran
 * @version 3/14/16
 */
public class EmojiAdapter extends BaseAdapter {

    private Context context;
    private GridView gridView;
    private int[] emojiList;

    /**
     * constructor
     *
     * @param context -- the context the adapter is instantiated in
     * @param gridview -- the gridview the adapter is associated with
     * @param emojiList -- an array of emoji Id
     */
    public EmojiAdapter(Context context, GridView gridview, int[] emojiList) {
        this.context = context;
        this.gridView = gridview;
        this.emojiList = emojiList;
    }

    /**
     * get the number of emoji in the array
     *
     * @return the number of emoji in the array
     */
    @Override
    public int getCount() {
        return emojiList.length;
    }

    /**
     * get the emoji at a specified position
     *
     * @param position -- the position of the requested emoji
     * @return the id of the specified emoji
     */
    @Override
    public Object getItem(int position) {
        return emojiList[position];
    }

    /**
     * get the id of the requested emoji
     *
     * @param position -- the position of the requested emoji
     * @return the id of the requested emoji
     */
    @Override
    public long getItemId(int position) {
        return emojiList[position];
    }

    /**
     * get a child view to display in the GridView
     *
     * @param position -- the position of the child view
     * @param convertView -- the previous view, if already inflated
     * @param parent -- the parent ViewGroup
     * @return the inflated child view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize the view
            imageView = new ImageView(context);
            imageView.setImageResource(emojiList[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));

        } else {
            imageView = (ImageView) convertView;
            if(0 <imageView.getMeasuredWidth()) {
                imageView.setLayoutParams(new FrameLayout.LayoutParams(
                        imageView.getMeasuredWidth(),
                        imageView.getMeasuredWidth()));
            }
        }
        return imageView;
    }
}