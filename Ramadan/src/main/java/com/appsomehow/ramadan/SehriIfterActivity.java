package com.appsomehow.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.appsomehow.ramadan.adapter.TimeTableAdapter;
import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.helper.Helper;
import com.appsomehow.ramadan.model.Region;
import com.appsomehow.ramadan.model.TimeTable;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.PreferenceHelper;
import com.appsomehow.ramadan.utilities.UIUtils;
import com.appsomehow.ramadan.utilities.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SehriIfterActivity extends ActionBarActivity {

    private List<TimeTable> timeTables;
    private TimeTableAdapter timeTableAdapter;
    ListView lvTimeTable;
    private ActionBar actionBar;
    private Map<String, String> regionMap;
    private String[] items;
    private List<Region> regions;
    private PreferenceHelper preferenceHelper;
    private static int currentDateIndex;
    private Region usersRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbManager.init(this);
        regionMap = new HashMap<String, String>();
        preferenceHelper = new PreferenceHelper(this);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_White_Ramadan)));
        actionBar.setDisplayShowHomeEnabled(true);
        currentDateIndex = UIUtils.getCurrentDateIndex();
        setContentView(R.layout.activity_sehri_ifter);


        timeTables = DbManager.getInstance().getAllTimeTables();
        usersRegion = DbManager.getInstance().getRegionWithName(preferenceHelper.getString(Constants.PREF_KEY_LOCATION, Constants.DEFAULT_REGION));
        for (TimeTable timeTable : timeTables) {
            if (usersRegion.isPositive()) {
                timeTable.setSehriTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(usersRegion.getIntervalSehri(), timeTable, getBaseContext(), true), getBaseContext()).toString());
                timeTable.setIfterTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(usersRegion.getIntervalIfter(), timeTable, getBaseContext(), false), getBaseContext()).toString());
            } else {
                timeTable.setSehriTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(-usersRegion.getIntervalSehri(), timeTable, getBaseContext(), true), getBaseContext()).toString());
                timeTable.setIfterTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(-usersRegion.getIntervalIfter(), timeTable, getBaseContext(), false), getBaseContext()).toString());
            }

        }
        lvTimeTable = (ListView) findViewById(R.id.lv_time_table);
        timeTableAdapter = new TimeTableAdapter(this, R.layout.list_item_time_table, timeTables) {


            @Override
            public int getViewTypeCount() {
                return 30;
            }

            @Override
            public int getItemViewType(int position) {
                return position % 30;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                if (currentDateIndex < 100 && position == currentDateIndex) {
                    v.setBackgroundColor(getResources().getColor(R.color.AB_Green_Ramadan));
                }
                return v;
            }


        };

        lvTimeTable.post(new Runnable() {
            @Override
            public void run() {
                lvTimeTable.setSelectionFromTop(currentDateIndex, lvTimeTable.getHeight()/2);
            }
        });

        lvTimeTable.setAdapter(timeTableAdapter);

        items = DbManager.getInstance().getAllRegionNames();
        regions = DbManager.getInstance().getAllRegions();
        regionMap.clear();
        for (Region r : regions) {
            regionMap.put(r.getName(), r.getNameInBangla());
        }
        SpinnerAdapter adapter = new ArrayAdapter<String>(this, R.layout.sehri_ifter_time_actionbar_spinner_dropdown_item, items) {


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setText(Utilities.getBanglaText(regionMap.get(items[position]), getBaseContext()));
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                v.setBackgroundColor(getResources().getColor(R.color.ActionBar_Navigation));
                ((TextView) v).setText(Utilities.getBanglaText(regionMap.get(items[position]), getBaseContext()));
                return v;
            }
        };

        ActionBar.OnNavigationListener callback = new ActionBar.OnNavigationListener() {

            String[] dropDownItems = DbManager.getInstance().getAllRegionNames();
            List<Region> regions = DbManager.getInstance().getAllRegions();


            @Override
            public boolean onNavigationItemSelected(int position, long id) {

                timeTables.clear();
                Region region = UIUtils.getSelectedLocation(regions, dropDownItems[position]);
                List<TimeTable> tempTimeTableList = DbManager.getInstance().getAllTimeTables();

                if (region.isPositive()) {
                    for (TimeTable timeTable : tempTimeTableList) {
                        timeTable.setSehriTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTable, getBaseContext(), true), getBaseContext()).toString());
                        timeTable.setIfterTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTable, getBaseContext(), false), getBaseContext()).toString());
                        timeTables.add(timeTable);
                    }

                } else {
                    for (TimeTable timeTable : tempTimeTableList) {
                        timeTable.setSehriTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTable, getBaseContext(), true), getBaseContext()).toString());
                        timeTable.setIfterTime(Utilities.getBanglaText(UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTable, getBaseContext(), false), getBaseContext()).toString());
                        timeTables.add(timeTable);
                    }
                }
                timeTableAdapter.notifyDataSetChanged();
                return true;
            }

        };


        // Action Bar
        ActionBar actions = getSupportActionBar();
        actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actions.setDisplayShowTitleEnabled(false);
        actions.setListNavigationCallbacks(adapter, callback);
        actions.setSelectedNavigationItem(Helper.getArrayIndex(items, preferenceHelper.getString(Constants.PREF_KEY_LOCATION, Constants.DEFAULT_REGION)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sehri_ifter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
