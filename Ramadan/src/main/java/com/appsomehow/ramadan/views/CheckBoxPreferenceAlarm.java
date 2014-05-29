package com.appsomehow.ramadan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.preference.CheckBoxPreference;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

/**
 * TODO: document your custom view class.
 */
public class CheckBoxPreferenceAlarm extends CheckBoxPreference {

    public CheckBoxPreferenceAlarm(Context context) {
        super(context);
    }

    public CheckBoxPreferenceAlarm(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxPreferenceAlarm(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView txtTitle = (TextView) view.findViewById(android.R.id.title);
        txtTitle.setTypeface(Utilities.getFont(getContext()));
      //  txtTitle.setText(AndroidCustomFontSupport.getCorrectedBengaliFormat("", Utilities.getFont(getContext()), -1));
    }

}
