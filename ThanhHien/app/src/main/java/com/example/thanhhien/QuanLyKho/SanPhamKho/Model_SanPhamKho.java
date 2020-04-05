package com.example.thanhhien.QuanLyKho.SanPhamKho;

public class Model_SanPhamKho {
    String MaSanPham,TenSanPham,GiaSanPham,SoLuongSanPham,NhaCungCap,ThuocTinh,DonViTinh;

    public Model_SanPhamKho() {
    }

    public Model_SanPhamKho(String maSanPham, String tenSanPham, String giaSanPham, String soLuongSanPham, String nhaCungCap, String thuocTinh, String donViTinh) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        GiaSanPham = giaSanPham;
        SoLuongSanPham = soLuongSanPham;
        NhaCungCap = nhaCungCap;
        ThuocTinh = thuocTinh;
        DonViTinh = donViTinh;
    }

    public String getThuocTinh() {
        return ThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        ThuocTinh = thuocTinh;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        MaSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(String giaSanPham) {
        GiaSanPham = giaSanPham;
    }

    public String getSoLuongSanPham() {
        return SoLuongSanPham;
    }

    public void setSoLuongSanPham(String soLuongSanPham) {
        SoLuongSanPham = soLuongSanPham;
    }

    public String getNhaCungCap() {
        return NhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        NhaCungCap = nhaCungCap;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        DonViTinh = donViTinh;
    }
}
