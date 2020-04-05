package com.example.thanhhien.BanHang.Seo_SanPhamDaChon;
/*
 * Người viết: Nguyễn Hữu Hai,Nguyễn Văn Thành
 * Date: 2019/12/20
 * Cái này seo viết đó nha
 *
 * */
public class Model_SanPhamDaChon {
    String MaSanPham,TenSanPham,SoLuong,DonViTinh,DonGia,ThuocTinh,NhaCungCap,SoLuongNhoLe,SoTienNhoLe;


    public Model_SanPhamDaChon(String maSanPham, String tenSanPham, String soLuong, String donViTinh, String donGia, String thuocTinh, String nhaCungCap, String soLuongNhoLe, String soTienNhoLe) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        DonViTinh = donViTinh;
        DonGia = donGia;
        ThuocTinh = thuocTinh;
        NhaCungCap = nhaCungCap;
        SoLuongNhoLe = soLuongNhoLe;
        SoTienNhoLe = soTienNhoLe;
    }

    public String getSoLuongNhoLe() {
        return SoLuongNhoLe;
    }

    public void setSoLuongNhoLe(String soLuongNhoLe) {
        SoLuongNhoLe = soLuongNhoLe;
    }

    public String getSoTienNhoLe() {
        return SoTienNhoLe;
    }

    public void setSoTienNhoLe(String soTienNhoLe) {
        SoTienNhoLe = soTienNhoLe;
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

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        DonViTinh = donViTinh;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String donGia) {
        DonGia = donGia;
    }

    public String getThuocTinh() {
        return ThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        ThuocTinh = thuocTinh;
    }

    public String getNhaCungCap() {
        return NhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        NhaCungCap = nhaCungCap;
    }
}
