package com.appsomehow.ramadan.model;

import java.util.List;

/**
 * Created by Iftekhar on 6/4/2014.
 */
public class Topic {
    private String header;
    private String shortDescription;
    private boolean isFullText;
    private int detailId;

    public Topic() {}

    public Topic(String header, String shortDescription, boolean isFullText, int detailId) {
        this.header = header;
        this.shortDescription = shortDescription;
        this.isFullText = isFullText;
        this.detailId = detailId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean isFullText() {
        return isFullText;
    }

    public void setFullText(boolean isFullText) {
        this.isFullText = isFullText;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
}
