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
    //Name of the food
    String name;
    //Date the food was entered
    String date;
    //SQL database helper
    DatabaseHelper database;
    //Cursor to get the Name
    Cursor cursorName;
    //Cursor to get the Date
    Cursor cursorDate;
    //How many rows there are in the database
    long rows;

    @Override
    //Sends out the notification for the half-life of the food
    public void onReceive(Context context, Intent intent) {
        //Notification manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        database = new DatabaseHelper(context);
        rows = database.getRows();
        //Gets the name of the item just entered from the SQL database
        cursorName = database.getItemName(String.valueOf(rows));
        if (cursorName.moveToFirst()){
            name = cursorName.getString(cursorName.getColumnIndex("NAME"));
        }
        Log.d("yah", String.valueOf(rows));
        //Gets the Date of the item just purchased from the SQL database
        cursorDate = database.getItemDate(String.valueOf(rows));
        if (cursorDate.moveToFirst()) {
            date = cursorDate.getString(cursorDate.getColumnIndex("DATE"));
        }
        //Builds the notification
        Notification notification = new NotificationCompat.Builder(context, Controller.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("Your food item \"" + name + "\" looks like it's more than halfway expired!")
                .setContentText("Check on \"" + name + "\" that was purchased on \"" + date + "\"")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        notificationManager.notify((int) database.getRows() + 1, notification);
    }
}
