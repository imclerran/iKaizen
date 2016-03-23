package mclerrani.ikaizen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * implement the RecyclerView.OnItemTouchListener to handle item clicks
 *
 * @// TODO: 3/14/2016 phase out. Use built in OnItemTouchListener instead.
 *
 * @author Jacob Tabak
 * @version 2/4/16
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    /**
     * constructor
     *
     * @param context -- the activity context
     * @param listener the item click listener
     */
    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    /**
     * intercept a touch event, and return true if the event is handled.
     *
     * @param view -- the RecyclerView clicked on
     * @param e -- the motion event that triggered OnItemTouchListener
     * @return true if event is handled
     */
    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildPosition(childView));
            return true;
        }
        return false;
    }

    /**
     * required method
     * not implemented
     *
     * @param view -- the RecyclerView
     * @param motionEvent -- the motion event that triggered the handler
     */
    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    /**
     * required method
     * not implemented
     *
     * @param disallowIntercept -- should disallow intercept?
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}