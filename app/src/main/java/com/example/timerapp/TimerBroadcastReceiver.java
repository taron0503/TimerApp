package com.example.timerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "asdf", Toast.LENGTH_SHORT).show();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");
        String datetime = sdf.format(Calendar.getInstance().getTime());
        Log.d("timerBr","receved " + datetime);
    }
}
