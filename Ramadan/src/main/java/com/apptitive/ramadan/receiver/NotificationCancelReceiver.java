package com.apptitive.ramadan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apptitive.ramadan.services.RingtoneService;

public class NotificationCancelReceiver extends BroadcastReceiver {
    public NotificationCancelReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context, RingtoneService.class));
    }
}
