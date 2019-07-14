package com.tranhuyson.weather.model;

public class HangGio {
    private long time;
    private String icon;
    private int temp;
    private String doAm;

    public HangGio(long time, String icon, int temp, String doAm) {
        this.time = time;
        this.icon = icon;
        this.temp = temp;
        this.doAm = doAm;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getDoAm() {
        return doAm;
    }

    public void setDoAm(String doAm) {
        this.doAm = doAm;
    }
}
