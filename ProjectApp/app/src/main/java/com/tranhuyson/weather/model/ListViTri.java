package com.tranhuyson.weather.model;

public class ListViTri {
    private String nameCity;
    private String anhMieuTa;
    private String ngayThang;
    private int nhietDo;
    private String quocGia;

    public ListViTri(String nameCity, String anhMieuTa, String ngayThang, int nhietDo, String quocGia) {
        this.nameCity = nameCity;
        this.anhMieuTa = anhMieuTa;
        this.ngayThang = ngayThang;
        this.nhietDo = nhietDo;
        this.quocGia = quocGia;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getAnhMieuTa() {
        return anhMieuTa;
    }

    public void setAnhMieuTa(String anhMieuTa) {
        this.anhMieuTa = anhMieuTa;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public int getNhietDo() {
        return nhietDo;
    }

    public void setNhietDo(int nhietDo) {
        this.nhietDo = nhietDo;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }
}
