package com.example.prc.alarm_manager;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prc.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v -> {
            startAlert();
        });

    }

    public void startAlert() {
        int second = Integer.parseInt(binding.et.getText().toString());

        ((AlarmManager) getSystemService(ALARM_SERVICE))
                .setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        second * 1000L,
                        PendingIntent.getBroadcast(
                                getApplicationContext(),
                                111,
                                new Intent(this, MyBroadcastReceiver.class),
                                FLAG_IMMUTABLE)
                );
        Toast.makeText(this, "Alarm set in " + second + " seconds", Toast.LENGTH_SHORT).show();
    }
}