package com.apptitive.ramadan.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.apptitive.ramadan.receiver.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sharif on 5/26/2014.
 */
public class Alarm {
    private Context context;
    private AlarmManager alarmManager;

    public Alarm(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }


    public void setOneTimeAlarm(int hourOfDay, int hourOfMinute) throws ParseException {
        Calendar dateTime = getCalculatedDateAndTime(hourOfDay, hourOfMinute);
        alarmManager.set(AlarmManager.RTC_WAKEUP, dateTime.getTimeInMillis(), setUpAlarmType(PendingIntent.FLAG_ONE_SHOT));
        saveSelectedDateTime(dateTime.getTime());
    }

    private void saveSelectedDateTime(Date time) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        String dateTime = new SimpleDateFormat(Constants.DATE_FORMAT_12_HOUR).format(time);
        preferenceHelper.setString(Constants.PREF_ALARM_DATE, dateTime);
    }

    private Calendar getCalculatedDateAndTime(int hourofDay, int hourOfMinute) throws ParseException {
        Calendar calendar =Calendar.getInstance();
        Date currentDateTime = UIUtils.simpleDateTimeFormat.parse(UIUtils.simpleDateTimeFormat
                .format(calendar.getTime()));

        calendar.set(Calendar.HOUR_OF_DAY,hourofDay);
        calendar.set(Calendar.MINUTE,hourOfMinute);
        Date selectedDateTime =UIUtils.simpleDateTimeFormat.parse(UIUtils.simpleDateTimeFormat.format(calendar.getTime()));
        calendar.setTime(selectedDateTime);
        if (selectedDateTime.before(currentDateTime)) {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            return calendar;
        }
        return calendar;
    }


    private PendingIntent setUpAlarmType(int flag) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0,
                intent, flag);
    }

}
