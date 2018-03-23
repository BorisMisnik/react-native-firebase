package io.invertase.firebase.messaging;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.google.firebase.messaging.RemoteMessage;

import javax.annotation.Nullable;

public class RNFirebaseBackgroundMessagingService extends HeadlessJsTaskService {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, "taimi_all")
                    .setContentTitle("Taimi")
                    .setContentText("New notification").build();

            startForeground(1337, notification);
            stopForeground(true);
        }
    }

    @Override
    protected @Nullable
    HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            RemoteMessage message = intent.getParcelableExtra("message");
            WritableMap messageMap = MessagingSerializer.parseRemoteMessage(message);
            return new HeadlessJsTaskConfig(
                    "RNFirebaseBackgroundMessage",
                    messageMap,
                    60000,
                    false
            );
        }
        return null;
    }
}
