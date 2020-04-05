package com.example.thanhhien.BanHang.Seo_BanHangLe;

public class Model_ListSanPhamBanLe {
    String IDSanPham,TenSanPham;

    public Model_ListSanPhamBanLe(String IDSanPham, String tenSanPham) {
        this.IDSanPham = IDSanPham;
        TenSanPham = tenSanPham;
    }

    public Model_ListSanPhamBanLe(String tenSanPham) {
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
