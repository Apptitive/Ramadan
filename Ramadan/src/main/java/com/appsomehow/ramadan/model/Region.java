package com.appsomehow.ramadan.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rayhan on 5/27/2014.
 */

@DatabaseTable
public class Region {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String nameInBangla;

    @DatabaseField
    private boolean isPositive;

    @DatabaseField
    private int intervalIfter;

    @DatabaseField
    private int intervalSehri;

    public Region() {
    }

    public Region(String id, String name, String nameInBangla, boolean isPositive, int intervalIfter, int intervalSehri) {
        this.id = id;
        this.name = name;
        this.nameInBangla = nameInBangla;
        this.isPositive = isPositive;
        this.intervalIfter = intervalIfter;
        this.intervalSehri = intervalSehri;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameInBangla() {
        return nameInBangla;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public int getIntervalIfter() {
        return intervalIfter;
    }

    public int getIntervalSehri() {
        return intervalSehri;
    }
}
