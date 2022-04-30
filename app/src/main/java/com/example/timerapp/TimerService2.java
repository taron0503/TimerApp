package com.example.timerapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

public class TimerService2 extends Service {

    private final String CHANNEL_ID = "11";
    private  NotificationManager mNotificationManager;
    TimerRepository timerRepository;
    MutableLiveData<TimerEntity> timerEntityMutableLiveData;

    private Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        timerRepository = TimerRepository.createInstance();
        timerEntityMutableLiveData = timerRepository.getMutableTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("awa","awa");

        int timeInMills = intent.getIntExtra("timeInMills",0);
//        timerEntityMutableLiveData.observe(context, timerEntity -> {
//
//        });
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChanel(){
        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setLightColor(Color.GRAY);
            mChannel.enableLights(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            mChannel.setSound(null, audioAttributes);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }
    }

    private void startNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_baseline_timer_24)
                .setContentTitle("imer")
                .setContentText("Timer")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setVibrate(new long[]{0, 500, 1000})
                .setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        startForeground(1,notification);
    }
}
