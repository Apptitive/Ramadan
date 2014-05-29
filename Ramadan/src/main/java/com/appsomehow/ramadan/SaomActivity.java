package com.appsomehow.ramadan;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appsomehow.ramadan.utilities.Utilities;
import com.appsomehow.ramadan.views.JustifiedTextView;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;


public class SaomActivity extends ActionBarActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saom);

        actionBar = getSupportActionBar();
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(getString(R.string.activity_saom), Utilities.getFont(this), -1));
        actionBar.setDisplayShowHomeEnabled(true);

        JustifiedTextView textViewDetails = (JustifiedTextView) findViewById(R.id.textview_details);
        textViewDetails.setText(getString(R.string.parallax_text), true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saom, menu);
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
