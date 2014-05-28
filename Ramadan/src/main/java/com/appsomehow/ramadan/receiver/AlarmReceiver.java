package com.appsomehow.ramadan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;

import com.appsomehow.ramadan.R;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {
    private MediaPlayer mMediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean isVibrate = sharedPref.getBoolean(context.getString(R.string.pref_key_alarm_vibrat), false);
        if (isVibrate) {
            setAarmVibration(context);
        }

        String ringTonName = sharedPref.getString(context.getString(R.string.pref_key_alarm_rington), "default ringtone");

        Boolean isRington = sharedPref.getBoolean(context.getString(R.string.pref_key_alarm), false);
        if (isRington)
            playSound(context, ringTonName);
    }

    private void setAarmVibration(Context context) {
        Vibrator v = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        // for 3 seconds
        long milliseconds = 3000;
        long pattern[] = {0, milliseconds, 200, 300, 500};
        v.vibrate(pattern, -1);
    }


    private void playSound(Context context, String ringTon) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, Uri.parse(ringTon));
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
        }
    }

}
