package com.appsomehow.ramadan;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appsomehow.ramadan.utilities.Utilities;
import com.appsomehow.ramadan.views.BanglaTextView;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

import uk.co.chrisjenx.paralloid.OnScrollChangedListener;


public class SaomActivity extends ActionBarActivity implements OnScrollChangedListener {

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ListView listViewDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.AB_Green_Ramadan)));
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(getString(R.string.saom), Utilities.getFont(this), -1));
        actionBar.setIcon(getResources().getDrawable(R.drawable.ic_saom));
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_saom);

        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        listViewDrawer = (ListView) findViewById(R.id.listview_drawer);

        listViewDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_layout,
                getResources().getStringArray(R.array.header_topic_saom)) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                BanglaTextView btv;
                if(convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.drawer_layout, parent, false);
                }
                btv = (BanglaTextView) convertView.findViewById(R.id.btv_nav);
                btv.setBanglaText(getItem(position));
                return convertView;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_drawer:
                int gravity = Gravity.RIGHT;
                if (drawerLayout.isDrawerOpen(gravity))
                    drawerLayout.closeDrawer(gravity);
                else drawerLayout.openDrawer(gravity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(Object who, int l, int t, int oldl, int oldt) {
        if (t < oldt)
            actionBar.hide();
        else if (t > oldt)
            actionBar.show();
        ;
    }

    @Override
    public void setTitle(CharSequence title) {
        actionBar.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(title.toString(), Utilities.getFont(this), -1));
    }
}
