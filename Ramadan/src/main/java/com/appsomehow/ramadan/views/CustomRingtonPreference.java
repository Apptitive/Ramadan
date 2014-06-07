package com.appsomehow.ramadan.views;

import android.content.Context;
import android.preference.RingtonePreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.appsomehow.ramadan.utilities.Utilities;

/**
 * Created by Sharif on 6/7/2014.
 */
public class CustomRingtonPreference extends RingtonePreference{
    public CustomRingtonPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomRingtonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRingtonPreference(Context context) {
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
