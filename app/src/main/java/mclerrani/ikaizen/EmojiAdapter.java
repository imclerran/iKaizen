package mclerrani.ikaizen;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * Created by Ian on 2/13/2016.
 */
public class EmojiAdapter extends BaseAdapter {

    private Context mContext;
    private GridView grid;

    // references to our images
    private int[] emojiList;

    public EmojiAdapter(Context context, GridView gridview, int[] emojiList) {
        mContext = context;
        this.grid = gridview;
        this.emojiList = emojiList;
    }

    @Override
    public int getCount() {
        return emojiList.length;
    }

    @Override
    public Object getItem(int position) {
        return emojiList[position];
    }

    @Override
    public long getItemId(int position) {
        return emojiList[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    grid.getWidth() / 2, grid.getHeight() / 11));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(emojiList[position]);
        //imageView.setMinimumHeight(R.dimen.grid_view_entry_size);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        //imageView.setMaxHeight(R.dimen.grid_view_entry_size);
        //imageView.setMinimumHeight(R.dimen.grid_view_entry_size);
        return imageView;
    }
}