package com.example.elimiwastev1;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class ReminderBroadcast extends BroadcastReceiver{
    //private NotificationManagerCompat notificationManager;
    @Override
    //Sends out the notification
    public void onReceive(Context context, Intent intent) {
        //notificationManager = NotificationManagerCompat.from(this);
        String string = new String();
        DatabaseHelper database = new DatabaseHelper(context);
        Notification notification = new NotificationCompat.Builder(context, NotifChannels.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("HI" + (database.getItemName("2")))
                .setContentText("HI" + Manual_Test.date.getText().toString())
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, notification);


//
//        Notification notification = new NotificationCompat.Builder(this, NotifChannels.CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_one)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//
//        notificationManager.notify(1, notification);
    }
}
