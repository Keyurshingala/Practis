package com.example.prc.unstopeble_timer;

import static com.example.prc.ExtKt.log;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prc.databinding.ActivityFinishableTimerBinding;

public class FinishableTimerActivity extends AppCompatActivity {

    ActivityFinishableTimerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinishableTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!isMyServiceRunning(TimerService.class))
            binding.btn.setVisibility(View.VISIBLE);

        binding.btn.setOnClickListener(v -> startTime());

        TimerService.setOnTik(new TimerService.OnCountDown() {
            @Override
            public void onTik(long millisUntilFinished) {
                log("rcving");

                if (millisUntilFinished / 1000 == 0) {
                    binding.btn.setVisibility(View.VISIBLE);

                    binding.tv.setVisibility(View.GONE);
                } else {
                    binding.tv.setVisibility(View.VISIBLE);
                    binding.tv.setText("resend otp in: " + millisUntilFinished / 1000);
                    binding.btn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void startTime() {
        if (!isMyServiceRunning(TimerService.class))
            startService(new Intent(this, TimerService.class));
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        for (ActivityManager.RunningServiceInfo service : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE))
            if (serviceClass.getName().equals(service.service.getClassName())) return true;
        return false;
    }
}