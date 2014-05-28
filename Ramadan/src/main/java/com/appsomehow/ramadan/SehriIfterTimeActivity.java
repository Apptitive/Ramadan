package com.appsomehow.ramadan;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class SehriIfterTimeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TableRow.LayoutParams params = new TableRow.LayoutParams(100, 100);
        TableLayout table = new TableLayout(this);
        for (int i = 0; i < 3; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < 3; j++) {
                TextView text = new TextView(this);
                text.setLayoutParams(params);
                text.setText(String.format("(%d, %d)", i, j));
                row.addView(text);
            }
            table.addView(row);
        }
        setContentView(table);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sehri_ifter_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
