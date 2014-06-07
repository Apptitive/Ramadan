package com.appsomehow.ramadan.utilities;

import android.content.Context;

import com.appsomehow.ramadan.R;

/**
 * Created by Sharif on 5/28/2014.
 */
public final class Constants {
    public static final String DEFAULT_REGION = "Dhaka";
    public static final String KEY_RINGTON_NAME = "pref_key_alarm_rington";
    public static final String PREF_KEY_LOCATION = "prep_key_location";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_HOUR_MINUTE = "dd/MM/yyyy HH:mm";
    public static final String IS_DB_CREATED = "key_db_creation";

    public final class topic {
        public static final String EXTRA_TITLE = "_title";
        public static final String EXTRA_ICON_ID = "_iconId";
        public static final String EXTRA_HEADER_ARRAY_ID = "_header";
        public static final String EXTRA_DESC_ARRAY_ID = "_brief";
        public static final String EXTRA_FULL_TEXT_ARRAY_ID = "_fullText";
    }

    public final class detail {
        public static final int VIEW_TYPE_TEXT_ONLY = 0;
        public static final int VIEW_TYPE_BULLET = 1;
        public static final int VIEW_TYPE_HEADER_ONLY = 2;
    }
}
