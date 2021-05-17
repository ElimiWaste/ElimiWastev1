package com.example.elimiwastev1;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;


public class NotificationsLogic extends AppCompatActivity{
    public static long halfLifeNotif(long theLifeL, long dateEnteredMillis) {
        return dateEnteredMillis + theLifeL / 2;
    }
    public static long twoDayNotif(long theLifeL, long dateEnteredMillis) {
        return dateEnteredMillis + theLifeL - 2 * 86400000L ;
    }
}