package com.apptitive.ramadan.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Iftekhar on 6/4/2014.
 */
public class Topic implements Parcelable {
    private String header;
    private String shortDescription;
    private boolean isFullText;
    private int detailId;

    private Topic(Parcel in) {
        header = in.readString();
        detailId = in.readInt();
    }

    public Topic() {
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(header);
        parcel.writeInt(detailId);
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel parcel) {
            return new Topic(parcel);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}
