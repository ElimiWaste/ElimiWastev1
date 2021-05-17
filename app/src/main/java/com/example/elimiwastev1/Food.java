package com.example.elimiwastev1;

import androidx.lifecycle.Lifecycle;

public class Food {

    public String name; //stores the food name of the Food object
    public String life; //stores the shelf life of the Food object

    /**
     * Constructor for the food class with two parameters: food name and shelf life
     * @param name is defined as the food name
     * @param life is defined as the shelf life of an item
     */
    public Food(String name, String life) {
        this.name = name;
        this.life = life;
    }

    /**
     * Default constructor for Food object
     * Sets both String name and String life to empty string
     */
    public Food(){
        name = "";
        life = "";
    }

    /**
     * Getter method for name of Food
     * @returns the food name for a Food object
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name of Food
     * @param name of the food
     * Sets name of the food to the Food object's name field
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for shelf life of a Food
     * @returns the food shelf life for a Food object
     */
    public String getLife() {
        return life;
    }

    /**
     * Setter method for shelf life of Food
     * @param life of the food (shelf life)
     * Sets shelf life of the food to the Food object's life field
     */
    public void setLife(String life) {
        this.life = life;
    }

    /**
     * Void method to print out Food object's shelf life
     */
    public void displayLife(){
        System.out.println(life);
    }

    /**
     * Void method to print out Food object's name
     */
    public void displayName(){
        System.out.println(name);
    }
}
