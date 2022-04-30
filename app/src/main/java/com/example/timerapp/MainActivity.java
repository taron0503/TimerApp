package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import com.example.timerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Context context;
    ActivityMainBinding binding;
    private CountDownTimer countDownTimer;
    private MainActiityViewModel viewModel;
    private MutableLiveData<TimerEntity> timerEntityMutableLiveData;
    private TimerEntity timerEntity;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;
        //timerEntity = new TimerEntity(63000);

        configureTimer();

        viewModel = new ViewModelProvider(this).get(MainActiityViewModel.class);
        timerEntityMutableLiveData = viewModel.getMutableTimer();
        timerEntityMutableLiveData.observe(this, timerEntity -> {
            this.timerEntity = timerEntity;
            binding.setTimerEntity(timerEntity);
        });

        viewModel.setNewTimer(63000);

//        timerEntity = new TimerEntity(63000);
//        binding.setTimerEntity(timerEntity);


        binding.startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TimerService.class);
                long timeInMills = timerEntity.getTimeInMills();
                intent.putExtra("timeInMills",timeInMills);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                }else{
                    context.startService(intent);
                }

//                Toast.makeText(getBaseContext(), "hey", Toast.LENGTH_SHORT).show();
//                Log.d("heyy","heyy");
//
//                int hours = binding.hoursPicker.getValue();
//                int minutes = binding.minutesPicker.getValue();
//                int seconds = binding.secondsPicker.getValue();
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR) + hours);
//                calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + minutes);
//                calendar.set(Calendar.HOUR,calendar.get(Calendar.SECOND) + Calendar.SECOND,seconds);
//
//                Log.d("timeeh", String.valueOf(calendar.get(Calendar.HOUR)));
//                Log.d("timeem", String.valueOf(calendar.get(Calendar.MINUTE)));
//                Log.d("timees", String.valueOf(calendar.get(Calendar.SECOND)));

//                long startTime = hours*60*60*1000 + minutes*60*1000 + seconds *1000;
//                long startTime = timerEntity.getTimeInMills();
//                getCountdownTimer(startTime).start();


//                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                Intent intent = new Intent(context,TimerBroadcastReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,3,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
//                }

            }
        });
    }

    private void configureTimer() {
        configureHours();
        configureMinutes();
        configureSeconds();
    }

    private void configureHours(){
        binding.hoursPicker.setMaxValue(23);
        binding.hoursPicker.setMinValue(0);
        binding.hoursPicker.setFormatter(getNumberPickerFormatter());
    }

    private void configureMinutes(){
        binding.minutesPicker.setMaxValue(59);
        binding.minutesPicker.setMinValue(0);
        binding.minutesPicker.setFormatter(getNumberPickerFormatter());
    }

    private void configureSeconds(){
        binding.secondsPicker.setMaxValue(59);
        binding.secondsPicker.setMinValue(0);
        binding.secondsPicker.setFormatter(getNumberPickerFormatter());
    }

    private NumberPicker.Formatter getNumberPickerFormatter(){
        return new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value<10?"0"+value:""+value;
            }
        };
    }

    private CountDownTimer getCountdownTimer(long finishTime){
        return new CountDownTimer(finishTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerEntity.setTimeInMills(timerEntity.getTimeInMills()-1000);
                viewModel.updateTimer(timerEntity);
                //binding.setTimerEntity(timerEntity);
                Log.d("horurs", String.valueOf(timerEntity.getHours()) + ":" +
                        String.valueOf(timerEntity.getMinutes()) + ":" +
                        String.valueOf(timerEntity.getSeconds()));
//                timerEntity.setTimeInMills(timerEntity.getTimeInMills()-1000);
//                binding
            }

            @Override
            public void onFinish() {
                Log.d("timer","timer finish");
            }
        };
    }
}