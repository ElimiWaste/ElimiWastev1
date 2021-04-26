package com.example.elimiwastev1;

public class User {
    private String name;
    private String date;


    public User(String initName, String initDate) {
        name = initName;
        date = initDate;
    }

    public String getItemName() {
        return name;
    }

    public void setItemName(String initName){
        name = initName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String initDate){
        date = initDate;
    }

}

