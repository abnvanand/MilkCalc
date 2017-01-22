package com.example.abhinav.milkcalc.pojo;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.abhinav.milkcalc.BR;

import java.io.Serializable;

public class Log extends BaseObservable implements Serializable {
    // Contains firebase push Id
    public String serverID;

    public String date;
    public String tankerNumber;
    public String fromDairy;
    public String toDairy;
    public String distance;
    public String milkQty;

    @Bindable
    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Bindable
    public String getTankerNumber() {
        return tankerNumber;
    }

    public void setTankerNumber(String tankerNumber) {
        this.tankerNumber = tankerNumber;
        notifyPropertyChanged(BR.tankerNumber);
    }

    @Bindable
    public String getFromDairy() {
        return fromDairy;
    }

    public void setFromDairy(String fromDairy) {
        this.fromDairy = fromDairy;
        notifyPropertyChanged(BR.fromDairy);
    }

    @Bindable
    public String getToDairy() {
        return toDairy;
    }

    public void setToDairy(String toDairy) {
        this.toDairy = toDairy;
        notifyPropertyChanged(BR.toDairy);
    }

    @Bindable
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
        notifyPropertyChanged(BR.distance);
    }

    @Bindable
    public String getMilkQty() {
        return milkQty;
    }

    public void setMilkQty(String milkQty) {
        this.milkQty = milkQty;
        notifyPropertyChanged(BR.milkQty);
    }
}
