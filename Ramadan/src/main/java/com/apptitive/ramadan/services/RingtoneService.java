package com.apptitive.ramadan.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import com.apptitive.ramadan.R;
import com.apptitive.ramadan.utilities.Constants;
import com.apptitive.ramadan.utilities.PreferenceHelper;
import com.apptitive.ramadan.utilities.Utilities;

import java.io.IOException;

public class RingtoneService extends Service {

    private MediaPlayer mMediaPlayer;
private PreferenceHelper preferenceHelper;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        preferenceHelper= new PreferenceHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String ringTonName="default ringtone";
        if (intent==null){
            ringTonName=preferenceHelper.getString("RINGTON_NAME", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString());
        }else {
            ringTonName  = intent.getStringExtra(Constants.KEY_RINGTONE_NAME);
        }
        playSound(ringTonName);
        Utilities.customNotification(getBaseContext());
        preferenceHelper.setString("RINGTON_NAME",ringTonName);

        return START_STICKY;
    }

    private void playSound(String ringTon) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(getBaseContext(), Uri.parse(ringTon));
            final AudioManager audioManager = (AudioManager) getBaseContext()
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }


}
