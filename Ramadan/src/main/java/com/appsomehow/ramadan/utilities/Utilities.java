package com.appsomehow.ramadan.utilities;

import android.content.Context;
import android.graphics.Typeface;

import com.appsomehow.ramadan.R;

/**
 * Created by Sharif on 5/27/2014.
 */
public class Utilities {

    public static Typeface getFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/" + context.getString(R.string.font_solaimanlipi));
    }

}
