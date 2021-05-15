package com.example.elimiwastev1;

import androidx.lifecycle.Lifecycle;

public class Food {

    public String name;
    public String life;

    /**
     * Constructor for the food class with two parameters: food name and shelf life
     * @param name is defined as the food name
     * @param life is defined as the shelf life of an item
     */
    public Food(String name, String life) {
        this.name = name;
        this.life = life;
    }

    public Food(){
        name = "";
        life = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public void displayLife(){
        System.out.println(life);
    }

    public void displayName(){
        System.out.println(name);
    }
}
