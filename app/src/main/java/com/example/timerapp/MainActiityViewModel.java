package com.example.timerapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainActiityViewModel extends AndroidViewModel {

    private TimerEntity timerEntity;
    private TimerRepository timerRepository;
    private MutableLiveData<TimerEntity> mutableTimer;

    public MainActiityViewModel(@NonNull Application application) {
        super(application);
       // setNewTimer(0);
        timerRepository = TimerRepository.createInstance();
        mutableTimer = timerRepository.getMutableTimer();
    }

    public void setNewTimer(long timeInMills){
        timerEntity = new TimerEntity(timeInMills);
        timerRepository.setNewTimer(timerEntity);
//        mutableTimer.setValue(timerEntity);
    }

    public void updateTimer(TimerEntity timerEntity){
        timerRepository.updateTimer(timerEntity);
//        mutableTimer.setValue(timerEntity);
    }

    public MutableLiveData<TimerEntity> getMutableTimer(){
        return mutableTimer;
    }

}
