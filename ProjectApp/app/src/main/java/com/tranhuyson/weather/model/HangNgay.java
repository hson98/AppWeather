package com.tranhuyson.weather.model;

public class HangNgay {
    private long timeNgay;
    private String doAm;
    private int temp;
    private String icon;

    public HangNgay(long timeNgay, String doAm, int temp, String icon) {
        this.timeNgay = timeNgay;
        this.doAm = doAm;
        this.temp = temp;
        this.icon = icon;
    }

    public long getTimeNgay() {
        return timeNgay;
    }

    public void setTimeNgay(long timeNgay) {
        this.timeNgay = timeNgay;
    }

    public String getDoAm() {
        return doAm;
    }

    public void setDoAm(String doAm) {
        this.doAm = doAm;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
