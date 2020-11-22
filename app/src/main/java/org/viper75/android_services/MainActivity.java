package org.viper75.android_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import org.viper75.android_services.databinding.ActivityMainBinding;
import org.viper75.android_services.BinderService.LocalServiceBinder;

public class MainActivity extends AppCompatActivity {

    private BinderService mBinderService;
    private Messenger mMessengerService;
    private boolean mBinderServiceBound = false;
    private boolean mMessengerServiceBound = false;

    private ServiceConnection binderConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalServiceBinder binder = (LocalServiceBinder) service;
            mBinderService = binder.getService();
            mBinderServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinderServiceBound = false;
        }
    };

    private ServiceConnection messengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessengerService = new Messenger(service);
            mMessengerServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessengerService = null;
            mMessengerServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.startStartedService.setOnClickListener(v -> {
            Intent intent = new Intent(this, StartedService.class);
            startService(intent);
        });

        Intent intent = new Intent(this, ForegroundService.class);
        mainBinding.startService.setOnClickListener(v -> {
            startService(intent);
        });

        mainBinding.stopService.setOnClickListener(v -> {
            stopService(intent);
        });

        mainBinding.getRandomNumber.setOnClickListener(v -> {
            if (mBinderServiceBound) {
                int num = mBinderService.getRandomNumber();
                Toast.makeText(this, "Generated number: " + num, Toast.LENGTH_SHORT).show();
            }
        });

        mainBinding.sayHello.setOnClickListener(v -> {
            if (!mMessengerServiceBound) return;

            Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
            try {
                mMessengerService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, messengerConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMessengerServiceBound) {
            unbindService(messengerConnection);
            mMessengerServiceBound = false;
        }
    }
}