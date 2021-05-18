package com.example.elimiwastev1;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//See HalfLifeNotif for comments
public class TwoDayNotif extends BroadcastReceiver{
    String name;
    String date;
    DatabaseHelper database;
    Cursor cursorName;
    Cursor cursorDate;
    long rows;
    //See HalfLifeNotif for comments

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        database = new DatabaseHelper(context);
        rows = database.getRows();

        Log.d("123456", "#rows is: " + String.valueOf(rows));
        //See HalfLifeNotif for comments
        cursorName = database.getItemName(String.valueOf(database.getRows()));
        if (cursorName.moveToFirst()){
            name = cursorName.getString(cursorName.getColumnIndex("NAME"));
        }
        Log.d("123456", "the food name is " + name);
        //See HalfLifeNotif for comments
        cursorDate = database.getItemDate(String.valueOf(database.getRows()));
        if (cursorDate.moveToFirst()) {
            date = cursorDate.getString(cursorDate.getColumnIndex("DATE"));
        }
        Log.d("123456", "the date is "+ date);
        //See HalfLifeNotif for comments

        Notification notification = new NotificationCompat.Builder(context, Controller.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("Your \"" + name + "\" expires in less than two days!!!")
                .setContentText("Check up on \""  + name +  "\"that was purchased on \"" + date + "\"" )
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        //Not sure if giving each notif a unique ID is needed, as the cancel works by canceling the pending intent, not the notif.
        notificationManager.notify(2147483647 - (int) database.getRows(), notification);
    }
}
