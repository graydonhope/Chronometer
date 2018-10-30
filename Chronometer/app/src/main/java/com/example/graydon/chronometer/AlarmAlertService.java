package com.example.graydon.chronometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmAlertService extends Service {
    private static MediaPlayer ringtonePlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SGAGB074", "Inside onStart of alarm");
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(5000);
        Uri ringtoneURI = Settings.System.DEFAULT_ALARM_ALERT_URI;
        if(ringtonePlayer != null &&  ringtonePlayer.isPlaying())
            ringtonePlayer.stop();
        ringtonePlayer = MediaPlayer.create(getBaseContext(), ringtoneURI);
        ringtonePlayer.setVolume(100,100);
        ringtonePlayer.start();
        Timer stopAudioTimer = new Timer();
        stopAudioTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(ringtonePlayer != null)
                    ringtonePlayer.stop();

            }
        },5000);
        ringtonePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ringtonePlayer.release();
            }
        });

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ringtonePlayer != null &&  ringtonePlayer.isPlaying())
            ringtonePlayer.stop();

    }
}
