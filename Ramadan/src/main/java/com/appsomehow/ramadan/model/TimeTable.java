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
    public String id;

    @DatabaseField
    public Date date;

    @DatabaseField
    public String dateInBangla;

    @DatabaseField
    public String seheriTime;

    @DatabaseField
    public String ifterTime;

    @DatabaseField
    public String rojaCount;


    public TimeTable() {
    }

    public TimeTable(String id, Date date, String dateInBangla, String seheriTime, String ifterTime, String rojaCount) {
        this.id = id;
        this.date = date;
        this.dateInBangla = dateInBangla;
        this.seheriTime = seheriTime;
        this.ifterTime = ifterTime;
        this.rojaCount = rojaCount;
    }

    public Date getDate() {
        return date;
    }

    public String getDateInBangla() {
        return dateInBangla;
    }

    public String getSeheriTime() {
        return seheriTime;
    }

    public String getIfterTime() {
        return ifterTime;
    }

    public String getRojaCount() {
        return rojaCount;
    }

    public String getId() {
        return id;
    }
}
