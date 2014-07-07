package com.apptitive.ramadan;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.apptitive.ramadan.adapter.SpinnerAdapter;
import com.apptitive.ramadan.helper.DbManager;
import com.apptitive.ramadan.model.Region;
import com.apptitive.ramadan.model.TimeTable;
import com.apptitive.ramadan.receiver.AlarmReceiver;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.PreferenceHelper;
import com.apptitive.ramadan.utilities.UIUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sharif on 7/1/2014.
 */
public class AlarmActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private TextView textDone, textCancel;
    private CompoundButton switchIftar, switchSehri;
    private PreferenceHelper preferenceHelper;
    private boolean isSheri, isIftar;
    private List<TimeTable> timeTables;
    private String calculatedSehriTime;
    private String calculatedIftarTime;
    private Spinner spinnerIftarTime, spinnerSehriTime;
    private int iftarRowPosition, sehriRowPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        preferenceHelper = new PreferenceHelper(this);
        DbManager.init(this);
        initViews();
        initListeners();

        isIftar = preferenceHelper.getBoolean(Constants.PREF_SWITCH_IFTAR);
        isSheri = preferenceHelper.getBoolean(Constants.PREF_SWITCH_SEHRI);
        iftarRowPosition = preferenceHelper.getInt(Constants.IFTAR_ROW_POSITION);
        sehriRowPosition = preferenceHelper.getInt(Constants.SEHRI_ROW_POSITION);
        spinnerIftarTime.setSelection(iftarRowPosition);
        spinnerSehriTime.setSelection(sehriRowPosition);
        switchIftar.setChecked(isIftar);
        switchSehri.setChecked(isSheri);
        sehriEnabled(isSheri);
        iftarEnabled(isIftar);
        getTodaysSehriIftarTime();
    }


    private void getTodaysSehriIftarTime() {
        timeTables = DbManager.getInstance().getAllTimeTables();

        try {
            Region region = UIUtils.getSelectedLocation(DbManager.getInstance().getAllRegions(), preferenceHelper.getString(Constants.PREF_KEY_LOCATION, Constants.DEFAULT_REGION));
            if (region != null) {
                if (region.isPositive()) {
                    calculatedSehriTime = UIUtils.getSehriIftarTime(region.getIntervalSehri(), timeTables, false, true);
                    calculatedIftarTime = UIUtils.getSehriIftarTime(region.getIntervalIfter(), timeTables, false, false);
                } else {
                    calculatedSehriTime = UIUtils.getSehriIftarTime(-region.getIntervalSehri(), timeTables, false, true);
                    calculatedIftarTime = UIUtils.getSehriIftarTime(-region.getIntervalIfter(), timeTables, false, false);
                }
            }
        } catch (Exception e) {
        }
    }

    private void initViews() {
        textDone = (TextView) findViewById(R.id.tv_dialog_action_ok);
        textCancel = (TextView) findViewById(R.id.tv_dialog_action_cancel);
        switchIftar = (CompoundButton) findViewById(R.id.switch_iftar_alarm);
        switchSehri = (CompoundButton) findViewById(R.id.switch_sehri_alarm);
        spinnerIftarTime = (Spinner) findViewById(R.id.sp_iftar_time);
        spinnerSehriTime = (Spinner) findViewById(R.id.sp_sehri_time);


        SpinnerAdapter iftarAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.iftar_time));
        iftarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIftarTime.setAdapter(iftarAdapter);

        SpinnerAdapter sehriAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sehri_time));
        sehriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSehriTime.setAdapter(sehriAdapter);

    }

    private void initListeners() {
        textDone.setOnClickListener(this);
        textCancel.setOnClickListener(this);
        switchIftar.setOnCheckedChangeListener(this);
        switchSehri.setOnCheckedChangeListener(this);
        spinnerSehriTime.setOnItemSelectedListener(this);
        spinnerIftarTime.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_action_cancel:
                break;
            case R.id.tv_dialog_action_ok:
                if (isIftar) {
                    int[] iftarTimes = new int[]{0, 5, 10, 30};
                    setUpSehriIftarAlarm(calculatedIftarTime, 0, iftarTimes[iftarRowPosition], Constants.IFTAR_REQUEST_CODE);
                }
                if (isSheri) {
                    int[][] sehriTimes = new int[][]{{0, 30}, {1, 0}, {1, 30}, {2, 0}};
                    setUpSehriIftarAlarm(calculatedSehriTime, sehriTimes[sehriRowPosition][0], sehriTimes[sehriRowPosition][1], Constants.SEHRI_REQUEST_CODE);
                }
                break;
        }
        finish();

    }


    private void setUpSehriIftarAlarm(String calculatedTime, int hour, int minute, int requestCode) {
        try {
            if (calculatedTime.equals("0:00")) {
                return;
            }
            setAlarm(calculatedTime, hour, minute, requestCode);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switch_iftar_alarm:
                preferenceHelper.setBoolean(Constants.PREF_SWITCH_IFTAR, isChecked);
                isIftar = isChecked;
                iftarEnabled(isChecked);
                break;
            case R.id.switch_sehri_alarm:
                sehriEnabled(isChecked);
                preferenceHelper.setBoolean(Constants.PREF_SWITCH_SEHRI, isChecked);
                isSheri = isChecked;
                break;

        }
    }

    private void sehriEnabled(boolean isChecked) {
        spinnerSehriTime.setEnabled(isChecked);
    }

    private void iftarEnabled(boolean isChecked) {
        spinnerIftarTime.setEnabled(isChecked);
    }


    public void setAlarm(String calculatedIftarTime, int hourOfDay, int hourOfMinute, int requestCode) throws ParseException {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(UIUtils.simpleDateTimeFormat.parse(calculatedIftarTime));
        calendar.add(Calendar.HOUR_OF_DAY, -hourOfDay);
        calendar.add(Calendar.MINUTE, -hourOfMinute);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), setUpAlarmType(PendingIntent.FLAG_ONE_SHOT, requestCode));
    }

    private PendingIntent setUpAlarmType(int flag, int requestCode) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        return PendingIntent.getBroadcast(this, requestCode,
                intent, flag);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_iftar_time:
                iftarRowPosition = position;
                preferenceHelper.setInt(Constants.IFTAR_ROW_POSITION, position);
                break;
            case R.id.sp_sehri_time:
                sehriRowPosition = position;
                preferenceHelper.setInt(Constants.SEHRI_ROW_POSITION, position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
