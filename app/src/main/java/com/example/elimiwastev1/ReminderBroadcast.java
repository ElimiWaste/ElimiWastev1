package com.example.elimiwastev1;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class ReminderBroadcast extends BroadcastReceiver{
    String name;
    DatabaseHelper database;
    Cursor c;
    //private NotificationManagerCompat notificationManager;
    @Override
    //Sends out the notification
    public void onReceive(Context context, Intent intent) {
        //notificationManager = NotificationManagerCompat.from(this);
        database = new DatabaseHelper(context);
        c = database.getItemName(1);
        if (c.moveToFirst()){
            name = c.getString(c.getColumnIndex("NAME"));
        }
        Notification notification = new NotificationCompat.Builder(context, NotifChannels.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("The first food is " + name)
                .setContentText("TBD" )
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, notification);


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
