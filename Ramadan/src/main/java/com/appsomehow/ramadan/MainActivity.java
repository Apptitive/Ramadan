package com.appsomehow.ramadan;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appsomehow.ramadan.utilities.Alarm;
import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;


public class MainActivity extends ActionBarActivity implements RadialTimePickerDialog.OnTimeSetListener {

    private TextView txtTime;
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private boolean mHasDialogFrame;
    private Button btnSaom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHasDialogFrame = findViewById(R.id.frame) != null;
        Log.e("mHasDialogFrame", "" + mHasDialogFrame);
        txtTime = (TextView) findViewById(R.id.txt_time);
        if (mHasDialogFrame) {
            txtTime.setText("!");
        } else {
            txtTime.setText("--");
        }
        btnSaom = (Button) findViewById(R.id.saom);
        btnSaom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SaomActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        RadialTimePickerDialog rtpd = (RadialTimePickerDialog) getSupportFragmentManager().findFragmentByTag(
                FRAG_TAG_TIME_PICKER);
        if (rtpd != null) {
            rtpd.setOnTimeSetListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if (id == R.id.menu_alarm) {
            DateTime now = DateTime.now();
            RadialTimePickerDialog radialTimePickerDialog = RadialTimePickerDialog.newInstance(MainActivity.this, now.getHourOfDay(), now.getMinuteOfHour(), DateFormat.is24HourFormat(MainActivity.this));
            if (mHasDialogFrame) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame, radialTimePickerDialog, FRAG_TAG_TIME_PICKER)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
            radialTimePickerDialog.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        Alarm alarm = new Alarm(this);
        alarm.setOneTimeAlarm(hourOfDay, minute);
        txtTime.setText("" + hourOfDay + ":" + minute);
    }

}