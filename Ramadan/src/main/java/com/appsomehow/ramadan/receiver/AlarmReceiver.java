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

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {
    private MediaPlayer mMediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean isVibrate = sharedPref.getBoolean(context.getString(R.string.pref_key_alarm_vibrat), false);
        if (isVibrate) {
            setAlarmVibration(context);
        }

        String ringTonName = sharedPref.getString(context.getString(R.string.pref_key_alarm_rington), "default ringtone");

        Boolean isRington = sharedPref.getBoolean(context.getString(R.string.pref_key_alarm), false);
        if (isRington) {
            Intent ringTonIntent = new Intent(context, RingtonService.class);
            ringTonIntent.putExtra(Constants.KEY_RINGTON_NAME, ringTonName);
            Log.e("rington name",""+ringTonName);
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
