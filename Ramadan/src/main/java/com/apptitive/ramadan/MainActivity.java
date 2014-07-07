package com.apptitive.ramadan;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apptitive.ramadan.helper.CSVToDbHelper;
import com.apptitive.ramadan.helper.DbManager;
import com.apptitive.ramadan.helper.DbTableName;
import com.apptitive.ramadan.model.Region;
import com.apptitive.ramadan.model.TimeTable;
import com.apptitive.ramadan.receiver.TimeTableWidgetProvider;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.PreferenceHelper;
import com.apptitive.ramadan.utilities.UIUtils;
import com.apptitive.ramadan.views.BanglaTextView;

import java.text.ParseException;
import java.util.List;


public class MainActivity extends BaseActionBar implements View.OnClickListener {
    private int mAppWidgetId;
    private ActionBar actionBar;
    private PreferenceHelper preferenceHelper;
    private BanglaTextView iftarTime;
    private BanglaTextView seheriTime;
    private List<TimeTable> timeTables;
    private List<Region> regions;
    private Region region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbManager.init(this);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        preferenceHelper = new PreferenceHelper(this);
        if (!preferenceHelper.getBoolean(Constants.IS_DB_CREATED)) {
            CSVToDbHelper.readCSVAndInserIntoDb(this, R.raw.region, DbTableName.Region);
            CSVToDbHelper.readCSVAndInserIntoDb(this, R.raw.timetable, DbTableName.TimeTable);
            preferenceHelper.setBoolean(Constants.IS_DB_CREATED, true);
        }

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_White_Ramadan)));
        actionBar.setDisplayShowTitleEnabled(false);

        findViewById(R.id.tab_iftar_time).setOnClickListener(this);
        findViewById(R.id.tab_saom).setOnClickListener(this);
        findViewById(R.id.tab_nioat).setOnClickListener(this);
        findViewById(R.id.tab_ramadan).setOnClickListener(this);
        findViewById(R.id.tab_saom_vonger_karon).setOnClickListener(this);
        findViewById(R.id.tab_tarabih).setOnClickListener(this);

        iftarTime = (BanglaTextView) findViewById(R.id.tv_ifter_time);
        seheriTime = (BanglaTextView) findViewById(R.id.tv_seheri_time);

        timeTables = DbManager.getInstance().getAllTimeTables();
        regions = DbManager.getInstance().getAllRegions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, TimeTableWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int[] ids = {mAppWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        seheriTime.setText("0:00");
        iftarTime.setText("0:00");

        region = UIUtils.getSelectedLocation(regions, preferenceHelper.getString(Constants.PREF_KEY_LOCATION,  Constants.DEFAULT_REGION));
        if (region != null) {
            try {
                if (region.isPositive()) {
                    seheriTime.setBanglaText(UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTables, true, true));
                    iftarTime.setBanglaText(UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTables, true, false));
                } else {
                    seheriTime.setBanglaText(UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTables, true, true));
                    iftarTime.setBanglaText(UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTables, true, false));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_alarm) {
            startActivity(new Intent(MainActivity.this, AlarmActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.tab_saom:
                i = new Intent(this, TopicsActivity.class);
                i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.saom));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_saom);
                i.putExtra(Constants.topic.EXTRA_DATA_FILE, R.raw.data_topic_saom);
                startActivity(i);
                break;
            case R.id.tab_iftar_time:
                this.startActivity(new Intent(MainActivity.this, SehriIfterActivity.class));
                break;
            case R.id.tab_nioat:
                i = new Intent(this, TopicsActivity.class);
                i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.niyat_o_doa));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_niyat);
                i.putExtra(Constants.topic.EXTRA_DATA_FILE, R.raw.data_topic_niyat_o_doa);
                startActivity(i);
                break;
            case R.id.tab_ramadan:
                i = new Intent(this, TopicsActivity.class);
                i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.ramadan));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_romzan);
                i.putExtra(Constants.topic.EXTRA_DATA_FILE, R.raw.data_topic_ramadan);
                startActivity(i);
                break;
            case R.id.tab_saom_vonger_karon:
                i = new Intent(this, SaomVongerKaronActivity.class);
                i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.saom_vongo));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_saom_vongo);
                i.putExtra(Constants.topic.EXTRA_DATA_FILE, R.raw.data_topic_saom_vongo);
                startActivity(i);
                break;
            case R.id.tab_tarabih:
                i = new Intent(this, TopicsActivity.class);
                i.putExtra(Constants.topic.EXTRA_TITLE, getString(R.string.tarabih));
                i.putExtra(Constants.topic.EXTRA_ICON_ID, R.drawable.ic_tarabih);
                i.putExtra(Constants.topic.EXTRA_DATA_FILE, R.raw.data_topic_tarabih);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
