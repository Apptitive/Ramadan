package com.apptitive.ramadan.utilities;

import android.content.Context;

import com.apptitive.ramadan.helper.DbManager;
import com.apptitive.ramadan.model.Region;
import com.apptitive.ramadan.model.TimeTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sharif on 5/28/2014.
 */
public class UIUtils {

    public static final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm");

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd/MM/yyyy");

    public static String getCurrentDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static TimeTable compareCurrentDate(List<TimeTable> timeTables,
                                               String currentDate) {
        for (TimeTable timeTable : timeTables) {
            if (timeTable.getDate().equals(currentDate)) {
                return timeTable;
            }
        }
        return null;
    }

    public static int getCurrentDateIndex() {
        List<TimeTable> timeTables = DbManager.getInstance().getAllTimeTables();
        int i = 0;
        for (TimeTable timeTable : timeTables) {
            if (timeTable.getDate().equals(getCurrentDate(Constants.DATE_FORMAT))) {
                return i;
            }
            i++;
        }
        return 100;
    }

    public static String dateToString(Date date) {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat(Constants.DATE_FORMAT);
            return dateformat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Region getSelectedLocation(List<Region> regions, String selectedLocation) {
        for (Region region : regions) {
            if (region.getName().equals(selectedLocation)) {
                return region;
            }
        }
        return DbManager.getInstance().getRegionWithName("Dhaka");
    }


    public static String getSehriIftarTime(int interval,TimeTable timeTable, Context context, boolean isSeheri) {
        if (timeTable==null){
            return "0:00";
        }
        try {
            Date sehriIftarTime = simpleDateTimeFormat.parse(getSehriIftarTime(isSeheri, timeTable));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, sehriIftarTime.getYear());
            calendar.set(Calendar.MONTH, sehriIftarTime.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, sehriIftarTime.getDay());
            calendar.set(Calendar.HOUR_OF_DAY, sehriIftarTime.getHours());
            calendar.set(Calendar.MINUTE, sehriIftarTime.getMinutes() + interval);
            return getReplacedString(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0:00";
    }


    public static String getSehriIftarTime(int interval,List<TimeTable>timeTables, Context context, boolean isSeheri) throws ParseException {

        TimeTable timeTable=  getCalculatedTimeTable(timeTables,isSeheri);
        if (timeTable==null){
            return "0:00";
        }

        try {
            Date sehriIftarTime = simpleDateTimeFormat.parse(getSehriIftarTime(isSeheri, timeTable));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, sehriIftarTime.getYear());
            calendar.set(Calendar.MONTH, sehriIftarTime.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, sehriIftarTime.getDay());
            calendar.set(Calendar.HOUR_OF_DAY, sehriIftarTime.getHours());
            calendar.set(Calendar.MINUTE, sehriIftarTime.getMinutes() + interval);
            return getReplacedString(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0:00";
    }

    public static String getReplacedString(Calendar calendar) {
        return Utilities.replaceBanglaCharacter("" + calendar.get(Calendar.HOUR)) + ":" + Utilities.replaceBanglaCharacter("" + String.format("%02d", calendar.get(Calendar.MINUTE)));
    }

    private static String getSehriIftarTime(boolean isSehri, TimeTable timeTable) {
        if (isSehri)
            return timeTable.getDate() + " " + timeTable.getSehriTime();
        String iftarTime= (Integer.parseInt(timeTable.getIfterTime().substring(0,2))-12)+ timeTable.getIfterTime().substring(2);
        return timeTable.getDate() + " " + iftarTime;
    }


    public static TimeTable getCalculatedTimeTable(List<TimeTable> timeTables,
                                                   boolean isSehri) throws ParseException {

        Calendar calendar = Calendar.getInstance();

        String currentDate = simpleDateFormat.format(calendar.getTime());

        Date currentDateTime = simpleDateTimeFormat.parse(simpleDateTimeFormat
                .format(calendar.getTime()));

        TimeTable timeTable = compareCurrentDate(timeTables, currentDate);
        if (timeTable != null) {
            Date mutableDateTime = getMutableDateTime(timeTable, isSehri);
            if (currentDateTime.before(mutableDateTime)) {
                return timeTable;
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                return compareCurrentDate(timeTables,
                        simpleDateFormat.format(calendar.getTime()));
            }
        }
        return null;
    }

    private static Date getMutableDateTime(TimeTable timeTable, boolean isSehri)
            throws ParseException {

        Calendar mutablCalendar = Calendar.getInstance();
        if (isSehri) {
            mutablCalendar.setTime(simpleDateTimeFormat.parse(timeTable
                    .getDate() + " " + timeTable.getSehriTime()));
        } else
            mutablCalendar.setTime(simpleDateTimeFormat.parse(timeTable
                    .getDate() + " " + timeTable.getIfterTime()));

        mutablCalendar.add(Calendar.MINUTE, 30);
        return mutablCalendar.getTime();
    }

}
