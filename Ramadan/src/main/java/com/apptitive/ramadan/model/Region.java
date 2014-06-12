package com.apptitive.ramadan.model;

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
    private int intervalSehri;

    @DatabaseField
    private int intervalIfter;

    public Region() {
    }

    public Region(String id, String name, String nameInBangla, boolean isPositive, int intervalSehri, int intervalIfter) {
        this.id = id;
        this.name = name;
        this.nameInBangla = nameInBangla;
        this.isPositive = isPositive;
        this.intervalSehri = intervalSehri;
        this.intervalIfter = intervalIfter;
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
