package com.example.kursovayarabota;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Park {
    protected Park(Parcel in) {
        name = in.readString();
        address = in.readString();
        comment = in.readString();
    }
    public static final Parcelable.Creator<Park> CREATOR = new Parcelable.Creator<Park>() {
        @Override
        public Park createFromParcel(Parcel in) {
            return new Park(in);
        }

        @Override
        public Park[] newArray(int size) {
            return new Park[size];
        }
    };
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(comment);
    }

    public int describeContents() {
        return 0;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Park(String comment, String address, String name) {
        this.comment = comment;
        this.address = address;
        this.name = name;
    }

    private String name;
    private String address;
    private String comment;
}
