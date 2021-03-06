package mclerrani.ikaizen;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * A dialog fragment to display a choice of sorting options
 *
 * @author Ian McLerran
 * @version 2/16/16
 */
public class SortKaizenByDialogFragment extends DialogFragment {

    public interface SortKaizenByDialogListener {
        void onDialogItemClick(DialogFragment dialog, int which);
    }

    // Use this instance of the interface to deliver action events
    SortKaizenByDialogListener mListener;

    /**
     * create a new instance of the dialog fragment
     *
     * @param title -- the title of the dialog fragment
     * @return the new fragment
     */
    public static SortKaizenByDialogFragment newInstance(String title) {
        SortKaizenByDialogFragment frag = new SortKaizenByDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    /**
     * use the AlertDialog.builder to generate the dialog
     *
     * @param savedInstanceState -- the saved application state
     * @return the generated dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort_kaizen_by_dialog_title)
                .setItems(R.array.arrSortKaizenByOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        mListener.onDialogItemClick(SortKaizenByDialogFragment.this, which);
                    }
                });
        return builder.create();
    }

    /**
     * attach the dialog to an activity
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SortKaizenByDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement SortByDialogListener");
        }
    }
}
