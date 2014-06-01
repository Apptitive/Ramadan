package com.appsomehow.ramadan;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.appsomehow.ramadan.utilities.Utilities;
import com.appsomehow.ramadan.views.JustifiedTextView;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;


public class SaomActivity extends ActionBarActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_Green_Ramadan)));
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(getString(R.string.activity_saom), Utilities.getFont(this), -1));
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_saom);
    }
}
