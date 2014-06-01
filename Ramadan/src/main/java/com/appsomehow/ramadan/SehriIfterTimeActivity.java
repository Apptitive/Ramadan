package com.appsomehow.ramadan;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.model.Region;
import com.appsomehow.ramadan.model.TimeTable;
import com.appsomehow.ramadan.utilities.UIUtils;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import java.util.ArrayList;
import java.util.List;


public class SehriIfterTimeActivity extends ActionBarActivity {

    FamilyNexusAdapter baseTableAdapter;
    TableFixHeaders tableFixHeaders;
    List<TimeTable> timeTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sehri_ifter_time);

        timeTables = DbManager.getInstance().getAllTimeTables();

        tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
        baseTableAdapter = new FamilyNexusAdapter(this, timeTables);
        tableFixHeaders.setAdapter(baseTableAdapter);

        String[] items = DbManager.getInstance().getAllRegionNames();
        SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ActionBar.OnNavigationListener callback = new ActionBar.OnNavigationListener() {

            String[] dropDownItems = DbManager.getInstance().getAllRegionNames();
            List<Region> regions = DbManager.getInstance().getAllRegions();


            @Override
            public boolean onNavigationItemSelected(int position, long id) {
                // Do stuff when navigation item is selected
                timeTables.clear();

                Log.e("NavigationItemSelected", dropDownItems[position]); // Debug
                Region region = UIUtils.getSelectedLocation(regions, dropDownItems[position]);
                List<TimeTable> tempTimeTableList = DbManager.getInstance().getAllTimeTables();

                if (region.isPositive()) {
                    for (TimeTable timeTable : tempTimeTableList) {
                        timeTable.setSehriTime(UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTable, getBaseContext(), true));
                        timeTable.setIfterTime(UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTable, getBaseContext(), false));
                        timeTables.add(timeTable);
                    }

                } else {
                    for (TimeTable timeTable : tempTimeTableList) {
                        // timeTable.setSehriTime(UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTable, getBaseContext(), true));
                        // timeTable.setIfterTime(UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTable, getBaseContext(), false));
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

    public class FamilyNexusAdapter extends BaseTableAdapter {

        private List<TimeTable> timeTables;
        private final NexusTypes familys[];
        private final String headers[] = {
                "তারিখ",
                "সেহরী",
                "ইফতার",
                "রমজান",
        };

        private final int[] widths = {
                120,
                100,
                140,
                60,
        };
        private final float density;

        public FamilyNexusAdapter(Context context, List<TimeTable> timeTables) {
            this.timeTables = timeTables;
            familys = new NexusTypes[]{
                    new NexusTypes("Ifter & Sehri Time"),
            };

            density = context.getResources().getDisplayMetrics().density;
            for (TimeTable timeTable : timeTables) {
                familys[0].list.add(new Nexus(timeTable.getDateInBangla(), timeTable.getSehriTime(), timeTable.getIfterTime(), timeTable.getRojaCount()));
            }
        }

        public void setUpdatedListItems(List<TimeTable> ttList) {
            familys[0].list.clear();
            for (TimeTable tt : ttList) {
                Log.e("Table_row ", tt.getSehriTime() + "_" + tt.getIfterTime());
                familys[0].list.add(new Nexus(tt.getDateInBangla(), tt.getSehriTime(), tt.getIfterTime(), tt.getRojaCount()));
            }
            update();
        }

        @Override
        public int getRowCount() {
            return timeTables.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public View getView(int row, int column, View convertView, ViewGroup parent) {
            final View view;
            switch (getItemViewType(row, column)) {
                case 0:
                    view = getFirstHeader(row, column, convertView, parent);
                    break;
                case 1:
                    view = getHeader(row, column, convertView, parent);
                    break;
                case 2:
                    view = getFirstBody(row, column, convertView, parent);
                    break;
                case 3:
                    view = getBody(row, column, convertView, parent);
                    break;
                case 4:
                    view = getFamilyView(row, column, convertView, parent);
                    break;
                default:
                    throw new RuntimeException("wtf?");
            }
            return view;
        }

        private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
            return convertView;
        }

        private View getHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
            return convertView;
        }

        private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
            return convertView;
        }

        private View getBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
            return convertView;
        }

        private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_family, parent, false);
            }
            final String string;
            if (column == -1) {
                string = getFamily(row).name;
            } else {
                string = "";
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
            return convertView;
        }

        @Override
        public int getWidth(int column) {
            return Math.round(widths[column + 1] * density);
        }

        @Override
        public int getHeight(int row) {
            final int height;
            if (row == -1) {
                height = 35;
            } else if (isFamily(row)) {
                height = 25;
            } else {
                height = 45;
            }
            return Math.round(height * density);
        }

        @Override
        public int getItemViewType(int row, int column) {
            final int itemViewType;
            if (row == -1 && column == -1) {
                itemViewType = 0;
            } else if (row == -1) {
                itemViewType = 1;
            } else if (isFamily(row)) {
                itemViewType = 4;
            } else if (column == -1) {
                itemViewType = 2;
            } else {
                itemViewType = 3;
            }
            return itemViewType;
        }

        private boolean isFamily(int row) {
            int family = 0;
            while (row > 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            return row == 0;
        }

        private NexusTypes getFamily(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            return familys[family - 1];
        }

        private Nexus getDevice(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            family--;
            return familys[family].get(row + familys[family].size());
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }

        public void update() {
            notifyDataSetChanged();
        }
    }


    private class Nexus {
        private final String[] data;

        private Nexus(String dateInBangali, String sehriTime, String ifterTime, String rojaCount) {
            data = new String[]{
                    dateInBangali,
                    sehriTime,
                    ifterTime,
                    rojaCount,
            };
        }
    }


    private class NexusTypes {
        private final String name;
        private final List<Nexus> list;

        NexusTypes(String name) {
            this.name = name;
            list = new ArrayList<Nexus>();
        }

        public int size() {
            return list.size();
        }

        public Nexus get(int i) {
            return list.get(i);
        }
    }

}
