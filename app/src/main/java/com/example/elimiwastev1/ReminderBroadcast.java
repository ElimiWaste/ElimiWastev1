package com.example.elimiwastev1;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
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
    long rows;

    @Override
    //Sends out the notification
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        database = new DatabaseHelper(context);
        rows = database.getRows();
        Log.d("12345", String.valueOf(rows));
        Log.d("12345", "hi");

        cursorName = database.getItemName(String.valueOf(database.getRows()));
        if (cursorName.moveToFirst()){
            name = cursorName.getString(cursorName.getColumnIndex("NAME"));
        }
        Log.d("12345", "hi" + name);

        cursorDate = database.getItemDate(String.valueOf(database.getRows()));
        if (cursorDate.moveToFirst()) {
            date = cursorDate.getString(cursorDate.getColumnIndex("DATE"));
        }
        Log.d("12345", "hi"+ date);

        Notification notification = new NotificationCompat.Builder(context, Controller.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("Your food item \"" + name + "\" is halfway expired!")
                .setContentText("Check on \"" + name + "\" that was purchased on \"" + date + "\"")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        notificationManager.notify((int) database.getRows() + 1, notification);
    }
}
