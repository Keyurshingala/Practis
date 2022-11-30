package com.example.prc.service;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.prc.databinding.ActivityLocationBinding;

public class LocationActivity extends AppCompatActivity implements ClipboardManager.OnPrimaryClipChangedListener {

    ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v -> startTraking());
    }

    private void startTraking() {
        if (!isMyServiceRunning(EndlessService.class))
            ContextCompat.startForegroundService(this, new Intent(LocationActivity.this, EndlessService.class));
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPrimaryClipChanged() {
        ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        CharSequence pasteData = "";
        ClipData.Item item = clipBoard.getPrimaryClip().getItemAt(0);
        pasteData = item.getText();
        Toast.makeText(getApplicationContext(), "copied val=" + pasteData, Toast.LENGTH_SHORT).show();
    }
}