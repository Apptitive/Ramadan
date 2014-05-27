package com.appsomehow.ramadan.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rayhan on 5/27/2014.
 */

@DatabaseTable
public class Region {

    @DatabaseField(id = true)
    public String id;

    @DatabaseField
    public String name;

    @DatabaseField
    public boolean isPositive;

    @DatabaseField
    public int interval;

    public Region() {
    }

    public Region(String id, String name, boolean isPositive, int interval) {
        this.id = id;
        this.name = name;
        this.isPositive = isPositive;
        this.interval = interval;
    }
}
