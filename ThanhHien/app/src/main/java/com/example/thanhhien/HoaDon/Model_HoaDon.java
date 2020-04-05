package com.example.thanhhien.HoaDon;

public class Model_HoaDon {
    String MaHoaDon,TenKhachHang,NgayTao,TongTien,DaTra,No,SoDienThoai,DiaChi,TinhTrang;

    public Model_HoaDon(String maHoaDon, String tenKhachHang, String ngayTao, String tongTien, String daTra, String no, String soDienThoai, String diaChi, String tinhTrang) {
        MaHoaDon = maHoaDon;
        TenKhachHang = tenKhachHang;
        NgayTao = ngayTao;
        TongTien = tongTien;
        DaTra = daTra;
        No = no;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        TinhTrang = tinhTrang;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        MaHoaDon = maHoaDon;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getDaTra() {
        return DaTra;
    }

    public void setDaTra(String daTra) {
        DaTra = daTra;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

}
