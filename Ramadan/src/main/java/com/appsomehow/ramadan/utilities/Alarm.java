package com.appsomehow.ramadan.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.appsomehow.ramadan.receiver.AlarmReceiver;
import com.appsomehow.ramadan.receiver.NotificationCancelReceiver;

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
        alarmManager.set(AlarmManager.RTC_WAKEUP, MyCalender.getInstance().getTimeInMillies(
                hourOfDay, hourOfMinute), setUpAlarmType(PendingIntent.FLAG_ONE_SHOT));
    }

    public void setOneTimeAlarm(Class<NotificationCancelReceiver> receiverClass, int requestCode) {
        Intent intent = new Intent(context, receiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode,
                intent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
    }

    public void setRepeatingAlarm() {
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                (60 * 1000), setUpAlarmType(PendingIntent.FLAG_CANCEL_CURRENT));
    }

    private PendingIntent setUpAlarmType(int flag) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0,
                intent, flag);
    }


}
