package com.example.elimiwastev1;

import java.util.ArrayList;
import java.util.Date;

public class DateConvert {
    int theDay;
    int theMonth;
    int theYear;

    public DateConvert(int day, int month, int year) {
        theDay = day;
        theMonth = month;
        theYear = year;
    }

    public int monthConverter() {
        int monthDay = 1;
        int sumMonthDays = 0;
        for(int i = theMonth; i >= 1; i--) {
            switch (i) {
                case 2:
                    if (((theYear % 4 == 0) && (theYear % 100 != 0)) || (theYear % 400 == 0))
                        monthDay = 29;
                    else
                        monthDay = 28;
                    break;
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
            }
            sumMonthDays += monthDay;
        }
        return sumMonthDays + theDay;

    }
}
