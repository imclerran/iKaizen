package mclerrani.ikaizen;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Activity class to display list of Kaizen objects and a welcome message
 *
 * @author Ian McLerran
 * @version 3/14/16
 */
public class KaizenRecyclerActivity extends AppCompatActivity
        implements SortKaizenByDialogFragment.SortKaizenByDialogListener {

    public final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";

    // appContext belongs in launch activity
    private static Context appContext;

    private DataManager dm;
    private PreferencesManager pm;
    private Kaizen kaizen;
    private ArrayList<Kaizen> kaizenList;
    private static KaizenRecyclerAdapter recAdapter;
    private CoordinatorLayout coordinatorLayout;
    private ContextMenuRecyclerView recKaizenList;
    private StaggeredGridLayoutManager sglm;
    private Kaizen toDelete = null;
    private int sortBy = KaizenComparator.COMPARE_DATE_MODIFIED;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // restore saved instance state
        if(null != savedInstanceState) {
            sortBy = savedInstanceState.getInt("sortBy");
        }

        // setup FAB
        FloatingActionButton fabNewKaizen = (FloatingActionButton) findViewById(R.id.fabNewKaizen);
        fabNewKaizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newKaizen();
            }
        });

        // this code belongs in launch activity
        appContext = this.getApplicationContext();
        // end launch activity code
        final Context activityContext = this;

        // get manager classes
        dm = DataManager.getInstance(appContext);
        pm = PreferencesManager.getInstance(appContext);

        // setup recycler view and adapter
        recKaizenList = (ContextMenuRecyclerView) findViewById(R.id.recKaizenList);
        recKaizenList.setHasFixedSize(false);
        kaizenList = dm.getKaizenList();
        recAdapter = new KaizenRecyclerAdapter(kaizenList);
        recKaizenList.setAdapter(recAdapter);
        registerForContextMenu(recKaizenList);

        // setup layout mananger for recycler view
        int orientation = this.getResources().getConfiguration().orientation;
        if(Configuration.ORIENTATION_PORTRAIT == orientation) {
            sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        }
        if(Configuration.ORIENTATION_LANDSCAPE == orientation) {
            sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        recKaizenList.setLayoutManager(sglm);

        // setup a touch listerner for recycler items
        recKaizenList.addOnItemTouchListener(
                new RecyclerItemClickListener(activityContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        kaizen = recAdapter.getKaizenList().get(position);
                        if (null != kaizen) {
                            Intent intent = new Intent(activityContext, KaizenDetailsActivity.class);
                            intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
                            startActivity(intent);
                        }
                    }
                })
        );
    }

    /**
     * inflate the options menu
     *
     * @param menu the menu to inflate
     * @return success or failure
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kaizen_recycler, menu);
        return true;
    }

    /**
     * respond to the user selection from the options menu
     *
     * @param item -- the item selected from the options menu
     * @return success or failure
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

        switch (item.getItemId()) {
            case R.id.action_settings:
                launchSettings();
                return true;
            case R.id.action_sort_by:
                promptForSortBy();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * inflate the context menu
     *
     * @param menu the menu to inflate
     * @param v the view in which the menu has context
     * @param menuInfo additional info about the context menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_kaizen_recycler, menu);
    }

    /**
     * respond to the user selection from the context menu
     *
     * @param item -- the item selected from the context menu
     * @return success or failure
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        ContextMenuRecyclerView.RecyclerContextMenuInfo info
                = (ContextMenuRecyclerView.RecyclerContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        kaizen = recAdapter.getKaizenList().get(position);

        switch (item.getItemId()) {
            case R.id.action_edit_kaizen:
                editKaizen(kaizen);
                return true;
            case R.id.action_delete_kaizen:
                deleteKaizen(kaizen);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * launch the settings activity
     */
    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * launch the edit kaizen activity
     *
     * @param k -- the kaizen to edit
     */
    public void editKaizen(Kaizen k) {
        if (null != kaizen) {
            Intent intent = new Intent(this, KaizenEditActivity.class);
            intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
            startActivityForResult(intent, KaizenEditActivity.EDIT_KAIZEN_REQUEST);
        }
    }

    /**
     * flag a kaizen for deletion, then call the method to delete it
     *
     * @param k -- the kaizen to delete
     */
    public void deleteKaizen(Kaizen k) {
        k.setDeleteMe(true);
        deleteKaizen();
    }

    /**
     * if a Kaizen has been flagged for deletion, remove it from the list
     * give the user a chance to restore it, or delete it from the DB if they do not
     */
    public void deleteKaizen() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        // if there is a Kaizen object with deleteMe flag set
        // remove it from the kaizenList, keep it in a temp var
        for (int i = 0; i < kaizenList.size(); i++) {
            if (kaizenList.get(i).isDeleteMe()) {
                toDelete = kaizenList.remove(i);
                recAdapter.notifyDataSetChanged();
                break;
            }
        }

        // if a kaizen has been flagged for deletion
        if (null != toDelete) {
            // notify user Kaizen has been deleted and prompt for undo
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Kaizen deleted!", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // if user chooses to undo the delete operation
                            // unflag the Kaizen for deletion
                            toDelete.setDeleteMe(false);
                        }
                    });

            // when the delete/undo snackbar is dismissed
            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    // check if kaizen is STILL flagged for deletion
                    if (null != toDelete) {
                        if (!toDelete.isDeleteMe()) {
                            // if not flagged for deletion, restore kaizen to list
                            recAdapter.add(toDelete);
                            recAdapter.sortKaizenList(sortBy);
                            recAdapter.notifyDataSetChanged();

                            // Notify user the kaizen has been restored
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Kaizen restored!", Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                        }
                        else {
                            dm.deleteKaizen(toDelete);
                        }
                    }
                    toDelete = null;
                }
            });
            snackbar.show();
        }
    }

    /**
     * show the sort kaizen dialog
     */
    public void promptForSortBy() {
        FragmentManager fm = getFragmentManager();
        SortKaizenByDialogFragment sortByDialog = SortKaizenByDialogFragment.newInstance("SortKaizenByDialogFragment");
        sortByDialog.show(fm, "sort_by_dialog_fragment");
    }

    /**
     * when the sort dialog resolves, determine which option was selected
     * then sort the kaizen list accordingly
     *
     * @param dialog -- the resolved dialog
     * @param which -- the index of the option chosen
     */
    @Override
    public void onDialogItemClick(DialogFragment dialog, int which) {
        switch (which) {
            case 0:
                sortBy = KaizenComparator.COMPARE_DATE_MODIFIED;
                break;
            case 1:
                sortBy = KaizenComparator.COMPARE_TOTAL_WASTE;
                break;
            default:
                sortBy = KaizenComparator.COMPARE_DATE_MODIFIED;
        }
        recAdapter.sortKaizenList(sortBy);
        recAdapter.notifyDataSetChanged();
    }


    /**
     * launch the KaizenEditActivity with request code CREATE_KAIZEN_REQUEST
     */
    public void newKaizen() {
        Intent intent = new Intent(this, KaizenEditActivity.class);
        startActivityForResult(intent, KaizenEditActivity.CREATE_KAIZEN_REQUEST);
    }

    /**
     * Android lifecycle onResume() method
     * set the visibility of the welcome message and delete any flagged kaizen at this time
     */
    @Override
    protected void onResume() {
        super.onResume();

        updateWelcomeMessageVisibility();
        deleteKaizen();
        recAdapter.sortKaizenList(sortBy);
        recAdapter.notifyDataSetChanged();
    }

    /**
     * handle completed activity requests
     * switch to details activity after editing a kaizen
     *
     * @param requestCode -- the request which completed
     * @param resultCode -- success or failure of the request
     * @param data -- any data returned by the requested activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == KaizenEditActivity.EDIT_KAIZEN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(EXTRA_KAIZEN_ID)) {
                    kaizen = dm.getKaizen(data.getIntExtra(EXTRA_KAIZEN_ID, -1));
                }
                Intent intent = new Intent(this, KaizenDetailsActivity.class);
                intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
                startActivity(intent);
            }
        }
    }

    /**
     * show or hide the welcome message according to number of kaizen and user preferences
     */
    public void updateWelcomeMessageVisibility() {
        pm.isEnableWelcomeMessage();
        LinearLayout llWelcomeMessage = (LinearLayout) findViewById(R.id.llWelcomeMessage);

        RelativeLayout.LayoutParams paramsShow =
                new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams paramsHide =
                new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);


        if(dm.getKaizenList().size() <= 3 && pm.isEnableWelcomeMessage()) {
            llWelcomeMessage.setLayoutParams(paramsShow);
        }
        else {
            llWelcomeMessage.setLayoutParams(paramsHide);
        }
    }

    /**
     * Android lifecycle onSaveInstanceState() method
     * save the sort option the user has selected
     *
     * @param outState -- the save state bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("sortBy", sortBy);
    }

    /**
     * provide outside access to the recycler adapter
     *
     * @return the kaizen recycler adapter
     */
    public static KaizenRecyclerAdapter getRecyclerAdapter() { return recAdapter; }

    /**
     * get application context
     * this method must be in the launch activity
     *
     * @return the application context
     */
    public static Context getContext() { return appContext; }

}
