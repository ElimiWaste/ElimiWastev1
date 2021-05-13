package com.example.elimiwastev1;

import android.util.Log;

import java.util.ArrayList;

public class NotifLogic {
    Controller control;
    //For each user entered food, there is the food name as well as the date entered
    //Check if the entered food exists in the Firebase database

    public void stuff() {
        control = new Controller();
        ArrayList<Food> firebaseFoods = control.getFood();
        Log.d("hi", "hi" + firebaseFoods.get(1).getName());
    }
}
