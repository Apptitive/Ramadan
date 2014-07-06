package com.apptitive.ramadan.helper;

import android.content.Context;

import com.apptitive.ramadan.model.Region;
import com.apptitive.ramadan.model.TimeTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by rayhan on 5/27/2014.
 */
public class CSVToDbHelper {

    public static void readCSVAndInserIntoDb(Context ctx, int resourceId, DbTableName dbTableName) {

        InputStream inputStream = ctx.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String readLine;
        Region region;
        TimeTable timeTable;
        try {
            while ((readLine = br.readLine()) != null) {
                String[] result = readLine.split(",");
                if (dbTableName.equals(DbTableName.Region)) {
                    region = new Region(result[0], result[1], result[2], Boolean.parseBoolean(result[3]), Integer.parseInt(result[4]), Integer.parseInt(result[5]));
                    DbManager.getInstance().addRegion(region);
                } else if (dbTableName.equals(DbTableName.TimeTable)) {
                    timeTable = new TimeTable(result[0], result[1], result[2], result[3], result[4], result[5]);
                    DbManager.getInstance().addTimeTable(timeTable);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
