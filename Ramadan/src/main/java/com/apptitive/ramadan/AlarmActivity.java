package com.apptitive.ramadan;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.apptitive.ramadan.helper.DbManager;
import com.apptitive.ramadan.model.Region;
import com.apptitive.ramadan.model.TimeTable;
import com.apptitive.ramadan.receiver.AlarmReceiver;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.InputFilterMinMax;
import com.apptitive.ramadan.utilities.PreferenceHelper;
import com.apptitive.ramadan.utilities.UIUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sharif on 7/1/2014.
 */
public class AlarmActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Button buttonDone;
    private CompoundButton switchIftar, switchSehri;
    private EditText etSehriHour, etSehriMinute, etIftarHour, etIftarMinute;
    private PreferenceHelper preferenceHelper;
    private boolean isSheri, isIftar;
    private List<TimeTable> timeTables;
    private String calculatedSehriTime;
    private String calculatedIftarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alarm_window);
        preferenceHelper = new PreferenceHelper(this);
        DbManager.init(this);
        initViews();
        initListeners();
        setUpText();

        isIftar = preferenceHelper.getBoolean(Constants.PREF_SWITCH_IFTAR);
        isSheri = preferenceHelper.getBoolean(Constants.PREF_SWITCH_SEHRI);

        switchIftar.setChecked(isIftar);
        switchSehri.setChecked(isSheri);
        sehriEnabled(isSheri);
        iftarEnabled(isIftar);
        getTodaysSehriIftarTime();
    }

    private void setUpText() {
        etSehriHour.setText(preferenceHelper.getString(Constants.SEHRI_HOUR, "hh"));
        etSehriMinute.setText(preferenceHelper.getString(Constants.SEHRI_MINUTE, "mm"));
        etIftarHour.setText(preferenceHelper.getString(Constants.IFTAR_HOUR, "hh"));
        etIftarMinute.setText(preferenceHelper.getString(Constants.IFTAR_MINUTE, "mm"));
    }

    private void getTodaysSehriIftarTime() {
        timeTables = DbManager.getInstance().getAllTimeTables();

        try {
            Region region = UIUtils.getSelectedLocation(DbManager.getInstance().getAllRegions(), preferenceHelper.getString(Constants.PREF_KEY_LOCATION, Constants.DEFAULT_REGION));

            if (region != null) {
                try {
                    if (region.isPositive()) {
                        calculatedSehriTime = UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTables, false, true);
                        calculatedIftarTime = UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTables, false, false);
                    } else {
                        calculatedSehriTime = UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTables, false, true);
                        calculatedIftarTime = UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTables, false, false);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
    }

    private void initViews() {
        buttonDone = (Button) findViewById(R.id.done);
        switchIftar = (CompoundButton) findViewById(R.id.switch_iftar);
        switchSehri = (CompoundButton) findViewById(R.id.switch_sehri);
        etSehriHour = (EditText) findViewById(R.id.et_sehri_hour);
        etSehriMinute = (EditText) findViewById(R.id.et_sehri_minute);
        etIftarHour = (EditText) findViewById(R.id.et_iftar_hour);
        etIftarMinute = (EditText) findViewById(R.id.et_iftar_minute);
        etIftarMinute.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        etSehriMinute.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
    }

    private void initListeners() {
        buttonDone.setOnClickListener(this);
        switchIftar.setOnCheckedChangeListener(this);
        switchSehri.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {


        if (isIftar) {

            if (isEmpty(false)) {
                finish();
                return;
            }
            try {
                setAlarm(calculatedIftarTime, Integer.valueOf(etIftarHour.getText().toString()), Integer.valueOf(etIftarMinute.getText().toString()), Constants.IFTAR_REQUEST_CODE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (isSheri) {
            if (isEmpty(true)) {
                finish();
                return;
            }
            try {
                setAlarm(calculatedSehriTime, Integer.valueOf(etSehriHour.getText().toString()), Integer.valueOf(etSehriMinute.getText().toString()), Constants.SEHRI_REQUEST_CODE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    public boolean isEmpty(boolean isSehriEmpty) {
        if (isSehriEmpty) {
            if (TextUtils.isEmpty(etSehriMinute.getText().toString()) || TextUtils.isEmpty(etSehriHour.getText().toString())) {
                return true;
            }
        } else if (!isSehriEmpty) {
            if (TextUtils.isEmpty(etIftarMinute.getText().toString()) || TextUtils.isEmpty(etIftarHour.getText().toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switch_iftar:
                preferenceHelper.setBoolean(Constants.PREF_SWITCH_IFTAR, isChecked);
                isIftar = isChecked;
                iftarEnabled(isChecked);
                break;
            case R.id.switch_sehri:
                sehriEnabled(isChecked);
                preferenceHelper.setBoolean(Constants.PREF_SWITCH_SEHRI, isChecked);
                isSheri = isChecked;
                break;

        }
    }

    private void sehriEnabled(boolean isChecked) {
        etSehriHour.setEnabled(isChecked);
        etSehriMinute.setEnabled(isChecked);
    }

    private void iftarEnabled(boolean isChecked) {
        etIftarHour.setEnabled(isChecked);
        etIftarMinute.setEnabled(isChecked);
    }


    public void setAlarm(String calculatedIftarTime, int hourOfDay, int hourOfMinute, int requestCode) throws ParseException {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(UIUtils.simpleDateTimeFormat.parse(calculatedIftarTime));
        calendar.add(Calendar.HOUR_OF_DAY, -hourOfDay);
        calendar.add(Calendar.MINUTE, -hourOfMinute);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), setUpAlarmType(PendingIntent.FLAG_ONE_SHOT, requestCode));

        if (requestCode == Constants.SEHRI_REQUEST_CODE) {
            preferenceHelper.setString(Constants.SEHRI_HOUR, "" + calendar.get(Calendar.HOUR_OF_DAY));
            preferenceHelper.setString(Constants.SEHRI_MINUTE, "" + calendar.get(Calendar.MINUTE));
        } else if (requestCode == Constants.IFTAR_REQUEST_CODE) {
            preferenceHelper.setString(Constants.IFTAR_HOUR, "" + calendar.get(Calendar.HOUR_OF_DAY));
            preferenceHelper.setString(Constants.IFTAR_MINUTE, "" + calendar.get(Calendar.MINUTE));
        }

    }

    private PendingIntent setUpAlarmType(int flag, int requestCode) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        return PendingIntent.getBroadcast(this, requestCode,
                intent, flag);
    }
}
