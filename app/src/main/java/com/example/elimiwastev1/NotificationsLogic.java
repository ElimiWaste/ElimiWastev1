package com.example.elimiwastev1;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

/**
 * This class models the logic of the Notifications sent by ElimiWaste
 * the notifications are sent during a half life and two day basis
 */
public class NotificationsLogic extends AppCompatActivity{
    /**
     * Converts theLifeL and dateEnteredMillis into a date in millis
     * @param theLifeL the shelfLife of the food
     * @param dateEnteredMillis the enter date the user put in millis
     * @return the date in between the date of expiration in millis
     */
    public static long halfLifeNotif(long theLifeL, long dateEnteredMillis) {
        return dateEnteredMillis + theLifeL / 2;
    }

    /**
     * Converts theLifeL and dateEnteredMillis into a date in millis
     * @param theLifeL theLifeL the shelfLife of the food
     * @param dateEnteredMillis the enter date the user put in millis
     * @return the date two days before the item expires
     */
    public static long twoDayNotif(long theLifeL, long dateEnteredMillis) {
        return dateEnteredMillis + theLifeL - 2 * 86400000L ;
    }

}