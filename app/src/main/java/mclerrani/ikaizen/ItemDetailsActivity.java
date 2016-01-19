package mclerrani.ikaizen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.Activity;


public class ItemDetailsActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "mclerrani.ikaizen.MESSAGE";


    Item myItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up data
        myItem = Item.getTestItem();

        EditText txtType = (EditText)findViewById(R.id.txtType);
        txtType.setText(myItem.getType());

        EditText txtQuantity = (EditText)findViewById(R.id.txtQuantity);
        txtQuantity.setText(String.valueOf(myItem.getQuantity()));

        EditText txtCost = (EditText)findViewById(R.id.txtCost);
        txtCost.setText(String.valueOf(myItem.getCost()));
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnActionOnClick(View view) {

        //Spinner spnActions = (Spinner)findViewById(R.id.spnItemActions);
        //String option = String.valueOf(spnActions.getSelectedItem());

        Spinner spnItemActions = (Spinner)findViewById(R.id.spnItemActions);
        String selectedAction = String.valueOf(spnItemActions.getSelectedItem());

        if( selectedAction.equals("Kaizen Details")) {
            // start new activity
            sendMessage(view);
        }

        else {
            View.OnClickListener snackbarAction = new OnClickListener() {
                public void onClick(View view) {
                    Spinner spnActions = (Spinner) findViewById(R.id.spnItemActions);
                    String option = String.valueOf(spnActions.getSelectedItem());
                    Toast.makeText(ItemDetailsActivity.this, option + "!"/*"That's all for now folks!"*/, Toast.LENGTH_SHORT).show();
                }
            };
            Snackbar.make(view, "Lights, camera..", Snackbar.LENGTH_LONG)
                    .setAction("Action!", snackbarAction).setActionTextColor(android.graphics.Color.GREEN).show();
        }
    }

    public void sendMessage(View view) {
        // start new activity
        Intent intent = new Intent(this, KaizenDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "message");
        startActivity(intent);
    }
}
