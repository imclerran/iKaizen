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

public class KaizenRecyclerActivity extends AppCompatActivity
        implements SortKaizenByDialogFragment.SortKaizenByDialogListener {

    public final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";

    // this code belongs in launch activity
    private static Context appContext;
    // end launch activity code

    private DataManager dm = DataManager.getInstance();
    private PreferencesManager pm;
    private Kaizen kaizen;
    private ArrayList<Kaizen> kaizenList;
    KaizenRecyclerAdapter recAdapter;
    private CoordinatorLayout coordinatorLayout;
    private ContextMenuRecyclerView recKaizenList;
    private StaggeredGridLayoutManager sglm;
    private Kaizen toDelete = null;
    private int sortBy = KaizenComparator.COMPARE_DATE_MODIFIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(null != savedInstanceState) {
            sortBy = savedInstanceState.getInt("sortBy");
        }

        FloatingActionButton fabNewKaizen = (FloatingActionButton) findViewById(R.id.fabNewKaizen);
        fabNewKaizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newKaizen();
            }
        });

        // this code belongs in launch activity
        appContext = this.getApplicationContext();
        pm = PreferencesManager.getInstance(appContext);
        // end launch activity code

        final Context activityContext = this;
        //activityContext = this;

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        kaizenList = dm.getKaizenList();

        recKaizenList = (ContextMenuRecyclerView) findViewById(R.id.recKaizenList);
        recKaizenList.setHasFixedSize(false);
        //LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);


        int orientation = this.getResources().getConfiguration().orientation;
        if(Configuration.ORIENTATION_PORTRAIT == orientation) {
            sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        }
        if(Configuration.ORIENTATION_LANDSCAPE == orientation) {
            sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        //recKaizenList.setLayoutManager(llm);
        recKaizenList.setLayoutManager(sglm);
        recAdapter = new KaizenRecyclerAdapter(kaizenList);
        recKaizenList.setAdapter(recAdapter);

        if (kaizenList.size() == 0)
            recAdapter.add(Kaizen.getTestKaizen());

        // Kaizen CardView touch event handler
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

        /*recKaizenList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerView.ViewHolder holder =(RecyclerView.ViewHolder)v.getTag();
                        int position = holder.getLayoutPosition();

                        kaizen = recAdapter.getKaizenList().get(position);
                        if (null != kaizen) {
                            Intent intent = new Intent(activityContext, KaizenDetailsActivity.class);
                            intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
                            startActivity(intent);
                        }
                    }
                }
        );*/

        registerForContextMenu(recKaizenList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kaizen_recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_kaizen_recycler, menu);

        /*MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_documents_fragment, menu);*/
    }

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

    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void editKaizen(Kaizen k) {
        if (null != kaizen) {
            Intent intent = new Intent(this, KaizenEditActivity.class);
            intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
            startActivityForResult(intent, KaizenEditActivity.EDIT_KAIZEN_REQUEST);
        }
    }

    public void deleteKaizen(Kaizen k) {
        k.setDeleteMe(true);
        deleteKaizen();
    }

    public void promptForSortBy() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        FragmentManager fm = getFragmentManager();
        SortKaizenByDialogFragment sortByDialog = (SortKaizenByDialogFragment) SortKaizenByDialogFragment.newInstance("SortKaizenByDialogFragment");
        sortByDialog.show(fm, "sort_by_dialog_fragment");
    }

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

    @Override
    protected void onResume() {
        super.onResume();

        updateWelcomeMessageVisibility(pm.isEnableWelcomeMessage());
        deleteKaizen();
        recAdapter.sortKaizenList(sortBy);
        recAdapter.notifyDataSetChanged();
    }

    /**
     * remove a Kaizen from kaizenList
     * give the user a chance to undo the delete with a snackbar action
     */
    public boolean deleteKaizen() {

        for (int i = 0; i < kaizenList.size(); i++) {
            if (kaizenList.get(i).isDeleteMe()) {
                toDelete = kaizenList.remove(i);
                recAdapter.notifyDataSetChanged();
                break;
            }
        }

        if (null != toDelete) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Kaizen deleted!", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Kaizen restored!", Snackbar.LENGTH_SHORT);
                            toDelete.setDeleteMe(false);
                            snackbar1.show();
                        }
                    });

            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (null != toDelete) {
                        if (!toDelete.isDeleteMe()) {
                            recAdapter.add(toDelete);
                            recAdapter.notifyDataSetChanged();
                        }
                    }
                    toDelete = null;
                }
            });
            snackbar.show();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        // Don't launch details when create completes

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

    public void updateWelcomeMessageVisibility(boolean enabled) {
        LinearLayout llWelcomeMessage = (LinearLayout) findViewById(R.id.llWelcomeMessage);

        RelativeLayout.LayoutParams paramsShow =
                new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams paramsHide =
                new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);


        if(dm.getKaizenList().size() <= 3 && enabled) {
            llWelcomeMessage.setLayoutParams(paramsShow);
        }
        else {
            llWelcomeMessage.setLayoutParams(paramsHide);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("sortBy", sortBy);
    }

    // this code belongs in launch activity
    public static Context getContext() { return appContext; }
    // end launch activity code

}
