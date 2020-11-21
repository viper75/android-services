package org.viper75.android_services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

public class BoundServiceExampleOne extends Service {

    private final IBinder binder = new LocalServiceBinder();
    private final Random generator = new Random();

    public class LocalServiceBinder extends Binder {
        BoundServiceExampleOne getService() {
            return BoundServiceExampleOne.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public int getRandomNumber() {
        return generator.nextInt(1000);
    }
}
