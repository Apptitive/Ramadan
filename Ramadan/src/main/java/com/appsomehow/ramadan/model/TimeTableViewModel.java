package com.appsomehow.ramadan.model;

import android.text.SpannableString;

/**
 * Created by rayhan on 6/3/2014.
 */
public class TimeTableViewModel {
    private String id;
    private SpannableString date;
    private SpannableString sehriTime;
    private SpannableString ifterTime;
    private SpannableString rojaCount;

    public TimeTableViewModel(String id, SpannableString date, SpannableString sehriTime, SpannableString ifterTime, SpannableString rojaCount) {
        this.id = id;
        this.date = date;
        this.sehriTime = sehriTime;
        this.ifterTime = ifterTime;
        this.rojaCount = rojaCount;
    }

    public SpannableString getDate() {
        return date;
    }

    public void setDate(SpannableString date) {
        this.date = date;
    }

    public SpannableString getSehriTime() {
        return sehriTime;
    }

    public void setSehriTime(SpannableString sehriTime) {
        this.sehriTime = sehriTime;
    }

    public SpannableString getIfterTime() {
        return ifterTime;
    }

    public void setIfterTime(SpannableString ifterTime) {
        this.ifterTime = ifterTime;
    }

    public SpannableString getRojaCount() {
        return rojaCount;
    }

    public void setRojaCount(SpannableString rojaCount) {
        this.rojaCount = rojaCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
