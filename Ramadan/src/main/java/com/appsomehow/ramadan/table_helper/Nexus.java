package com.appsomehow.ramadan.table_helper;

/**
 * Created by rayhan on 6/1/2014.
 */
public class Nexus {
    public final String[] data;

    public Nexus(String dateInBangali, String sehriTime, String ifterTime, String rojaCount) {
        data = new String[]{
                dateInBangali,
                sehriTime,
                ifterTime,
                rojaCount,
        };
    }
}
