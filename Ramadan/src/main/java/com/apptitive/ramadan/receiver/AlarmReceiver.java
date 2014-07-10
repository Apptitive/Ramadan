package com.apptitive.ramadan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.apptitive.ramadan.R;
import com.apptitive.ramadan.services.RingtoneService;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.PreferenceHelper;

public class AlarmReceiver extends BroadcastReceiver {
    private PreferenceHelper preferenceHelper;
    private int requestCode;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferenceHelper = new PreferenceHelper(context);
        preferenceHelper.getBoolean(Constants.PREF_SWITCH_IFTAR);
        preferenceHelper.getBoolean(Constants.PREF_SWITCH_SEHRI);

        Bundle extras = intent.getExtras();
        if (extras != null) {
            requestCode = extras.getInt(Constants.REQUEST_CODE);
        }

        if (requestCode == Constants.IFTAR_REQUEST_CODE & preferenceHelper.getBoolean(Constants.PREF_SWITCH_IFTAR)) {
            setUpVibrationAndRingTon(context);
        }

        if (requestCode == Constants.SEHRI_REQUEST_CODE & preferenceHelper.getBoolean(Constants.PREF_SWITCH_SEHRI)) {
            setUpVibrationAndRingTon(context);
        }


    }

    private void setUpVibrationAndRingTon(Context context) {
        boolean isVibrate = preferenceHelper.getBoolean(context.getString(R.string.pref_key_alarm_vibrate));
        if (isVibrate) {
            setAlarmVibration(context);
        }
        String ringTonName = preferenceHelper.getString(context.getString(R.string.pref_key_alarm_ringtone), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString());
        if (ringTonName != null) {
            Intent ringTonIntent = new Intent(context, RingtoneService.class);
            ringTonIntent.putExtra(Constants.KEY_RINGTONE_NAME, ringTonName);
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
