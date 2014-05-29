package com.appsomehow.ramadan.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by rayhan on 5/27/2014.
 */
@DatabaseTable
public class TimeTable {
    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private Date date;

    @DatabaseField
    private String dateInBangla;

    @DatabaseField
    private String sehriTime;

    @DatabaseField
    private String ifterTime;

    @DatabaseField
    private String rojaCount;


    public TimeTable() {
    }

    public TimeTable(String id, Date date, String dateInBangla, String sehriTime, String ifterTime, String rojaCount) {
        this.id = id;
        this.date = date;
        this.dateInBangla = dateInBangla;
        this.sehriTime = sehriTime;
        this.ifterTime = ifterTime;
        this.rojaCount = rojaCount;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDateInBangla() {
        return dateInBangla;
    }

    public String getSehriTime() {
        return sehriTime;
    }

    public String getIfterTime() {
        return ifterTime;
    }

    public String getRojaCount() {
        return rojaCount;
    }
}
