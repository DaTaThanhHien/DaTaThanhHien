package com.example.thanhhien.HoaDon;

public class Model_ChiTietHoaDon {
    String TenSanPham,SoLuong,DonGia,ThanhTien;


    public Model_ChiTietHoaDon(String tenSanPham, String soLuong, String donGia, String thanhTien) {
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        DonGia = donGia;
        ThanhTien = thanhTien;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String donGia) {
        DonGia = donGia;
    }

    public String getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(String thanhTien) {
        ThanhTien = thanhTien;
    }
}
