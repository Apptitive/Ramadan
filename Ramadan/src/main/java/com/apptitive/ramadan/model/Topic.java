package com.apptitive.ramadan.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Iftekhar on 6/4/2014.
 */
public class Topic implements Parcelable {
    private String header;
    private String shortDescription;
    private boolean hasFullText;
    private int detailId;
    private Uri detailUri;

    private Topic(Parcel in) {
        header = in.readString();
        detailId = in.readInt();
        detailUri = Uri.parse(in.readString());
    }

    public Topic() {
    }

    public Topic(String header, String shortDescription, boolean hasFullText, int detailId, Uri detailUri) {
        this.header = header;
        this.shortDescription = shortDescription;
        this.hasFullText = hasFullText;
        this.detailId = detailId;
        this.detailUri = detailUri;
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

    public boolean hasFullText() {
        return hasFullText;
    }

    public void setHasFullText(boolean isFullText) {
        this.hasFullText = isFullText;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public Uri getDetailUri() {
        return detailUri;
    }

    public void setDetailUri(Uri detailUri) {
        this.detailUri = detailUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(header);
        parcel.writeInt(detailId);
        parcel.writeString(detailUri == null ? "" : detailUri.toString());
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
