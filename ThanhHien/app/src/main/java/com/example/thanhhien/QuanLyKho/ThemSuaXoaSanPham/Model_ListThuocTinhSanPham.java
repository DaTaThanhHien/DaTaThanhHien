package com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham;

public class Model_ListThuocTinhSanPham {
    String IDSanPham,TenSanPham;

    public Model_ListThuocTinhSanPham(String IDSanPham, String tenSanPham) {
        this.IDSanPham = IDSanPham;
        TenSanPham = tenSanPham;
    }

    public Model_ListThuocTinhSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(String IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }
}
