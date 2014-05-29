package com.appsomehow.ramadan.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.receiver.NotificationCancelReceiver;

/**
 * Created by Sharif on 5/27/2014.
 */
public class Utilities {

    public static Typeface getFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/" + context.getString(R.string.font_solaimanlipi));
    }


    public static void CustomNotification(Context context) {


        Intent notificationIntent = new Intent(context, NotificationCancelReceiver.class);
        notificationIntent.setAction("notification_cancelled");
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Alarm alarm = new Alarm(context);
        // alarm.setOneTimeAlarm(NotificationCancelReceiver.class, 10);

        int mNotificationId = 001;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_tarabih)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!").setDeleteIntent(deletePendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mBuilder.setAutoCancel(true);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }


}
