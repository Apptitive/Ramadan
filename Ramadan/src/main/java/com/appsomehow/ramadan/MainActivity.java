package com.appsomehow.ramadan;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.appsomehow.ramadan.helper.CSVToDbHelper;
import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.helper.DbTableName;
import com.appsomehow.ramadan.model.Region;
import com.appsomehow.ramadan.model.TimeTable;
import com.appsomehow.ramadan.utilities.Alarm;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.PreferenceHelper;
import com.appsomehow.ramadan.utilities.UIUtils;
import com.appsomehow.ramadan.utilities.Utilities;
import com.appsomehow.ramadan.views.BanglaTextView;
import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.nineoldandroids.view.ViewHelper;

import org.joda.time.DateTime;

import java.util.List;


public class MainActivity extends ActionBarActivity implements RadialTimePickerDialog.OnTimeSetListener, View.OnClickListener {
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private boolean mHasDialogFrame;
    private ActionBar actionBar;
    private PreferenceHelper preferenceHelper;
    private BanglaTextView iftarTime;
    private BanglaTextView seheriTime;
    private List<TimeTable> timeTables;
    private TimeTable timeTable;
    private List<Region> regions;
    private Region region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbManager.init(this);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
        preferenceHelper = new PreferenceHelper(this);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_White_Ramadan)));
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        mHasDialogFrame = findViewById(R.id.frame) != null;

        findViewById(R.id.tab_iftar_time).setOnClickListener(this);
        findViewById(R.id.tab_saom).setOnClickListener(this);
        findViewById(R.id.tab_nioat).setOnClickListener(this);
        findViewById(R.id.tab_ramadan).setOnClickListener(this);
        findViewById(R.id.tab_saom_vonger_karon).setOnClickListener(this);
        findViewById(R.id.tab_tarabih).setOnClickListener(this);

        iftarTime = (BanglaTextView) findViewById(R.id.tv_ifter_time);
        seheriTime = (BanglaTextView) findViewById(R.id.tv_seheri_time);

        if (!preferenceHelper.getBoolean(Constants.IS_DB_CREATED)) {
            Log.e("Db Checking : ", "First Time Db Created !");
            CSVToDbHelper.readCSVAndInserIntoDb(this, R.raw.region, DbTableName.Region);
            CSVToDbHelper.readCSVAndInserIntoDb(this, R.raw.timetable, DbTableName.TimeTable);
            preferenceHelper.setBoolean(Constants.IS_DB_CREATED, true);
        }
        timeTables = DbManager.getInstance().getAllTimeTables();
        timeTable = UIUtils.compareCurrentDate(timeTables);
        regions = DbManager.getInstance().getAllRegions();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Example of reattaching to the fragment
        if (timeTable != null)
            region = UIUtils.getSelectedLocation(regions, preferenceHelper.getString(Constants.PREF_KEY_LOCATION, "Dhaka"));

        if (region.isPositive()) {
            seheriTime.setBanglaText(UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTable, this, true,true));
            iftarTime.setBanglaText(UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTable, this, false,true));
        } else {
            seheriTime.setBanglaText(UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTable, this, true,true));
            iftarTime.setBanglaText(UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTable, this, false,true));
        }

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
            RadialTimePickerDialog radialTimePickerDialog = RadialTimePickerDialog.newInstance(MainActivity.this, now.getHourOfDay(), now.getMinuteOfHour(), DateFormat.is24HourFormat(MainActivity.this), preferenceHelper.getBoolean(getString(R.string.pref_key_alarm),true));
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
    public void onClick(View view) {
        Intent i = new Intent(this, TopicsActivity.class);
        switch (view.getId()) {
            case R.id.tab_saom:
                i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.saom));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_saom);
                i.putExtra(Constants.topic.EXTRA_HEADER_ARRAY_ID, R.array.header_topic_saom);
                i.putExtra(Constants.topic.EXTRA_DESC_ARRAY_ID, R.array.brief_topic_saom);
                i.putExtra(Constants.topic.EXTRA_FULL_TEXT_ARRAY_ID, R.array.full_text_topic_saom);
                startActivity(i);
                break;
            case R.id.tab_iftar_time:
                this.startActivity(new Intent(MainActivity.this, SehriIfterActivity.class));
                break;
            case R.id.tab_nioat:
                /*i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.niyat_o_doa));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_niyat);
                startActivity(i);*/
                this.startActivity(new Intent(MainActivity.this, DetailsActivity.class));
                break;
            case R.id.tab_ramadan:
                i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.ramadan));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_romzan);
                i.putExtra(Constants.topic.EXTRA_HEADER_ARRAY_ID, R.array.header_topic_ramadan);
                i.putExtra(Constants.topic.EXTRA_DESC_ARRAY_ID, R.array.brief_topic_ramadan);
                i.putExtra(Constants.topic.EXTRA_FULL_TEXT_ARRAY_ID, R.array.full_text_topic_ramadan);
                startActivity(i);
                break;
            case R.id.tab_saom_vonger_karon:
                /*i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.saom_vongo));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_saom_vongo);
                startActivity(i);*/
                this.startActivity(new Intent(MainActivity.this, DetailsActivity.class));
                break;
            case R.id.tab_tarabih:
                /*i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.tarabih));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_tarabih);
                startActivity(i);*/
                this.startActivity(new Intent(MainActivity.this, DetailsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay, int minute, boolean isSwitchOn) {
        preferenceHelper.setBoolean(getString(R.string.pref_key_alarm), isSwitchOn);
        Alarm alarm = new Alarm(this);
        alarm.setOneTimeAlarm(hourOfDay, minute);
    }
}
