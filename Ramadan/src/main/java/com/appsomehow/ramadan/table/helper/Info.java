package com.appsomehow.ramadan.table.helper;

/**
 * Created by rayhan on 6/1/2014.
 */
public class Info {
    public final String[] data;

    public Info(String dateInBangali, String sehriTime, String ifterTime, String rojaCount) {
        data = new String[]{
                dateInBangali,
                sehriTime,
                ifterTime,
                rojaCount,
        };
    }
}
