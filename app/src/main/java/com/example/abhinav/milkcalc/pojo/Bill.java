package com.example.abhinav.milkcalc.pojo;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.example.abhinav.milkcalc.BR;

import java.io.Serializable;

public class Bill extends BaseObservable implements Serializable {
    public String serverId;
    public String date;
    public String tankerNumber;
    public String fromDairy;
    public String toDairy;
    public String distance;

    public String dieselPP1;
    public String dieselPP2;
    public String dieselPP3;
    public String dieselTotal;

    public String average;
    public String balanceHSD;
    public String cash;
    public String tollTax;
    public String balanceCash;

    @Bindable
    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
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
        this.setAverage();
    }

    @Bindable
    public String getDieselPP1() {
        return dieselPP1;
    }

    public void setDieselPP1(String dieselPP1) {
        this.dieselPP1 = dieselPP1;
        notifyPropertyChanged(BR.dieselPP1);
        setDieselTotal();
    }

    @Bindable
    public String getDieselPP2() {
        return dieselPP2;
    }

    public void setDieselPP2(String dieselPP2) {
        this.dieselPP2 = dieselPP2;
        notifyPropertyChanged(BR.dieselPP2);
        setDieselTotal();
    }

    @Bindable
    public String getDieselPP3() {
        return dieselPP3;
    }

    public void setDieselPP3(String dieselPP3) {
        this.dieselPP3 = dieselPP3;
        notifyPropertyChanged(BR.dieselPP3);
        setDieselTotal();
    }

    @Bindable
    public String getDieselTotal() {
        return dieselTotal;
    }

    private void setDieselTotal() {
        float total = 0;

        if (!TextUtils.isEmpty(this.dieselPP1)) total += Float.valueOf(this.dieselPP1);
        if (!TextUtils.isEmpty(this.dieselPP2)) total += Float.valueOf(this.dieselPP2);
        if (!TextUtils.isEmpty(this.dieselPP3)) total += Float.valueOf(this.dieselPP3);

        this.dieselTotal = String.valueOf(total);
        notifyPropertyChanged(BR.dieselTotal);
        this.setAverage();
    }

    @Bindable
    public String getAverage() {
        return average;
    }

    public void setAverage() {
        if (!TextUtils.isEmpty(this.distance)
                && !TextUtils.isEmpty(this.dieselTotal)
                && !this.dieselTotal.equals("0.0")) {
            this.average = String.valueOf(Float.parseFloat(this.distance) / Float.parseFloat(this.dieselTotal));
        } else {
            this.average = "";
        }

        notifyPropertyChanged(BR.average);
    }

    @Bindable
    public String getBalanceHSD() {
        return balanceHSD;
    }

    public void setBalanceHSD(String balanceHSD) {
        this.balanceHSD = balanceHSD;
        notifyPropertyChanged(BR.balanceHSD);
    }

    @Bindable
    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
        notifyPropertyChanged(BR.cash);
    }

    @Bindable
    public String getTollTax() {
        return tollTax;
    }

    public void setTollTax(String tollTax) {
        this.tollTax = tollTax;
        notifyPropertyChanged(BR.tollTax);
    }

    @Bindable
    public String getBalanceCash() {
        return balanceCash;
    }

    public void setBalanceCash(String balanceCash) {
        this.balanceCash = balanceCash;
        notifyPropertyChanged(BR.balanceCash);
    }
}
