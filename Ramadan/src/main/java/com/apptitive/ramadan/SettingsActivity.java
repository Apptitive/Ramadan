package com.apptitive.ramadan;

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

import com.apptitive.ramadan.helper.DbManager;
import com.apptitive.ramadan.utilities.AboutUsDialog;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.PreferenceHelper;
import com.apptitive.ramadan.utilities.Utilities;
import com.google.analytics.tracking.android.EasyTracker;

import static android.preference.Preference.OnPreferenceChangeListener;


public class SettingsActivity extends PreferenceActivity {
    private PreferenceCategory categoryAlarm;
    private PreferenceCategory categoryLocation;
    private PreferenceCategory categoryAboutUs;
    private ListPreference preferenceLocation;
    private static Context settingsActivity;
    private Preference preferenceAboutUs;
    private RingtonePreference preferenceRingtone;
    private CheckBoxPreference preferenceVibrate;
    private static String[] entries;

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbManager.init(this);
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

        entries = DbManager.getInstance().getAllBanglaRegionNames();

        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.pref_key_location));
        if (listPreference != null) {
            String[] englishRegionNames = DbManager.getInstance().getAllRegionNames();
            listPreference.setEntries(Utilities.getBanglaSpannableStrings(DbManager.getInstance().getAllBanglaRegionNames(), this));
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
        preferenceRingtone = (RingtonePreference) findPreference(getString(R.string.pref_key_alarm_ringtone));
        preferenceVibrate = (CheckBoxPreference) findPreference(getString(R.string.pref_key_alarm_vibrate));
    }

    private void setBanglaTextToView() {
        categoryAlarm.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_alarm), this));
        categoryLocation.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_location), this));
        categoryAboutUs.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_about_us), this));
        preferenceLocation.setTitle(Utilities.getBanglaSpannableString(getString(R.string.title_location_setting), this));
        preferenceAboutUs.setSummary(Utilities.getBanglaSpannableString(getString(R.string.title_about_us), this));
        preferenceRingtone.setTitle(Utilities.getBanglaSpannableString(getString(R.string.pref_title_ringtone), this));
        preferenceVibrate.setTitle(Utilities.getBanglaSpannableString(getString(R.string.pref_title_vibrate), this));

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
                preference.setSummary(Utilities.getBanglaSpannableString(entries[index], settingsActivity));
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
