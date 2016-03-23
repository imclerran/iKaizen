package mclerrani.ikaizen;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * An Activity class for displaying solution details, or a list of countermeasures
 * This is achieved using tabs, and a separate fragment for each tab
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class SolutionOverviewTabbedActivity extends AppCompatActivity
        implements SolutionDetailsFragment.OnFragmentInteractionListener,
        CountermeasureListFragment.OnFragmentInteractionListener,
        SortCountermeasureByDialogFragment.SortCountermeasureByDialogListener{

    private final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";
    private final static int SOLUTION_DETAILS_FRAGMENT_ID = 1;
    private final static int COUNTERMEASURE_LIST_FRAGMENT_ID = 2;

    private int activeTab;
    private DataManager dm;
    private Kaizen kaizen;
    int sortBy = CountermeasureComparator.COMPARE_DATE_MODIFIED;
    FloatingActionButton fab;

    /**
     * Android lifecycle onCreate() method
     *
     * @param savedInstanceState -- the saved application state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_overview_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dm = DataManager.getInstance(getApplicationContext());

        if(null != savedInstanceState) {
            activeTab = savedInstanceState.getInt("activeTab");
            sortBy = savedInstanceState.getInt("sortBy");
            if(COUNTERMEASURE_LIST_FRAGMENT_ID == activeTab) {
                CountermeasureListFragment cmlfrag = (CountermeasureListFragment) getSupportFragmentManager().findFragmentByTag("countermeasure_list_fragment");
                cmlfrag.setSortBy(sortBy);
            }
            setActiveTabHighlight();
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCountermeasure();
            }
        });

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_KAIZEN_ID)) {
            kaizen = dm.getKaizen(intent.getIntExtra(EXTRA_KAIZEN_ID, -1));
        }
        else {
            finish();
        }

        // if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        // Create an instance of ExampleFragment
        SolutionDetailsFragment firstFragment = SolutionDetailsFragment.newInstance(kaizen.getItemID());

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        //firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, firstFragment).commit();
        activeTab = SOLUTION_DETAILS_FRAGMENT_ID;

    }

    /**
     * Android lifecycle onSaveInstanceState() method
     * store active tab and sort by info to restore when app resumes
     *
     * @param outState -- the save state bundle
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("activeTab", activeTab);
        outState.putInt("sortBy", sortBy);
    }

    /**
     * Android lifecycle onResume() method
     * restore the active tab
     * if the current tab is the countermeasure list, delete any flagged countermeasures
     */
    @Override
    protected void onResume() {
        super.onResume();
        CountermeasureListFragment cmlfrag =
                (CountermeasureListFragment) getSupportFragmentManager().findFragmentByTag("countermeasure_list_fragment");
        if(COUNTERMEASURE_LIST_FRAGMENT_ID == activeTab && null != cmlfrag) {
            fab.show();
            cmlfrag.deleteCountermeasure();
        }
    }

    /**
     * required method
     */
    @Override
    public void onFragmentInteraction() {
    }

    /**
     * respond when the solution details tab is clicked
     *
     * @param view -- the clicked view
     */
    public void btnSolutionDetailsTabOnClick(View view) {
        setFragmentToSolutionDetails();
    }

    /**
     * set solution details as the active tab
     */
    public void setFragmentToSolutionDetails() {
        activeTab = SOLUTION_DETAILS_FRAGMENT_ID;
        setActiveTabHighlight();
        fab.hide();

        SolutionDetailsFragment newFragment = new SolutionDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(SolutionDetailsFragment.ARG_KAIZEN_ID, kaizen.getItemID());
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, newFragment);
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    /**
     * respond when the solution details tab is clicked
     *
     * @param view -- the clicked view
     */
    public void btnCountermeasureListTabOnClick(View view) {
        setFragmentToCountermeasureList();
    }

    /**
     * set the countermeasure list as the active tab
     */
    public void setFragmentToCountermeasureList() {
        activeTab = COUNTERMEASURE_LIST_FRAGMENT_ID;
        setActiveTabHighlight();
        fab.show();

        CountermeasureListFragment newFragment = new CountermeasureListFragment();
        Bundle args = new Bundle();
        args.putInt(CountermeasureListFragment.ARG_KAIZEN_ID, kaizen.getItemID());
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, newFragment, "countermeasure_list_fragment");
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    /**
     * set the correct active tab highlight
     */
    public void setActiveTabHighlight() {
        View vSolutionDetailsTabHighlight = findViewById(R.id.vSolutionDetailsTabHighlight);
        View vCountermeasureListTabHighlight = findViewById(R.id.vCountermeasureListTabHighlight);

        switch (activeTab) {
            case SOLUTION_DETAILS_FRAGMENT_ID:
                vSolutionDetailsTabHighlight.setVisibility(View.VISIBLE);
                vCountermeasureListTabHighlight.setVisibility(View.INVISIBLE);
                break;
            case COUNTERMEASURE_LIST_FRAGMENT_ID:
                vSolutionDetailsTabHighlight.setVisibility(View.INVISIBLE);
                vCountermeasureListTabHighlight.setVisibility(View.VISIBLE);
                break;

        }
    }

    /**
     * inflate the options menu
     *
     * @param menu -- the menu to inflate
     * @return success or failure
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solution_overview_tabbed, menu);
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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_edit_solution:
                editSolution();
                return true;
            case R.id.action_sort_by:
                promptForSortBy();
                return true;
            case R.id.action_settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * launch the activity to edit a solution
     */
    public void editSolution() {
        Intent intent = new Intent(this, SolutionEditActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivityForResult(intent, SolutionEditActivity.EDIT_SOLUTION_REQUEST);
    }

    /**
     * launch the activity to create a new countermeasure
     */
    public void createCountermeasure() {
        Intent intent = new Intent(this, CountermeasureEditActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivityForResult(intent, CountermeasureEditActivity.CREATE_COUNTERMEASURE_REQUEST);
    }

    /**
     * launch the settings activity
     */
    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * show a dialog to allow the use to choose a sorting option
     */
    public void promptForSortBy() {
        FragmentManager fm = getFragmentManager();
        SortCountermeasureByDialogFragment sortCountermeasureByDialog = (SortCountermeasureByDialogFragment) SortCountermeasureByDialogFragment.newInstance("SortCountermeasureByDialogFragment");
        sortCountermeasureByDialog.show(fm, "sort_countermeasure_by_dialog_fragment");
    }

    /**
     * handle completed activity requests
     * set the active tab, according to which request completed
     *
     * @param requestCode -- the request which completed
     * @param resultCode -- success or failure of the request
     * @param data -- any data returned by the requested activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CountermeasureEditActivity.EDIT_COUNTERMEASURE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                setFragmentToCountermeasureList();
            }
        }
        if (requestCode == CountermeasureEditActivity.CREATE_COUNTERMEASURE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                setFragmentToCountermeasureList();
            }
        }
        if (requestCode == SolutionEditActivity.EDIT_SOLUTION_REQUEST) {
            if (resultCode == RESULT_OK) {
                setFragmentToSolutionDetails();
            }
        }
    }

    /**
     * after the sort by dialog resolves,
     * sort the countermeasure list according to the user input
     *
     * @param dialog -- the resolved dialog
     * @param which -- the index of the option chosen
     */
    @Override
    public void onDialogItemClick(DialogFragment dialog, int which) {
        switch (which) {
            case 0:
                sortBy = CountermeasureComparator.COMPARE_DATE_MODIFIED;
                break;
            case 1:
                sortBy = CountermeasureComparator.COMPARE_COST_ASCENDING;
                break;
            case 2:
                sortBy = CountermeasureComparator.COMPARE_COST_DESCENDING;
                break;
            default:
                sortBy = CountermeasureComparator.COMPARE_DATE_MODIFIED;

        }

        CountermeasureListFragment cmlfrag = (CountermeasureListFragment) getSupportFragmentManager().findFragmentByTag("countermeasure_list_fragment");
        cmlfrag.sortBy(sortBy);
    }

    /**
     * navigate up in the app
     *
     * @return always true
     */
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
