package com.example.thanhhien.BanHang.Seo_BanHangLe;
/*
 * Người viết: Nguyễn Hữu Hai,Nguyễn Văn Thành
 * Date: 2019/12/20
 * Cái này seo viết đó nha
 *
 * */
public class Model_GoiYKhachHang {
    String MaKhachHang,TenKhachHang,SoDienThoai,NoTon;

    public Model_GoiYKhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, String noTon) {
        MaKhachHang = maKhachHang;
        TenKhachHang = tenKhachHang;
        SoDienThoai = soDienThoai;
        NoTon = noTon;
    }

    public String getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        MaKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getNoTon() {
        return NoTon;
    }

    public void setNoTon(String noTon) {
        NoTon = noTon;
    }
}
