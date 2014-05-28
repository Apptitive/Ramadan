package com.appsomehow.ramadan.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import com.appsomehow.ramadan.utilities.Constants;

import java.io.IOException;

public class RingtonService extends IntentService {
    private MediaPlayer mMediaPlayer;

    public RingtonService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String ringTonName = intent.getStringExtra(Constants.KEY_RINGTON_NAME);
        if (ringTonName != null) {
            playSound(ringTonName);
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
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }


}