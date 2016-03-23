package mclerrani.ikaizen;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Fragment class to allow the user to pick an emoji from a list
 *
 * @author Ian McLerran
 * @version 2/16/16
 */
public class EmojiPickerActivityFragment extends Fragment {

    GridView grdEmojiPicker;

    /**
     * default constructor
     */
    public EmojiPickerActivityFragment() {
    }

    /**
     * Android lifecycle onCreateView() method
     *
     * @param inflater -- the LayoutInflater
     * @param container -- the ViewGroup which contains the fragment
     * @param savedInstanceState -- the saved application state
     * @return the inflated fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoji_picker, container, false);
        grdEmojiPicker = (GridView) view.findViewById(R.id.grdEmojiPicker);

        grdEmojiPicker.setAdapter(new EmojiAdapter(getContext(), grdEmojiPicker, EmojiHelper.emojiGoodId));

        grdEmojiPicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Intent resultIntent = new Intent();
                //resultIntent.putExtra(EmojiPickerActivity.RESULT_EMOJI_ID, parent.getAdapter().getItemId(position));
                resultIntent.putExtra(EmojiPickerActivity.RESULT_EMOJI_ID, id);
                getActivity().setResult(Activity.RESULT_OK, resultIntent);
                getActivity().finish();
            }
        });

        return view;
    }

    /**
     * set the number of columns the GridView should used, based on the screen width
     */
    private void refreshGridView() {
        int gridViewEntrySize = getResources().getDimensionPixelSize(R.dimen.grid_view_entry_size);
        int gridViewSpacing = getResources().getDimensionPixelSize(R.dimen.grid_view_spacing);

        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int numColumns = (display.getWidth() - gridViewSpacing) / (gridViewEntrySize + gridViewSpacing);
        grdEmojiPicker.setNumColumns(numColumns);
    }

    /**
     * Android lifecycle onResume() method
     * call method to set the number of grid columns when the activity is resumed
     */
    @Override
    public void onResume() {
        super.onResume();
        refreshGridView();
    }
}
