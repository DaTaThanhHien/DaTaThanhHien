package com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe;

public class Model_ListSanPhamBan {
    String MaSanPham,TenSanPham,SoLuong,GiaSanPham,ThuocTinh,NhaCungCap,DonViTinh,QuyCach,DonViNhap,Nang,Dai,SoLuongQuyDoi;

    public Model_ListSanPhamBan() {
    }

    public Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham, String thuocTinh, String nhaCungCap,  String donViTinh,String quyCach) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        GiaSanPham = giaSanPham;
        ThuocTinh = thuocTinh;
        NhaCungCap = nhaCungCap;
        QuyCach = quyCach;
        DonViTinh = donViTinh;
    }

    public Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham,String donViTinh, String thuocTinh, String quyCach) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        GiaSanPham = giaSanPham;
        DonViTinh = donViTinh;
        ThuocTinh = thuocTinh;
        QuyCach = quyCach;
    }
    public Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham,String donViTinh, String thuocTinh, String quyCach,String soLuongQuyDoi,String soLuongQuyDoi5,String soLuongQuyDoi4,String soLuongQuyDoi3,String soLuongQuyDoi2,String soLuongQuyDoi1) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        GiaSanPham = giaSanPham;
        DonViTinh = donViTinh;
        ThuocTinh = thuocTinh;
        QuyCach = quyCach;
        SoLuongQuyDoi = soLuongQuyDoi;
    }
    public Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham, String thuocTinh, String nhaCungCap, String donViTinh, String quyCach, String donViNhap) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        GiaSanPham = giaSanPham;
        ThuocTinh = thuocTinh;
        NhaCungCap = nhaCungCap;
        DonViTinh = donViTinh;
        QuyCach = quyCach;
        DonViNhap = donViNhap;
    }

    public Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        GiaSanPham = giaSanPham;
    }

    public Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham, String thuocTinh, String nhaCungCap, String donViTinh, String quyCach, String donViNhap, String nang, String dai) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        GiaSanPham = giaSanPham;
        ThuocTinh = thuocTinh;
        NhaCungCap = nhaCungCap;
        DonViTinh = donViTinh;
        QuyCach = quyCach;
        DonViNhap = donViNhap;
        Nang = nang;
        Dai = dai;
    }

    public Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham, String thuocTinh, String nhaCungCap, String donViTinh, String quyCach, String donViNhap, String nang, String Dai, String soLuongQuyDoi) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        SoLuong = soLuong;
        GiaSanPham = giaSanPham;
        ThuocTinh = thuocTinh;
        NhaCungCap = nhaCungCap;
        DonViTinh = donViTinh;
        QuyCach = quyCach;
        DonViNhap = donViNhap;
        Nang = nang;
        Dai = Dai;
        SoLuongQuyDoi = soLuongQuyDoi;
    }

    public String getSoLuongQuyDoi() {
        return SoLuongQuyDoi;
    }

    public void setSoLuongQuyDoi(String soLuongQuyDoi) {
        SoLuongQuyDoi = soLuongQuyDoi;
    }

    public String getNang() {
        return Nang;
    }

    public void setNang(String nang) {
        Nang = nang;
    }

    public String getDai() {
        return Dai;
    }

    public void setDai(String dai) {
        Dai = dai;
    }

    public String getQuyCach() {
        return QuyCach;
    }

    public void setQuyCach(String quyCach) {
        QuyCach = quyCach;
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

    public String getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(String giaSanPham) {
        GiaSanPham = giaSanPham;
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



    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        DonViTinh = donViTinh;
    }

    public String getDonViNhap() {
        return DonViNhap;
    }

    public void setDonViNhap(String donViNhap) {
        DonViNhap = donViNhap;
    }
}
