package com.apptitive.ramadan.model;

/**
 * Created by Iftekhar on 6/5/2014.
 */
public class Detail {
    private String text;
    private int viewType;

    public Detail() {
    }

    public Detail(String text, int detailViewType) {
        this.text = text;
        viewType = detailViewType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
