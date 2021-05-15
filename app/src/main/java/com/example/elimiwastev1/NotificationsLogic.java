package com.example.elimiwastev1;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;


public class NotificationsLogic extends AppCompatActivity{
    public static long halfLifeNotif(int foodLife, long dateEnteredMillis) {
        long theLifeL = 86400000L * foodLife;
        return dateEnteredMillis + theLifeL / 2;
    }
    public static long twoDayNotif(int foodLife, long dateEnteredMillis) {
        long theLifeL = 86400000L * (foodLife-2);
        return dateEnteredMillis + theLifeL;
    }
}