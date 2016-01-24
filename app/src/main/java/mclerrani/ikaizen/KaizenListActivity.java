package mclerrani.ikaizen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class KaizenListActivity extends AppCompatActivity {
    public final static String EXTRA_KAIZEN_ID = "mclerrani.ikaizen.KAIZEN_ID";

    private DataManager dataManager = DataManager.getDataManager();
    private Kaizen kaizen;
    private ArrayList<Kaizen> list;
    private Spinner spnKaizenList;
    public static ArrayAdapter<Kaizen> spnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaizen_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewKaizen);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                newKaizen();
            }
        });

        spnKaizenList = (Spinner) findViewById(R.id.spnKaizenList);
        list = dataManager.getKaizenList();
        spnAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list);
        spnKaizenList.setAdapter(spnAdapter);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(spnAdapter.getCount() == 0)
            spnAdapter.add(Kaizen.getTestKaizen());
    }

    public void btnViewKaizenDetailsOnClick(View view) {
        kaizen = (Kaizen)spnKaizenList.getSelectedItem();
        if(null != kaizen) {
            Intent intent = new Intent(this, KaizenDetailsActivity.class);
            intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
            //startActivityForResult(intent, KaizenEditActivity.EDIT_KAIZEN_REQUEST);
            startActivity(intent);
        }
    }

    public void newKaizen() {
        kaizen = new Kaizen();
        //spnAdapter.add(kaizen);
        dataManager.getKaizenList().add(kaizen);
        spnAdapter.notifyDataSetChanged();

        Intent intent = new Intent(this, KaizenEditActivity.class);
        intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
        startActivityForResult(intent, KaizenEditActivity.EDIT_KAIZEN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == KaizenEditActivity.EDIT_KAIZEN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if(data.hasExtra(EXTRA_KAIZEN_ID)) {
                    kaizen = dataManager.getKaizen(data.getIntExtra(EXTRA_KAIZEN_ID, -1));
                }
                Intent intent = new Intent(this, KaizenDetailsActivity.class);
                intent.putExtra(EXTRA_KAIZEN_ID, kaizen.getItemID());
                startActivity(intent);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
