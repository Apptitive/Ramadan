package com.apptitive.ramadan;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apptitive.ramadan.model.Topic;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.Utilities;
import com.apptitive.ramadan.views.BanglaTextView;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

import java.util.ArrayList;


public class DetailsActivity extends BaseActionBar implements DetailsFragment.DetailProvider {

    private int fileResId, iconDrawableId, topicPosition;
    private Topic topicInView;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ArrayList<Topic> topics;
    private ArrayAdapter<Topic> drawerListAdapter;
    private ListView listViewDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);

        topics = getIntent().getParcelableArrayListExtra(Constants.topic.EXTRA_PARCELABLE_LIST);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
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

        drawerListAdapter = new ArrayAdapter<Topic>(this, R.layout.drawer_layout,
                topics) {
            @Override
            public int getViewTypeCount() {
                if (getCount() != 0)
                    return getCount();
                return 1;
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                BanglaTextView btv;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.drawer_layout, parent, false);
                }
                btv = (BanglaTextView) convertView.findViewById(R.id.btv_nav);
                btv.setBanglaText(getItem(position).getHeader());
                if (position == topics.indexOf(topicInView))
                    convertView.setBackgroundColor(getResources().getColor(R.color.ActionBar_Navigation));
                return convertView;
            }
        };
        listViewDrawer.setAdapter(drawerListAdapter);

        listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                topicInView = topics.get(position);
                if (TextUtils.isEmpty(topicInView.getDetailUri().toString())) {
                    actionBar.setTitle(Utilities.getBanglaSpannableString(topicInView.getHeader(), DetailsActivity.this));
                    DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_details);
                    detailsFragment.changeTopic(topicInView);
                    listViewDrawer.setAdapter(drawerListAdapter);
                    drawerLayout.closeDrawer(listViewDrawer);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(topicInView.getDetailUri());
                    startActivity(intent);
                }
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(listViewDrawer))
            drawerLayout.closeDrawer(listViewDrawer);
        else
            super.onBackPressed();
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
