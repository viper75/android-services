package org.viper75.android_services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {

    static final int MSG_SAY_HELLO = 1;

    static class MessageHandler extends Handler {
        private final Context mContext;

        public MessageHandler(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_SAY_HELLO) {
                Toast.makeText(mContext, "Hello from Messenger Service", Toast.LENGTH_SHORT).show();
            } else {
                super.handleMessage(msg);
            }
        }
    }

    private Messenger messenger;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Binding...", Toast.LENGTH_SHORT).show();
        messenger = new Messenger(new MessageHandler(this));
        return messenger.getBinder();
    }
}
