package com.tranhuyson.weather.model;

public class HangNgay {
    private long timeNgay;
    private String doAm;
    private int temp;
    private String icon;
    private String min;
    private String max;
    private String moTa;


    public HangNgay(long timeNgay, String doAm, int temp, String icon,String min,String max,String moTa) {
        this.timeNgay = timeNgay;
        this.doAm = doAm;
        this.temp = temp;
        this.icon = icon;
        this.min=min;
        this.max=max;
        this.moTa=moTa;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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
