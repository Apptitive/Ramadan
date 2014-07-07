package com.apptitive.ramadan.utilities;

/**
 * Created by Sharif on 5/28/2014.
 */
public final class Constants {
    public static final String DEFAULT_REGION = "Dhaka";
    public static final String KEY_RINGTONE_NAME = "pref_key_alarm_ringtone";
    public static final String PREF_KEY_LOCATION = "prep_key_location";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_HOUR_MINUTE_SECOND = "dd/MM/yyyy HH:mm:ss aa";
    public static final String DATE_FORMAT_12_HOUR = "dd-MM-yyyy hh:mm aa";
    public static final String IS_DB_CREATED = "key_db_creation";
    public static final String PREF_ALARM_DATE = "key_alarm_date";
    public static final String PREF_SWITCH_IFTAR="pref_switch_iftar";
    public static final String PREF_SWITCH_SEHRI="pref_switch_sehri";
    public static final int IFTAR_REQUEST_CODE=1;
    public static final int SEHRI_REQUEST_CODE=2;

    public static final String IFTAR_ROW_POSITION="iftar_row_position";
    public static final String SEHRI_ROW_POSITION="sehri_row_position";


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
        public static final int VIEW_TYPE_TEXT_BULLET_ALIGN = 3;
        public static final int VIEW_TYPE_ARABIC = 4;
        public static final int VIEW_TYPE_ARABIC_BULLET_ALIGN = 5;
    }
}
