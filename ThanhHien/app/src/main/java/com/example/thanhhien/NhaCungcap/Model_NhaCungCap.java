package com.example.thanhhien.NhaCungcap;

public class Model_NhaCungCap {
    String IdNhaCungCap,TenNhaCungCap,SoDienThoai,Email,DiaChi,No;

    public Model_NhaCungCap(String idNhaCungCap, String tenNhaCungCap) {
        IdNhaCungCap = idNhaCungCap;
        TenNhaCungCap = tenNhaCungCap;
    }

    public Model_NhaCungCap(String idNhaCungCap, String tenNhaCungCap, String soDienThoai, String diaChi) {
        IdNhaCungCap = idNhaCungCap;
        TenNhaCungCap = tenNhaCungCap;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
    }

    public Model_NhaCungCap(String idNhaCungCap, String tenNhaCungCap, String soDienThoai, String diaChi, String no) {
        IdNhaCungCap = idNhaCungCap;
        TenNhaCungCap = tenNhaCungCap;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        No = no;
    }

    public String getIdNhaCungCap() {
        return IdNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        IdNhaCungCap = idNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return TenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        TenNhaCungCap = tenNhaCungCap;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }
}
