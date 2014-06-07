package com.appsomehow.ramadan.views;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.appsomehow.ramadan.utilities.Utilities;

/**
 * Created by Sharif on 6/7/2014.
 */
public class CustomCheckBoxPreferennce extends CheckBoxPreference {


   public CustomCheckBoxPreferennce(Context context) {
        super(context);
    }

    public CustomCheckBoxPreferennce(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCheckBoxPreferennce(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView txtTitle = (TextView) view.findViewById(android.R.id.title);
        txtTitle.setTypeface(Utilities.getFont(getContext()));
    }

}