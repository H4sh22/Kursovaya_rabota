package com.example.kursovayarabota;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Plant implements Parcelable {
    public void setSpecies(String species) {
        this.species = species;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    private String name;
    private String description;
    private String species;
    public Plant(String name,String species,String description) {
        this.name = name;
        this.species = species;
        this.description = description;
    }

    protected Plant(Parcel in) {
        name = in.readString();
        species = in.readString();
        description = in.readString();
    }

    public static final Creator<Plant> CREATOR = new Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(species);
        dest.writeString(description);
    }
}
