package com.appsomehow.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.helper.Helper;
import com.appsomehow.ramadan.model.Region;
import com.appsomehow.ramadan.model.TimeTable;
import com.appsomehow.ramadan.table.helper.TableAdapter;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.PreferenceHelper;
import com.appsomehow.ramadan.utilities.UIUtils;
import com.appsomehow.ramadan.utilities.Utilities;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SehriIfterTimeActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private TableAdapter baseTableAdapter;
    private TableFixHeaders tableFixHeaders;
    private List<TimeTable> timeTables;
    private Map<String,String> regionMap;
    private String[] items;
    private List<Region> regions;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        regionMap = new HashMap<String, String>();
        preferenceHelper = new PreferenceHelper(this);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_White_Ramadan)));
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_sehri_ifter_time);
        timeTables = DbManager.getInstance().getAllTimeTables();
        tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
        baseTableAdapter = new TableAdapter(this, timeTables){
            @Override
            public View getView(int row, int column, View convertView, ViewGroup parent) {
                View v =  super.getView(row, column, convertView, parent);
                if (UIUtils.getCurrentDateIndex() < 100 && row == UIUtils.getCurrentDateIndex() +1){
                    v.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                return v;
            }
        };
        tableFixHeaders.setAdapter(baseTableAdapter);

        items = DbManager.getInstance().getAllRegionNames();
        regions = DbManager.getInstance().getAllRegions();
        regionMap.clear();
        for (Region r: regions){
            regionMap.put(r.getName(),r.getNameInBangla());
        }
        SpinnerAdapter adapter = new ArrayAdapter<String>(this, R.layout.sehri_ifter_time_actionbar_spinner_dropdown_item, items){


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
                        //timeTable.setSehriTime(UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTable, getBaseContext(), true));
                        //timeTable.setIfterTime(UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTable, getBaseContext(), false));
                        timeTables.add(timeTable);
                    }

                } else {
                    for (TimeTable timeTable : tempTimeTableList) {
                        //timeTable.setSehriTime(UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTable, getBaseContext(), true));
                        //timeTable.setIfterTime(UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTable, getBaseContext(), false));
                        timeTables.add(timeTable);
                    }
                }
                baseTableAdapter.setUpdatedListItems(timeTables);
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

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sehri_ifter_time, menu);
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
