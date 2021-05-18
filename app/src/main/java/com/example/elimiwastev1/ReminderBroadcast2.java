package com.example.elimiwastev1;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ReminderBroadcast2 extends BroadcastReceiver{
    String name;
    String date;
    DatabaseHelper database;
    Cursor cursorName;
    Cursor cursorDate;
    long rows;

    @Override
    //Sends out the notification
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        database = new DatabaseHelper(context);
        rows = database.getRows();

        Log.d("123456", "#rows is: " + String.valueOf(rows));

        cursorName = database.getItemName(String.valueOf(database.getRows()));
        if (cursorName.moveToFirst()){
            name = cursorName.getString(cursorName.getColumnIndex("NAME"));
        }
        Log.d("123456", "the food name is " + name);

//String.valueOf(database.getRows())
        cursorDate = database.getItemDate(String.valueOf(database.getRows()));
        if (cursorDate.moveToFirst()) {
            date = cursorDate.getString(cursorDate.getColumnIndex("DATE"));
        }
        Log.d("123456", "the date is "+ date);

        Notification notification = new NotificationCompat.Builder(context, Controller.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("Your \"" + name + "\" expires in less than two days!!!")
                .setContentText("Check up on \""  + name +  "\"that was purchased on \"" + date + "\"" )
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(2147483647 - (int) database.getRows(), notification);
    }
}
