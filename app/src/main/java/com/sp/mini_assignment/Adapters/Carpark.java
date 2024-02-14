package com.sp.mini_assignment.Adapters;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

// CLASS TO HOLD DATA FOR 1 CARPARK AND STORE IN A LIST
public class Carpark implements Parcelable {

    // Initialise Carpark Variables
    public String carparkImage;
    public String carparkName;
    public String carparkPrice;
    public String carparkDistance;

    public String carparkLevel;

    public String carparkDate;

    public String favStatus;

    public String carparkCapacity;

    public Double latitude, longitude;
    public String carparkTime;



    public  int key_id;

    // Constructor
    public Carpark(String carparkImage, String carparkName, String carparkDistance, String carparkSlot, String carparkDate, String carparkPrice, String favStatus, int key_id, String carparkCapacity, Double Latitude, Double Longitude) {
        this.carparkImage = carparkImage;
        this.carparkName = carparkName;
        this.carparkPrice = carparkPrice;
        this.carparkDistance = carparkDistance;
        this.carparkDate = carparkDate;
        this.carparkLevel = carparkSlot;
        this.favStatus = favStatus;
        this.key_id = key_id;
        this.carparkCapacity = carparkCapacity;
        this.latitude = Latitude;
        this.longitude = Longitude;
    }

    public Carpark() {
        // Default constructor required for calls to DataSnapshot.getValue(Carpark.class)
    }

    protected Carpark(Parcel in) {
        carparkImage = in.readString();
        carparkName = in.readString();
        carparkPrice = in.readString();
        carparkDistance = in.readString();
        carparkLevel = in.readString();
        carparkDate = in.readString();
    }

    public static final Creator<Carpark> CREATOR = new Creator<Carpark>() {
        @Override
        public Carpark createFromParcel(Parcel in) {
            return new Carpark(in);
        }

        @Override
        public Carpark[] newArray(int size) {
            return new Carpark[size];
        }
    };

    public String getCarparkImage() {
        return carparkImage;
    }

    public String getCarparkName() {
        return carparkName;
    }

    public String getCarparkPrice() {
        return carparkPrice;
    }

    public String getCarparkDistance() {
        return carparkDistance;
    }

    public String getCarparkLevel() { return carparkLevel;}

    public String getCarparkDate() { return carparkDate;}
    public void setCarparkImage(String carparkImage) {
        this.carparkImage = carparkImage;
    }

    public String getCarparkCapacity() { return carparkCapacity;}

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public String getCarparkTime() {
        return carparkTime;
    }
    public void setCarparkTime(String carparkTime) {
        this.carparkTime = carparkTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(carparkImage);
        dest.writeString(carparkName);
        dest.writeString(carparkPrice);
        dest.writeString(carparkDistance);
        dest.writeString(carparkLevel);
        dest.writeString(carparkDate);
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String status) {
        this.favStatus = status;

    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;

    }

    public int getKey_id() {
        return  key_id;
    }

    public void setCarparkName(String carparkName) {
        this.carparkName = carparkName;
    }
}
