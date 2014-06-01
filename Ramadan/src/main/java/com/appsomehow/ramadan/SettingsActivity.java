package com.appsomehow.ramadan;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
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
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.helper.DbManager;
import com.appsomehow.ramadan.model.Region;
import com.appsomehow.ramadan.utilities.Utilities;
import com.appsomehow.ramadan.views.CheckBoxPreferenceAlarm;
import com.dibosh.experiments.android.support.customfonthelper.AndroidCustomFontSupport;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupSimplePreferencesScreen();
    }


    private void setupSimplePreferencesScreen() {
        addPreferencesFromResource(R.xml.pref_general);
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.prep_key_location));
        if (listPreference != null) {
            String[] regionNames = DbManager.getInstance().getAllRegionNames();
            listPreference.setEntries(regionNames);
            listPreference.setEntryValues(regionNames);
        }
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prep_key_location)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_alarm_rington)));
        CheckBoxPreferenceAlarm checkBoxPreference =(CheckBoxPreferenceAlarm)findPreference(getString(R.string.pref_key_alarm));
        checkBoxPreference.setTitle(AndroidCustomFontSupport.getCorrectedBengaliFormat(getString(R.string.title_activity_settings), Utilities.getFont(this),-1));


    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
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
                preference.setSummary(stringValue);
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
