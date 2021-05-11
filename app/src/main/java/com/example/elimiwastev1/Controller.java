package com.example.elimiwastev1;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.Random;

public class Controller extends Application {

    ArrayList<Food> foodList = new ArrayList<Food>();

    public ArrayList<Food> getFood() {
        return foodList;
    }

    public void addFood(Food yummy) {
        foodList.add(yummy);
    }

    public static final String CHANNEL_1_ID = "notifications";
    public static final String CHANNEL_2_ID = "notifications2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationsChannels();
    }

    private void createNotificationsChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "ReminderChannel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is the first channel");
            //channel. (add default settings to channel)
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "ReminderChannel2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is the second channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }

}
