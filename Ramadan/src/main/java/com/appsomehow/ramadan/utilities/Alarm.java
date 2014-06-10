package com.appsomehow.ramadan.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.appsomehow.ramadan.receiver.AlarmReceiver;
import com.appsomehow.ramadan.receiver.NotificationCancelReceiver;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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


    public void setOneTimeAlarm(int hourOfDay, int hourOfMinute) {
        MutableDateTime dateTime = getCalculatedDateAndTime(hourOfDay, hourOfMinute);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dateTime.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, dateTime.getHourOfDay());
        calendar.set(Calendar.MINUTE, dateTime.getMinuteOfHour());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, getAM_PM(hourOfDay));
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), setUpAlarmType(PendingIntent.FLAG_ONE_SHOT));
        saveSelectedDateTime(calendar.getTime());
    }

    private void saveSelectedDateTime(Date time) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        String dateTime = new SimpleDateFormat(Constants.DATE_FORMAT_12_HOUR).format(time);
        preferenceHelper.setString(Constants.PREF_ALARM_DATE, dateTime);
    }

    private MutableDateTime getCalculatedDateAndTime(int hourofDay, int hourOfMinute) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        DateTime now = DateTime.now();
        String inputDateTime = now.getMonthOfYear() + "/" + now.getDayOfMonth() + "/" + now.getYear() + " " + hourofDay + ":" + hourOfMinute + ":00";
        MutableDateTime selected = new MutableDateTime(dtf.parseDateTime(inputDateTime));
        if (selected.isBefore(now)) {
            selected.addDays(1);
            return selected;
        }
        return selected;
    }


    private PendingIntent setUpAlarmType(int flag) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0,
                intent, flag);
    }

    public int getAM_PM(int hourOfDay) {
        return hourOfDay < 12 ? Calendar.AM : Calendar.PM;
    }


}
