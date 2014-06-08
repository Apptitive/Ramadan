package com.appsomehow.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorManager;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.Window;

import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;


public class TopicsActivity extends ActionBarActivity {

    private int topicResId;
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
            topicResId = extras.getInt(Constants.topic.EXTRA_DATA_FILE);
        }

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_White_Ramadan)));
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(title, Utilities.getFont(this), -1));
        actionBar.setIcon(getResources().getDrawable(iconDrawableId));
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_topics);
    }

    public int getTopicResId() {
        return topicResId;
    }
}
