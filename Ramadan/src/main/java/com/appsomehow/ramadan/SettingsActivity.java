package com.appsomehow.ramadan;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.SpannableString;
import android.text.TextUtils;

import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.utilities.Utilities;

import static android.preference.Preference.OnPreferenceChangeListener;


public class SettingsActivity extends PreferenceActivity {
    private PreferenceCategory categoryAlarm;
    private CheckBoxPreference preferenceAlarm;
    private PreferenceCategory categoryLocation;
    private PreferenceCategory categoryAboutUs;
    private ListPreference preferenceLocation;

    private static Context settingsActivity;
    private Preference preferenceAboutUs;
    private RingtonePreference prefereneRington;
    private CheckBoxPreference preferenceVibrat;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        settingsActivity = this;
        setupSimplePreferencesScreen();
    }


    private void setupSimplePreferencesScreen() {
        addPreferencesFromResource(R.xml.pref_general);
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.pref_key_location));
        if (listPreference != null) {
            String[] englishRegionNames = DbManager.getInstance().getAllRegionNames();
            SpannableString[] banglaRegionNames = Utilities.banglaSpannableStrings(DbManager.getInstance().getAllBanglaRegionNames(), this);
            listPreference.setEntries(banglaRegionNames);
            listPreference.setEntryValues(englishRegionNames);
            listPreference.setDialogTitle(Utilities.getBanglaText(getString(R.string.title_location_setting), this));
        }
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_location)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_alarm_rington)));
        findViews();
        setBanglaTextToView();
    }

    private void findViews() {
        categoryAlarm = (PreferenceCategory) findPreference(getString(R.string.pref_key_alarm_settings));
        preferenceAlarm = (CheckBoxPreference) findPreference(getString(R.string.pref_key_alarm));
        categoryLocation = (PreferenceCategory) findPreference(getString(R.string.pref_key_location_settings));
        categoryAboutUs = (PreferenceCategory) findPreference(getString(R.string.pref_key_about_us));
        preferenceLocation = (ListPreference) findPreference(getString(R.string.pref_key_location));
        preferenceAboutUs = (Preference) findPreference(getString(R.string.pref_key_preference_about_us));
        prefereneRington = (RingtonePreference) findPreference(getString(R.string.pref_key_alarm_rington));
        preferenceVibrat = (CheckBoxPreference) findPreference(getString(R.string.pref_key_alarm_vibrat));
    }

    private void setBanglaTextToView() {
        categoryAlarm.setTitle(Utilities.getBanglaText(getString(R.string.title_alarm), this));
        categoryLocation.setTitle(Utilities.getBanglaText(getString(R.string.title_location), this));
        categoryAboutUs.setTitle(Utilities.getBanglaText(getString(R.string.title_about_us), this));
        preferenceAlarm.setTitle(Utilities.getBanglaText(getString(R.string.title_alarm_setting), this));
        preferenceLocation.setTitle(Utilities.getBanglaText(getString(R.string.title_location_setting), this));
        preferenceAboutUs.setSummary(Utilities.getBanglaText(getString(R.string.title_about_us), this));
        prefereneRington.setTitle(Utilities.getBanglaText(getString(R.string.pref_title_ringtone), this));
        preferenceVibrat.setTitle(Utilities.getBanglaText(getString(R.string.pref_title_vibrate), this));
        preferenceAboutUs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getBaseContext(), AboutUsActivity.class));
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
                preference.setSummary(index >= 0 ? Utilities.getBanglaText(listPreference.getEntries()[index].toString(), settingsActivity) : null);
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
                preference.setSummary(Utilities.getBanglaText(stringValue, settingsActivity));
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
