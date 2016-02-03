package mclerrani.ikaizen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class KaizenRecyclerActivity extends AppCompatActivity {

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
    RecyclerView recKaizenList;
    private Kaizen toDelete = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        recKaizenList = (RecyclerView) findViewById(R.id.recKaizenList);
        recKaizenList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recKaizenList.setLayoutManager(llm);
        recAdapter = new KaizenRecyclerAdapter(kaizenList);
        recKaizenList.setAdapter(recAdapter);

        if (kaizenList.size() == 0)
            recAdapter.add(Kaizen.getTestKaizen());

        // Kaizen CardView touch event handler
        recKaizenList.addOnItemTouchListener(
                new RecyclerItemClickListener(activityContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * add a kaizen to the DataManager and launch the KaizenEditActivity
     */
    public void newKaizen() {
        /*kaizen = new Kaizen();
        dm.getKaizenList().add(kaizen);
        recAdapter.notifyDataSetChanged();*/

        Intent intent = new Intent(this, KaizenEditActivity.class);
        //intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivityForResult(intent, KaizenEditActivity.CREATE_KAIZEN_REQUEST);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateWelcomeMessageVisibility(pm.isShowWelcomeMessage());
        deleteKaizen();
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

        /*if (requestCode == KaizenEditActivity.EDIT_KAIZEN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data.hasExtra(EXTRA_KAIZEN_ID)) {
                    kaizen = dm.getKaizen(data.getIntExtra(EXTRA_KAIZEN_ID, -1));
                }
                Intent intent = new Intent(this, KaizenDetailsActivity.class);
                intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
                startActivity(intent);
            }
        }*/
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

    // this code belongs in launch activity
    public static Context getContext() { return appContext; }
    // end launch activity code

}
