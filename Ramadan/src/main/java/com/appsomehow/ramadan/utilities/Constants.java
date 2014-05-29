package com.appsomehow.ramadan.utilities;

import android.content.Context;

import com.appsomehow.ramadan.R;

/**
 * Created by Sharif on 5/28/2014.
 */
public final class Constants {
    public static final String KEY_RINGTON_NAME = "key_rington_name";
    public static final String PREF_KEY_LOCATION = "prep_key_location";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_HOUR_MINUTE = "dd/MM/yyyy HH:mm";

    public static final StringBuilder banglaReplaceCharacter(Context context, String word) {
        String[] REPLACEMENT = new String[Character.MAX_VALUE + 1];


        for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
            REPLACEMENT[i] = Character.toString(Character.toLowerCase((char) i));
        }
        REPLACEMENT['0'] = context.getString(R.string.bangla_character_zero);
        REPLACEMENT['1'] = context.getString(R.string.bangla_character_one);
        REPLACEMENT['2'] = context.getString(R.string.bangla_character_two);
        REPLACEMENT['3'] = context.getString(R.string.bangla_character_three);
        REPLACEMENT['4'] = context.getString(R.string.bangla_character_four);
        REPLACEMENT['5'] = context.getString(R.string.bangla_character_five);
        REPLACEMENT['6'] = context.getString(R.string.bangla_character_six);
        REPLACEMENT['7'] = context.getString(R.string.bangla_character_seven);
        REPLACEMENT['8'] = context.getString(R.string.bangla_character_eight);
        REPLACEMENT['9'] = context.getString(R.string.bangla_character_nine);

        StringBuilder sb = new StringBuilder(word.length());
        for (int i = 0; i < word.length(); i++)
            sb.append(REPLACEMENT[word.charAt(i)]);

        return sb;

    }

}
