package org.viper75.android_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import org.viper75.android_services.databinding.ActivityMainBinding;
import org.viper75.android_services.BoundServiceExampleOne.LocalServiceBinder;

public class MainActivity extends AppCompatActivity {

    private BoundServiceExampleOne mService;
    private boolean mBound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalServiceBinder binder = (LocalServiceBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

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

        mainBinding.getRandomNumber.setOnClickListener(v -> {
            if (mBound) {
                int num = mService.getRandomNumber();
                Toast.makeText(this, "Generated number: " + num, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BoundServiceExampleOne.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }
}