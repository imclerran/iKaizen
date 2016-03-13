package mclerrani.ikaizen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

/**
 * An extension of the RecyclerView class that supports context menus
 * @author Renaud Cerrato
 * @version 2/4/16
 */
public class ContextMenuRecyclerView extends android.support.v7.widget.RecyclerView {

    private RecyclerContextMenuInfo mContextMenuInfo;

    /**
     * constructor
     * @param context -- the context in which the Recycler is instantiated
     */
    public ContextMenuRecyclerView(Context context) {
        super(context);
    }

    /**
     *
     * @param context -- the context in which the Recycler is instantiated
     * @param attrs -- a collection of xml attributes
     */
    public ContextMenuRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param context -- the context in which the RecyclerView is instantiated
     * @param attrs -- a collection of xml attributes
     * @param defStyle -- the default style to apply to the RecyclerView
     */
    public ContextMenuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * getter for ContextMenuInfo
     * @return additional info about the creation of the context menu
     */
    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    /**
     * show the context menu
     * @param originalView -- the view to create a context menu for
     * @return success or failure
     */
    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int longPressPosition = getChildAdapterPosition(originalView);
        if (longPressPosition >= 0) {
            final long longPressId = getAdapter().getItemId(longPressPosition);
            mContextMenuInfo = new RecyclerContextMenuInfo(longPressPosition, longPressId);
            return super.showContextMenuForChild(originalView);
        }
        return false;
    }

    /**
     * implementation ContextMenuInfo class
     * adds fields to store position and id of context-clicked item
     */
    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {

        /**
         * constructor
         * @param position -- position of context-clicked item
         * @param id -- id of context-clicked item
         */
        public RecyclerContextMenuInfo(int position, long id) {
            this.position = position;
            this.id = id;
        }

        final public int position;
        final public long id;
    }

}
