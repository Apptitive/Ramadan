package com.appsomehow.ramadan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.preference.RingtonePreference;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

import org.w3c.dom.Text;

/**
 * TODO: document your custom view class.
 */
public class CustomRingtonPreference extends RingtonePreference {

    public CustomRingtonPreference(Context context) {
        super(context);

    }

    public CustomRingtonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomRingtonPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView textViewTitle = (TextView) view.findViewById(android.R.id.title);
        textViewTitle.setTypeface(Utilities.getFont(getContext()));
        textViewTitle.setText(AndroidCustomFontSupport.getCorrectedBengaliFormat("", Utilities.getFont(getContext()), -1));
    }
}
