package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.timerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        configureTimer();
        binding.startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "hey", Toast.LENGTH_SHORT).show();
                Log.d("heyy","heyy");
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
}