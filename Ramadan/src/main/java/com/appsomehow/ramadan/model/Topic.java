package com.appsomehow.ramadan.model;

/**
 * Created by Iftekhar on 6/4/2014.
 */
public class Topic {
    private String header;
    private String shortDescription;
    private boolean isFullText;

    public Topic(String header, String shortDescription, boolean isFullText) {
        this.header = header;
        this.shortDescription = shortDescription;
        this.isFullText = isFullText;
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
}
