package com.appsomehow.ramadan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;

import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.services.RingtonService;
import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.PreferenceHelper;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {
    private MediaPlayer mMediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);


        boolean isVibrate = preferenceHelper.getBoolean(context.getString(R.string.pref_key_alarm_vibrat));
        boolean isAlarmSelected = preferenceHelper.getBoolean(context.getString(R.string.pref_key_alarm));
        if (!isAlarmSelected) {
            return;
        }

        if (isVibrate) {
            setAlarmVibration(context);
        }
        String ringTonName = preferenceHelper.getString(context.getString(R.string.pref_key_alarm_rington), "default ringtone");
        boolean isRington = preferenceHelper.getBoolean(context.getString(R.string.pref_key_alarm));
        if (isRington) {
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
