package com.apptitive.ramadan;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.apptitive.ramadan.R;
import com.google.analytics.tracking.android.EasyTracker;

public class BaseActionBar extends ActionBarActivity {

  @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
