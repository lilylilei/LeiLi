package com.leili.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by android on 6/30/18.
 */

public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String path = intent.getStringExtra("path");
        String action = intent.getAction();
        Log.d(TAG, "onStartCommand: lei331  path>>" + path + "  action>>" + action);
        if (mediaPlayer != null) {
            try {
                if (action.equals("play")) {
                    if(path != null && !mediaPlayer.isPlaying()){
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } else {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        } else {
                            mediaPlayer.start();
                        }
                    }
                } else if (action.equals("reset")) {
                        mediaPlayer.seekTo(0);
                        mediaPlayer.start();
                } else if (path != null && action.equals("next")) {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
