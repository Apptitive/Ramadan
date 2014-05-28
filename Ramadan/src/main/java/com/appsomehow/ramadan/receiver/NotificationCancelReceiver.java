package com.appsomehow.ramadan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.appsomehow.ramadan.services.RingtonService;

public class NotificationCancelReceiver extends BroadcastReceiver {
    public NotificationCancelReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context, RingtonService.class));
    }
}
