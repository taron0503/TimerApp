package com.example.timerapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.concurrent.TimeUnit;

public class TimerEntity extends BaseObservable {

    private long timeInMills;
//    private int hours;
//    private int minutes;
//    private int seconds;

    public TimerEntity(long timeInMills) {
        this.timeInMills = timeInMills;
    }

    @Bindable
    public long getTimeInMills() {
        return timeInMills;
    }

    public void setTimeInMills(long timeInMills) {
        this.timeInMills = timeInMills;
        notifyPropertyChanged(BR.timeInMills);
    }

    @Bindable
    public int getHours() {
        return (int) (timeInMills/(60*60*1000)%24);
    }

    public void setHours(int hours) {
        long newTime = timeInMills - (getHours()*360000) + hours*360000;;
        setTimeInMills(newTime);
    }

    @Bindable
    public int getMinutes() {
        return ((int) (timeInMills/(60*1000)) % 60);
    }

    public void setMinutes(int minutes) {
        long newTime = timeInMills - (getMinutes()*60000) + minutes*60000;
        setTimeInMills(newTime);
    }

    @Bindable
    public int getSeconds() {
        return (int) ((timeInMills/1000) % 60);
    }

    public void setSeconds(int seconds) {
        long newTime = timeInMills - (getSeconds()*1000) + seconds*1000;;
        setTimeInMills(newTime);
    }
}
