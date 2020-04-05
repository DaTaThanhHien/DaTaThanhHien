package com.example.thanhhien.NhapXuatHang;

public class Model_ChonNguyenLieu {
    private String IDThuocTinh,iDChiTietSanPham,IDNhaCungCap,GiaNhap,GiaBan,SoLuong,IDSanPham,IDKho,soLuongMua,TenSanPham;

    public Model_ChonNguyenLieu(String IDThuocTinh, String iDChiTietSanPham, String IDNhaCungCap, String giaNhap, String giaBan, String soLuong, String IDSanPham, String IDKho, String soLuongMua, String tenSanPham) {
        this.IDThuocTinh = IDThuocTinh;
        this.iDChiTietSanPham = iDChiTietSanPham;
        this.IDNhaCungCap = IDNhaCungCap;
        GiaNhap = giaNhap;
        GiaBan = giaBan;
        SoLuong = soLuong;
        this.IDSanPham = IDSanPham;
        this.IDKho = IDKho;
        this.soLuongMua = soLuongMua;
        TenSanPham = tenSanPham;
    }

    public String getiDChiTietSanPham() {
        return iDChiTietSanPham;
    }

    public void setiDChiTietSanPham(String iDChiTietSanPham) {
        this.iDChiTietSanPham = iDChiTietSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(String soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public String getIDThuocTinh() {
        return IDThuocTinh;
    }

    public void setIDThuocTinh(String IDThuocTinh) {
        this.IDThuocTinh = IDThuocTinh;
    }

    public String getIDNhaCungCap() {
        return IDNhaCungCap;
    }

    public void setIDNhaCungCap(String IDNhaCungCap) {
        this.IDNhaCungCap = IDNhaCungCap;
    }

    public String getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(String giaNhap) {
        GiaNhap = giaNhap;
    }

    public String getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(String giaBan) {
        GiaBan = giaBan;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(String IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public String getIDKho() {
        return IDKho;
    }

    public void setIDKho(String IDKho) {
        this.IDKho = IDKho;
    }
}
