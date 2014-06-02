package com.appsomehow.ramadan.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.appsomehow.ramadan.utilities.Constants;
import com.appsomehow.ramadan.utilities.Utilities;

import java.io.IOException;

public class RingtonService extends Service {

    private MediaPlayer mMediaPlayer;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        String ringTonName = intent.getStringExtra(Constants.KEY_RINGTON_NAME);
        if (ringTonName != null) {
            playSound(ringTonName);
            Log.e("service rington name", "" + ringTonName);
            Log.e("start music ", "music started");
            Utilities.customNotification(getBaseContext());
        }
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
        Log.e("service destroyed", "oh!");
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }


}
