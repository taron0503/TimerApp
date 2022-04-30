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
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

public class TimerService extends Service {

    private final String CHANNEL_ID = "1836";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private TimerEntity timerEntity;
    TimerRepository timerRepository;
    MutableLiveData<TimerEntity> timerEntityMutableLiveData;

    private Context context;

    public TimerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        timerRepository = TimerRepository.createInstance();
        timerEntityMutableLiveData = timerRepository.getMutableTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long timeInMills = intent.getLongExtra("timeInMills",0);
        timerEntity = new TimerEntity(timeInMills);
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        createNotificationChanel();
        startNotification();
        getCountdownTimer().start();
        return  START_STICKY;
    }

    private void createNotificationChanel(){
        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME4", NotificationManager.IMPORTANCE_HIGH);
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

        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_baseline_timer_24)
                .setContentTitle("Timer")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                ;

        notification = builder.build();
        startForeground(1,notification);
    }

    private CountDownTimer getCountdownTimer(){
        return new CountDownTimer(timerEntity.getTimeInMills(),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String leftTime = getLeftTimeInFormat();
                builder.setContentText(leftTime);
                startForeground(1,builder.build());
                timerEntity.setTimeInMills(timerEntity.getTimeInMills()-1000);
            }

            @Override
            public void onFinish() {
                Log.d("timer","timer finish");
            }
        };
    }

    private String getLeftTimeInFormat(){
        String hours = String.valueOf(timerEntity.getHours());
        String minutes = String.valueOf(timerEntity.getMinutes());
        String seconds = String.valueOf(timerEntity.getSeconds());
        String leftTime = hours + " : " + minutes + " : " + seconds;
        return leftTime;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}