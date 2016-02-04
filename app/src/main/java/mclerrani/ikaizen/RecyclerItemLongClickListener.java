package mclerrani.ikaizen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ian on 2/4/2016.
 */

// TODO: implement context menu for RecyclerView item
@Deprecated
public class RecyclerItemLongClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemLongClickListener mListener;
    private GestureDetector mGestureDetector;

    public interface OnItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }

    public RecyclerItemLongClickListener(Context context, OnItemLongClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
        mGestureDetector.setIsLongpressEnabled(true);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemLongClick(childView, rv.getChildPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemLongClick(childView, rv.getChildPosition(childView));
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}
