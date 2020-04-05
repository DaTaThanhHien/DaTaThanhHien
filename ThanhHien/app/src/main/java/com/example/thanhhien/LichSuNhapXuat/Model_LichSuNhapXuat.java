package com.example.thanhhien.LichSuNhapXuat;

public class Model_LichSuNhapXuat {
    String TenNhaCungCap,IDPhieuNhap,NgayTao,TinhTrang,TongTien,TongTienDaTra,NgayNhap;

    public Model_LichSuNhapXuat( String IDPhieuNhap,String tenNhaCungCap, String ngayTao, String tinhTrang, String tongTien, String tongTienDaTra, String ngayNhap) {
        TenNhaCungCap = tenNhaCungCap;
        this.IDPhieuNhap = IDPhieuNhap;
        NgayTao = ngayTao;
        TinhTrang = tinhTrang;
        TongTien = tongTien;
        TongTienDaTra = tongTienDaTra;
        NgayNhap = ngayNhap;
    }

    public String getTenNhaCungCap() {
        return TenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        TenNhaCungCap = tenNhaCungCap;
    }

    public String getIDPhieuNhap() {
        return IDPhieuNhap;
    }

    public void setIDPhieuNhap(String IDPhieuNhap) {
        this.IDPhieuNhap = IDPhieuNhap;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getTongTienDaTra() {
        return TongTienDaTra;
    }

    public void setTongTienDaTra(String tongTienDaTra) {
        TongTienDaTra = tongTienDaTra;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }
}
