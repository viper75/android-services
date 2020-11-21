package org.viper75.android_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.viper75.android_services.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.startStartedService.setOnClickListener(v -> {
            Intent intent = new Intent(this, StartedServiceExample.class);
            startService(intent);
        });

        Intent intent = new Intent(this, ForegroundServiceExample.class);
        mainBinding.startService.setOnClickListener(v -> {
            startService(intent);
        });

        mainBinding.stopService.setOnClickListener(v -> {
            stopService(intent);
        });
    }
}