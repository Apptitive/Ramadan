package com.appsomehow.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appsomehow.ramadan.model.Topic;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.Utilities;
import com.appsomehow.ramadan.views.BanglaTextView;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

import java.util.ArrayList;


public class DetailsActivity extends ActionBarActivity implements DetailsFragment.DetailProvider{

    private int fileResId, iconDrawableId, topicPosition;
    private Topic topicInView;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ArrayList<Topic> topics;
    private ListView listViewDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);

        topics = getIntent().getParcelableArrayListExtra(Constants.topic.EXTRA_PARCELABLE_LIST);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            topicPosition = extras.getInt(Constants.topic.EXTRA_VIEWING_NOW);
            iconDrawableId = extras.getInt(Constants.topic.EXTRA_ICON_ID);
            fileResId = extras.getInt(Constants.topic.EXTRA_DATA_FILE);
        }
        topicInView = topics.get(topicPosition);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_Green_Ramadan)));
        actionBar.setTitle(Utilities.getBanglaSpannableString(topicInView.getHeader(), this));
        actionBar.setIcon(getResources().getDrawable(iconDrawableId));
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_details);

        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        listViewDrawer = (ListView) findViewById(R.id.listView_drawer);

        listViewDrawer.setAdapter(new ArrayAdapter<Topic>(this, R.layout.drawer_layout,
                topics) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                BanglaTextView btv;
                if(convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.drawer_layout, parent, false);
                }
                btv = (BanglaTextView) convertView.findViewById(R.id.btv_nav);
                btv.setBanglaText(getItem(position).getHeader());
                return convertView;
            }
        });

        listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                topicInView = topics.get(position);
                actionBar.setTitle(Utilities.getBanglaSpannableString(topicInView.getHeader(), DetailsActivity.this));
                DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_details);
                detailsFragment.changeTopic(topicInView);
                item.setSelected(true);
                drawerLayout.closeDrawer(listViewDrawer);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_drawer:
                if (drawerLayout.isDrawerOpen(listViewDrawer))
                    drawerLayout.closeDrawer(listViewDrawer);
                else drawerLayout.openDrawer(listViewDrawer);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(title.toString(), Utilities.getFont(this), -1));
    }

    public Topic getTopic() {
        return topicInView;
    }

    public int getFileResId() {
        return fileResId;
    }
}
