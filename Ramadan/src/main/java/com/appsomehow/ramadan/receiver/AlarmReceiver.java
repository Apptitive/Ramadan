package com.appsomehow.ramadan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.services.RingtonService;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.PreferenceHelper;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);



        boolean isAlarmSelected = preferenceHelper.getBoolean(context.getString(R.string.alarm_switch));
        if (!isAlarmSelected) {
            return;
        }




        boolean isVibrate = preferenceHelper.getBoolean(context.getString(R.string.pref_key_alarm_vibrat));
        if (isVibrate) {
            setAlarmVibration(context);
        }
        String ringTonName = preferenceHelper.getString(context.getString(R.string.pref_key_alarm_rington), "default ringtone");

        if (ringTonName!=null) {
            Intent ringTonIntent = new Intent(context, RingtonService.class);
            ringTonIntent.putExtra(Constants.KEY_RINGTON_NAME, ringTonName);
            Log.e("rington name", "" + ringTonName);
            context.startService(ringTonIntent);
        }

    }

    private void setAlarmVibration(Context context) {
        Vibrator v = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        // for 3 seconds
        long milliseconds = 3000;
        long pattern[] = {0, milliseconds, 200, 300, 500};
        v.vibrate(pattern, -1);
    }


}
