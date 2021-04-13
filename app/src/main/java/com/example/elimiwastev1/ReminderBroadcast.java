package com.example.elimiwastev1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class ReminderBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyMe");
        builder.setContentTitle("My notification");
        builder.setContentText("Milk expires soon");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
