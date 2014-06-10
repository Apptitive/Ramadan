package com.appsomehow.ramadan;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.utilities.AboutUsDialog;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.PreferenceHelper;
import com.appsomehow.ramadan.utilities.Utilities;

import static android.preference.Preference.OnPreferenceChangeListener;


public class SettingsActivity extends PreferenceActivity {
    private PreferenceCategory categoryAlarm;
    private PreferenceCategory categoryLocation;
    private PreferenceCategory categoryAboutUs;
    private ListPreference preferenceLocation;
    private static Context settingsActivity;
    private Preference preferenceAboutUs;
    private RingtonePreference prefereneRington;
    private CheckBoxPreference preferenceVibrat;
    private Preference alrmPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 11) {
            getActionBar().hide();
        }
        addPreferencesFromResource(R.xml.pref_general);
        setContentView(R.layout.activity_settings);
        settingsActivity = this;
        setupSimplePreferencesScreen();

        LinearLayout settings = (LinearLayout) findViewById(R.id.settings_back);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setupSimplePreferencesScreen() {
        // addPreferencesFromResource(R.xml.pref_general);
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.pref_key_location));
        if (listPreference != null) {
            String[] englishRegionNames = DbManager.getInstance().getAllRegionNames();
            listPreference.setEntries(Utilities.banglaSpannableStrings(DbManager.getInstance().getAllBanglaRegionNames(), this));
            listPreference.setEntryValues(englishRegionNames);
            listPreference.setDialogTitle(Utilities.getBanglaSpannableString(getString(R.string.title_location_setting), this));
        }
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_location)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_alarm_ringtone)));
        findViews();
        setBanglaTextToView();
    }

    private void findViews() {
        categoryAlarm = (PreferenceCategory) findPreference(getString(R.string.pref_key_alarm_settings));
        categoryLocation = (PreferenceCategory) findPreference(getString(R.string.pref_key_location_settings));
        categoryAboutUs = (PreferenceCategory) findPreference(getString(R.string.pref_key_about_us));
        preferenceLocation = (ListPreference) findPreference(getString(R.string.pref_key_location));
        preferenceAboutUs = (Preference) findPreference(getString(R.string.pref_key_preference_about_us));
        prefereneRington = (RingtonePreference) findPreference(getString(R.string.pref_key_alarm_ringtone));
        preferenceVibrat = (CheckBoxPreference) findPreference(getString(R.string.pref_key_alarm_vibrate));
        alrmPreference = (Preference) findPreference(getString(R.string.pref_key_alarm_time));
    }

    private void setBanglaTextToView() {
        categoryAlarm.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_alarm), this));
        categoryLocation.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_location), this));
        categoryAboutUs.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_about_us), this));
        preferenceLocation.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_location_setting), this));
        preferenceAboutUs.setSummary(Utilities.getBanglaSpannableString(getString(R.string.title_about_us), this));
        prefereneRington.setTitle(Utilities.getBanglaSpannableString(getString(R.string.pref_title_ringtone), this));
        preferenceVibrat.setTitle(Utilities.getBanglaSpannableString(getString(R.string.pref_title_vibrate), this));
        alrmPreference.setTitle(Utilities.getBanglaSpannableString(getString(R.string.alarm_time), this));

        PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        String dateTime = preferenceHelper.getString(Constants.PREF_ALARM_DATE, "");
        if (!dateTime.equals("")) {
            alrmPreference.setSummary(dateTime);
        } else {
            alrmPreference.setSummary(Utilities.getBanglaSpannableString(getString(R.string.alarm_time_off), this));
        }

        preferenceAboutUs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AboutUsDialog aboutUsDialog = new AboutUsDialog(SettingsActivity.this);
                aboutUsDialog.show();
                return true;
            }
        });
    }

    private static OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                String districtName = listPreference.getEntries()[index].toString();
                preference.setSummary(Utilities.getBanglaSpannableString(districtName, settingsActivity));
            } else if (preference instanceof RingtonePreference) {
                if (TextUtils.isEmpty(stringValue)) {
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        preference.setSummary(null);
                    } else {
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                preference.setSummary(Utilities.getBanglaSpannableString(stringValue, settingsActivity));
            }
            return true;
        }
    };

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), "")
        );
    }
}
