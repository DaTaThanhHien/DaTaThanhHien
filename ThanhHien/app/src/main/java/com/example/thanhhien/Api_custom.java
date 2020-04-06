package com.example.thanhhien;
public class Api_custom {

      public static String diaChiIP="https://db.cobacoffeeroastery.com/";
    public static String diaChiIP123456="http://192.168.1.105:8085/";
        //<=========================== phần bán hàng==============================>
        public static String GetTatCaSanPhamTheoDanhMuc=diaChiIP+"listSanPhamTheoDanhMucSP/";
        public static String ListToanBoKhachHang=diaChiIP+"ListToanBoKhachHang";
        public static String ThemHoaDon=diaChiIP+"AddHoaDon";
        public static String ThemCTHoaDon=diaChiIP+"AddCTHoaDon";
        public static String CapNhatSoLuongSanPham=diaChiIP+"CapNhatSoLuongSanPham";





        //<============================ Phần hóa đơn ============================>
        public static String ListToanBoHoaDon=diaChiIP+"ListToanBoHoaDon";





        //<============================ Phần quản lý kho ==========================>
        public static String ListToanBoSanPham=diaChiIP+"ListToanSanPham";
        public static String ListCTPhieuNhapSanPham=diaChiIP+"ListCTPhieuNhapSanPham/";





      //<==============================phần nhà cung cấp===================================>
      public static String GetTaCaNhaCungCap=diaChiIP+"listAllNhaCungCap";
        // thêm nhà cung cấp

      public static String ThemNhaCungCap=diaChiIP+"ThemNhaCungCap";



    //<==============================phần đơn vị tính===================================>
    public static String GetTaCaDonViTinh=diaChiIP+"listAllDonViTinh";




    //<==============================phần sản phẩm===================================>
    public static String ThemSanPhamMoi=diaChiIP+"ThemSanPhamMoi";
    public static String SuaSanPham=diaChiIP+"SuaSanPham";
    public static String GetTatCaSanPham=diaChiIP+"listAllSanPham";
    public static String GetSanPham=diaChiIP+"listSanPham/";





    //<==============================phần danh mục===================================>
    public static String GetTaCaDanhMuc=diaChiIP+"listAllDanhMuc";
    // thêm nhà cung cấp

    public static String ThemDanhMuc=diaChiIP+"ThemDanhMuc";

      // phần nhập hàng
    public static String listAllNhaCungCap=diaChiIP+"listAllNhaCungCap/";
    public static String listAllDanhMucSanPham=diaChiIP+"listAllDanhMucSanPham/";
    public static String listAllPhieuNhap=diaChiIP+"listAllPhieuNhap";
    public static String ThemPhieuNhap=diaChiIP+"ThemPhieuNhap";
    public static String ThemChiTietPhieuNhap=diaChiIP+"ThemChiTietPhieuNhap";
    public static String listQuyCachDanhMucSP=diaChiIP+"listQuyCachDanhMucSP/";
    public static String listQuyCachTheoNhaCungCapDanhMucSP=diaChiIP+"listQuyCachTheoNhaCungCapDanhMucSP/";
    public static String listSanPhamTheoQuyCachDanhMucSP=diaChiIP+"listSanPhamTheoQuyCachDanhMucSP/";
    public static String listSanPhamTheoQuyCachTheoNhaCungCapDanhMucSP=diaChiIP+"listSanPhamTheoQuyCachTheoNhaCungCapDanhMucSP/";
    public static String listSanPhamTheoDanhMucSPKoNCCBanDau=diaChiIP+"listSanPhamTheoDanhMucSPKoNCCBanDau/";
    public static String listSanPhamTheoNhaCungCapDanhMucSPBanDau=diaChiIP+"listSanPhamTheoNhaCungCapDanhMucSPBanDau/";
    public static String listSanPhamTheoIDSanPham=diaChiIP+"listSanPhamTheoIDSanPham/";
    public static String CapNhatHoanThanhDon=diaChiIP+"CapNhatTinhTrangPhieu";
    public static String ListChiTietPhieuNhap=diaChiIP+"listCTPhieuNhap/";








    //<================================= Phần thống kê ==============================>

    public static String ThuNhapHomNay=diaChiIP+"ThuNhapHomNay/";























    // địa chỉ đường dẫn
    public static String laytongtiendachitheothang=diaChiIP+"laytongtiendachitheothang/";
    public static String laytongtienbanletheongay=diaChiIP+"laytongtienbanletheongay/";
    public static String laytongtienbansitheongay=diaChiIP+"laytongtienbansitheongay/";
    // phần bán hàng

    public static String themHoaDonBanLe=diaChiIP+"themhoadonbanle";
    public static String themChiTietHoaDonBanLe=diaChiIP+"themchitiethoadonbanle";
    public static String listTatCaSanPham=diaChiIP+"listAllSanPham";
    public static String trusoluongsanphamkhibanra=diaChiIP+"trusoluongsanphamkhibanra";
    public static String laytatcathuoctinhsanpham=diaChiIP+"laytatcathuoctinhsanpham";
    public static String listallsanphamtheothuoctinh=diaChiIP+"listallsanphamtheothuoctinh/";
    public static String listallsanphamtheoidsanpham=diaChiIP+"listallsanphamtheoidsanpham/";
    public static String listallsanphamtheoidsanphamvaidthuoctinh=diaChiIP+"listallsanphamtheoidsanphamvaidthuoctinh/";
    public static String listallsanphamtheonccvaidsanpham=diaChiIP+"listallsanphamtheonccvaidsanpham/";
    public static String listallsanphamtheonccvaidthuoctinh=diaChiIP+"listallsanphamtheonccvaidthuoctinh/";
    public static String listallsanphamtheonccvaidthuoctinhvaidsanpham=diaChiIP+"listallsanphamtheonccvaidthuoctinhvaidsanpham/";
    //phần hóa đơn
    public static String layHoaDonBanLe=diaChiIP+"layHoaDonBanLe";
    public static String layCTHoaDonBanLe=diaChiIP+"layCTHoaDonBanLe/";

    public static String listKho=diaChiIP+"listKho";
    public static String listTatCaNhaCungCap=diaChiIP+"listNCC";
    public static String listThuotTinh=diaChiIP+"listThuotTinh";
    public static String CapNhatSoLuongSanPhamKhiNhap=diaChiIP+"CapNhatSoLuongSanPhamKhiNhap";
    public static String listSanPham=diaChiIP+"listSanPham";
    public static String listLichSuNhaphang=diaChiIP+"listLichSuNhaphang";
    public static String CapNhatIDSanPhamKhiSuaTrung=diaChiIP+"CapNhatIDSanPhamKhiSuaTrung";
    public static String CapNhatIDThuocTinhKhiSuaTrung=diaChiIP+"CapNhatIDThuocTinhKhiSuaTrung";
    public static String CapNhatChiTietSanPham=diaChiIP+"CapNhatChiTietSanPham";
    public static String CapNhatIDNhaCungCapChiTietSanPham=diaChiIP+"CapNhatIDNhaCungCapChiTietSanPham";
    public static String themchitietsanpham=diaChiIP+"themchitietsanpham";
    public static String themthuoctinhsanpham=diaChiIP+"themthuoctinhsanpham";
    public static String themKho=diaChiIP+"themKho";
    public static String themsanpham=diaChiIP+"themsanpham";
    public static String themphieuxuat=diaChiIP+"themphieuxuat";
    public static String themchitietphieuxuat=diaChiIP+"themchitietphieuxuat";
    public static String themncc=diaChiIP+"themncc";
    public static String listKiemTraKhoKhiThemMoi=diaChiIP+"listKiemTraKhoKhiThemMoi/";
    public static String listChiTietSanPhamTheoIDChiTietSanPham=diaChiIP+"listChiTietSanPhamTheoIDChiTietSanPham/";
    public static String listSanPhamTheoIDChiTietSanPham=diaChiIP+"listSanPhamTheoIDChiTietSanPham/";
    public static String listDanhSachPhieuNhapHomNay=diaChiIP+"listDanhSachPhieuNhapHomNay/";
    public static String listSoLuongTonNhapXuatTheoIDChiTietSanPham=diaChiIP+"listSoLuongTonNhapXuatTheoIDChiTietSanPham/";
    public static String listNCCKhiThemMoi=diaChiIP+"listNCCKhiThemMoi/";
    public static String listSanPhamTheoNhaCungCap=diaChiIP+"listSanPhamTungNhaCungCap/";
    public static String listKiemTraThuocTinhSanPham=diaChiIP+"listKiemTraThuocTinhSanPham/";
    public static String listKiemTraThemSanPham=diaChiIP+"listKiemTraThemSanPham/";
    public static String listKiemTraChiTietSanPham=diaChiIP+"listKiemTraChiTietSanPham/";
    public static String listKiemTraChiTietSanPham1=diaChiIP+"listKiemTraChiTietSanPham1/";
    public static String listKiemtraAllSanPhamThem=diaChiIP+"listKiemtraAllSanPhamThem/";
    public static String listSanPhamTungKho=diaChiIP+"listSanPhamTungKho/";
    public static String listChiTietSanPhamTheoID=diaChiIP+"listChiTietSanPhamTheoID/";
    public static String listKiemTraKhiCapNhatSoLuong=diaChiIP+"listKiemTraKhiCapNhatSoLuong/";
}

