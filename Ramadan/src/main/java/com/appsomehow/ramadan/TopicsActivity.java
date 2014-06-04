package com.appsomehow.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.appsomehow.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;


public class TopicsActivity extends ActionBarActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_White_Ramadan)));
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(getString(R.string.activity_saom), Utilities.getFont(this), -1));
        actionBar.setIcon(getResources().getDrawable(R.drawable.ic_saom));
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_topics);
    }
}
