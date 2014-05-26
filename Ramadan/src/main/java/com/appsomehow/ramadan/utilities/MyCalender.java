package com.appsomehow.ramadan.utilities;

import java.util.Calendar;

/**
 * Created by Sharif on 5/26/2014.
 */
public class MyCalender {
    private static MyCalender ourInstance = new MyCalender();

    public static MyCalender getInstance() {
        return ourInstance;
    }


    private Calendar calendar;
    private int AM_PM;

    private MyCalender() {
        calendar = Calendar.getInstance();
    }

    public long getTimeInMillies(int hourOfDay, int hourOfMinute) {
        return setCurrentTime(hourOfDay, hourOfMinute).getTimeInMillis();
    }

    private Calendar setCurrentTime(int hourOfDay, int hourOfMinute) {
        calendar.set(Calendar.YEAR, Calendar.YEAR);
        calendar.set(Calendar.MONTH, Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, hourOfMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, getAM_PM(hourOfDay));
        return calendar;
    }

    public int getAM_PM(int hourOfDay) {
        if (hourOfDay < 12) {
            AM_PM = Calendar.AM;
        } else {
            AM_PM = Calendar.PM;
        }
        return AM_PM;
    }
}
