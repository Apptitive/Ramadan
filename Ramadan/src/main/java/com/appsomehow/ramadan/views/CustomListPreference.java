package com.appsomehow.ramadan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.preference.ListPreference;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

/**
 * TODO: document your custom view class.
 */
public class CustomListPreference extends ListPreference {


    public CustomListPreference(Context context) {
        super(context);
    }

    public CustomListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView txtTittle = (TextView) view.findViewById(android.R.id.title);
        txtTittle.setTypeface(Utilities.getFont(getContext()));
        txtTittle.setText(AndroidCustomFontSupport.getCorrectedBengaliFormat("", Utilities.getFont(getContext()), -1));
    }
}
