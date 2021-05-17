package com.example.elimiwastev1;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class converts the date from day month year format to milliseconds, accepts any date after 1970
 */
public class DateConvert {
    //A day value from 1-31
    int theDay;
    //The month from 1-12
    int theMonth;
    //The year
    int theYear;

    /**
     * Paramter constructor, takes input variables day, month, and year as int and stores
     * @param day is user-defined day stored as int
     * @param month is user-defined month stored as int
     * @param year is user-defined year stored as int
     */
    public DateConvert(int day, int month, int year) {
        theDay = day;
        theMonth = month;
        theYear = year;
    }

    /**
     * converts the month and day into days passed since the beginning of the current year
     * @return the months and day in terms of days
     */
    public int monthAndDayConverter() {
        int monthDay = 1;
        int sumMonthDays = 0;
        for (int i = theMonth-1; i >= 1; i--) {
            switch (i) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    monthDay = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    monthDay = 30;
                    break;
                case 2:
                    if (((theYear % 4 == 0) && (theYear % 100 != 0)) || (theYear % 400 == 0))
                        monthDay = 29;
                    else
                        monthDay = 28;
                    break;
            }
            sumMonthDays += monthDay;
        }
        return sumMonthDays + theDay - 1;
    }

    /**
     * Converts the number of years since 1970 to the number of days since 1970 on the level of years
     * @return int years that have passed since 1970 in days
     */
    public int yearConverter() {
        int yearDay = 0;
        int sumYearDays = 0;
        for (int i = theYear; i > 1970; i--) {
            if (((i % 4 == 1) && (i % 100 != 1)) || (i % 400 == 1)) {
                yearDay = 366;
                sumYearDays += yearDay;

            } else {
                yearDay = 365;
                sumYearDays += yearDay;
            }
        }

        return sumYearDays;
    }

    /**
     * Takes the raw data from firebase with the name and the shelflife together and cleans the data such that only the shelflife of the food is returned.
     * @param shelfLife the firebase data with the name and shelf life of the food together
     * @return the shelf life of the food converted into how ever many days it is
     */
    public int convertInputDate(String shelfLife){
        shelfLife = shelfLife.toUpperCase();
        Log.d("albatross", shelfLife);
        int convertedLife;
        if(shelfLife.contains("YEAR")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross1", cleanShelfLife);
            convertedLife = 365 * Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("MONTH")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross2", cleanShelfLife);
            convertedLife = 30 * Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("WEEK")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross3", cleanShelfLife);
            convertedLife = 7 * Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("DAY")){
            String cleanShelfLife = shelfLife.replaceAll("\\D+",""); //remove non-digits
            Log.d("albatross4", cleanShelfLife);
            convertedLife = Integer.parseInt(cleanShelfLife);
        }
        else if(shelfLife.contains("INDEFINITELY")){
            convertedLife = 9999999;
        }
        else{
            convertedLife = 0;
        }
        return convertedLife;
    }
}
