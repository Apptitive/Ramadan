package com.appsomehow.ramadan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.appsomehow.ramadan.R;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

/**
 * Created by Sharif on 5/26/2014.
 */
public class BanglaTextView extends TextView {
    TypedArray typedArray;
    Typeface myTypeface;
    String banglaText;
    String fontName;


    public BanglaTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public BanglaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BanglaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setBanglaText(String banglaText) {

        setBanglaSupportedText(banglaText);
        if (typedArray != null) {
            typedArray.recycle();
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        if (attrs != null) {
            try {
                typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomeTextView);
                fontName = typedArray.getString(R.styleable.CustomeTextView_fontName);
                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                banglaText = typedArray.getString(R.styleable.CustomeTextView_banglaText);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (fontName != null) {
                setTypeface(myTypeface);
            }
            setBanglaSupportedText(banglaText);
            typedArray.recycle();
        }
    }

    private void setBanglaSupportedText(String banglaText) {
        if (banglaText != null) {
            setText(AndroidCustomFontSupport.getCorrectedBengaliFormat(banglaText, myTypeface, -1));
        }
    }

}
