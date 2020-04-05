package com.example.thanhhien.QuanLyKho.SanPhamKho;

public class Model_LichSuNhapXuatChiTiet {
    String MaSanPham,TenSanPham,MaPhieuNhapXuat,DonViTinh,SoLuong,NgayNhapXuat;

    public Model_LichSuNhapXuatChiTiet(String tenSanPham, String donViTinh, String soLuong, String ngayNhapXuat) {
        TenSanPham = tenSanPham;
        DonViTinh = donViTinh;
        SoLuong = soLuong;
        NgayNhapXuat = ngayNhapXuat;
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

    public String getMaPhieuNhapXuat() {
        return MaPhieuNhapXuat;
    }

    public void setMaPhieuNhapXuat(String maPhieuNhapXuat) {
        MaPhieuNhapXuat = maPhieuNhapXuat;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        DonViTinh = donViTinh;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getNgayNhapXuat() {
        return NgayNhapXuat;
    }

    public void setNgayNhapXuat(String ngayNhapXuat) {
        NgayNhapXuat = ngayNhapXuat;
    }
}
