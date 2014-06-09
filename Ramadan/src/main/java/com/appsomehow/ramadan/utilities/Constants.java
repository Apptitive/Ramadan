package com.appsomehow.ramadan.utilities;

import android.content.Context;

import com.appsomehow.ramadan.R;

/**
 * Created by Sharif on 5/28/2014.
 */
public final class Constants {
    public static final String DEFAULT_REGION = "Dhaka";
    public static final String KEY_RINGTONE_NAME = "pref_key_alarm_ringtone";
    public static final String PREF_KEY_LOCATION = "prep_key_location";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_HOUR_MINUTE = "dd/MM/yyyy HH:mm";
    public static final String IS_DB_CREATED = "key_db_creation";
    public static final String PREF_ALARM_DATE="key_alarm_date";
    public static final String PREF_ALARM_HOUR="key_alarm_hour";
    public static final String PREF_ALARM_MINUT="key_alarm_minute";

    public final class topic {
        public static final String EXTRA_TITLE = "_title";
        public static final String EXTRA_PARCELABLE_LIST = "_parcelable";
        public static final String EXTRA_VIEWING_NOW = "_viewingNow";
        public static final String EXTRA_ICON_ID = "_iconId";
        public static final String EXTRA_DATA_FILE = "_dataFile";
    }

    public final class detail {
        public static final int VIEW_TYPE_TEXT_ONLY = 0;
        public static final int VIEW_TYPE_BULLET = 1;
        public static final int VIEW_TYPE_HEADER_ONLY = 2;
        public static final int VIEW_TYPE_ARABIC = 3;
        public static final int VIEW_TYPE_ARABIC_BULLET = 4;
    }
}
