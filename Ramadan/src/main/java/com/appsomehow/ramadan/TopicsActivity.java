package com.appsomehow.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;


public class TopicsActivity extends ActionBarActivity {

    private int headerArrayId, shortDescArrayId, fullTextArrayId;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);

        String title = getString(R.string.ramadan);
        int iconDrawableId = R.drawable.ic_ramadan;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString(Constants.topic.EXTRA_TITLE);
            iconDrawableId = extras.getInt(Constants.topic.EXTRA_ICON_ID);
            headerArrayId = extras.getInt(Constants.topic.EXTRA_HEADER_ARRAY_ID);
            shortDescArrayId = extras.getInt(Constants.topic.EXTRA_DESC_ARRAY_ID);
            fullTextArrayId = extras.getInt(Constants.topic.EXTRA_FULL_TEXT_ARRAY_ID);
        }

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_White_Ramadan)));
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(title, Utilities.getFont(this), -1));
        actionBar.setIcon(getResources().getDrawable(iconDrawableId));
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_topics);
    }

    public int getHeaderArrayId() {
        return headerArrayId;
    }

    public int getShortDescArrayId() {
        return shortDescArrayId;
    }

    public int getFullTextArrayId() {
        return fullTextArrayId;
    }
}
