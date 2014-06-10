package com.appsomehow.ramadan.views;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.appsomehow.ramadan.utilities.Utilities;

/**
 * Created by Sharif on 6/7/2014.
 */
public class CustomListPreference extends ListPreference {
    public CustomListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView txtTitle = (TextView) view.findViewById(android.R.id.title);
        txtTitle.setTypeface(Utilities.getFont(getContext()));
        TextView txtSummary = (TextView) view.findViewById(android.R.id.summary);
        txtSummary.setTypeface(Utilities.getFont(getContext()));
    }
}
