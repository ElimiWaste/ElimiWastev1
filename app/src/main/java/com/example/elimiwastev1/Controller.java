package com.example.elimiwastev1;

import android.app.Application;

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

}
