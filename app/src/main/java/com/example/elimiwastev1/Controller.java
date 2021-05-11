package com.example.elimiwastev1;

import android.app.Application;

import java.util.ArrayList;
import java.util.Random;

public class Controller extends Application {

    ArrayList<Food> foods = new ArrayList<Food>();

    public ArrayList<Food> getFood() {
        return foods;
    }


}
