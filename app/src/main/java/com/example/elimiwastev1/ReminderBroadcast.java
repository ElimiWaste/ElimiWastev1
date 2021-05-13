package com.example.elimiwastev1;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;


public class ReminderBroadcast extends BroadcastReceiver{
    String name;
    String date;
    DatabaseHelper database;
    Cursor cursorName;
    Cursor cursorDate;
    int rows;
    //final Controller aController = (Controller) getApplicationContext();

    //private NotificationManagerCompat notificationManager;
    @Override
    //Sends out the notification
    public void onReceive(Context context, Intent intent) {
        //notificationManager = NotificationManagerCompat.from(this);
        database = new DatabaseHelper(context);
//        rows = database.getRows();
//        Log.d("12345", String.valueOf(rows));
//        Log.d("12345", "hi");


        cursorName = database.getItemName(String.valueOf(database.getRows()));
        if (cursorName.moveToFirst()){
            name = cursorName.getString(cursorName.getColumnIndex("NAME"));
        }

        cursorDate = database.getItemDate(String.valueOf(database.getRows()));
        if (cursorDate.moveToFirst()) {
            date = cursorDate.getString(cursorDate.getColumnIndex("DATE"));
        }
        Notification notification = new NotificationCompat.Builder(context, "notifications")
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("The food just entered was: " + name)
                .setContentText("It was entered in on: " + date)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, notification);
//
//        c = database.getItemName(1);
//        if (c.moveToFirst()){
//            name = c.getString(c.getColumnIndex("NAME"));
//        }
//        Notification notification2 = new NotificationCompat.Builder(context, Controller.CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_one)
//                .setContentTitle("The first food is " + name)
//                .setContentText("TBD" )
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .build();
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//        notificationManager.notify(2, notification2);


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
