package com.example.timerapp;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

public class TimerRepository {
    private static TimerRepository timerRepository;
    private MutableLiveData<TimerEntity> mutableTimer = new MutableLiveData<>();

    public static TimerRepository createInstance(){
        if(timerRepository == null){
            timerRepository = new TimerRepository();
        }
        return timerRepository;
    }

    public void setNewTimer(TimerEntity timerEntity){
//        TimerEntity timerEntity = new TimerEntity(timeInMills);
        mutableTimer.setValue(timerEntity);
    }

    public void updateTimer(TimerEntity timerEntity){
        mutableTimer.setValue(timerEntity);
    }

    public MutableLiveData<TimerEntity> getMutableTimer(){return mutableTimer;}
}
