package com.appsomehow.ramadan;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.appsomehow.ramadan.helper.CSVToDbHelper;
import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.helper.DbTableName;
import com.appsomehow.ramadan.model.Region;
import com.appsomehow.ramadan.model.TimeTable;
import com.appsomehow.ramadan.preferences.SettingsActivity;
import com.appsomehow.ramadan.utilities.Alarm;
import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;

import org.joda.time.DateTime;

import java.util.List;


public class MainActivity extends ActionBarActivity implements RadialTimePickerDialog.OnTimeSetListener {

    private TextView txtTime;
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private boolean mHasDialogFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbManager.init(this);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);

        mHasDialogFrame = findViewById(R.id.frame) != null;
        View r = findViewById(R.id.tab_saom);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SaomActivity.class));
            }
        });

        CSVToDbHelper.readCSVAndInserIntoDb(this, R.raw.region, DbTableName.Region);
        CSVToDbHelper.readCSVAndInserIntoDb(this, R.raw.timetable, DbTableName.TimeTable);

        List<Region> regions = DbManager.getInstance().getAllRegions();
        for (Region rgn : regions){
            Log.e("Region Name: ", rgn.name);
        }

        List<TimeTable> timeTables = DbManager.getInstance().getAllTimeTables();
        for (TimeTable t : timeTables){
            Log.e("TimeTable Log: ", t.dateInBangla);
        }

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
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
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
    }

}
