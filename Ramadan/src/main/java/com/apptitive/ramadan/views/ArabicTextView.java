package com.apptitive.ramadan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.apptitive.ramadan.R;
import com.apptitive.ramadan.utilities.Utilities;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

/**
 * Created by Iftekhar on 6/9/2014.
 */
public class ArabicTextView extends JustifiedTextView {
    TypedArray typedArray;
    Typeface myTypeface;
    String arabicText;
    String fontName;
    boolean justify;

    public ArabicTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public ArabicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ArabicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setArabicText(String arabicText) {
        setBanglaSupportedText(arabicText, justify);
        if (typedArray != null) {
            typedArray.recycle();
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        if (attrs != null) {
            try {
                typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
                fontName = typedArray.getString(R.styleable.CustomTextView_fontName);
                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                arabicText = typedArray.getString(R.styleable.CustomTextView_banglaText);
                justify = Boolean.parseBoolean(typedArray.getString(R.styleable.CustomTextView_justify));

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (fontName != null) {
                setTypeface(myTypeface);
            }

            setBanglaSupportedText(arabicText, justify);

            typedArray.recycle();
        }
    }

    private void setBanglaSupportedText(String arabicText, boolean justify) {
        if (arabicText != null) {
            setText(Utilities.isBuildAboveThirteen() ? arabicText : AndroidCustomFontSupport.getCorrectedBengaliFormat(arabicText, myTypeface, -1));
        }
    }
}
