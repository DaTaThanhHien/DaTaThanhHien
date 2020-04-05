package com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe;

public class Model_QuyCachVaTinhChat {
    String MaQuyCachVaTT,TenQuyCachVaTT;

    public Model_QuyCachVaTinhChat(String maQuyCachVaTT, String tenQuyCachVaTT) {
        MaQuyCachVaTT = maQuyCachVaTT;
        TenQuyCachVaTT = tenQuyCachVaTT;
    }
    public Model_QuyCachVaTinhChat(String tenQuyCachVaTT) {
        TenQuyCachVaTT = tenQuyCachVaTT;
    }

    public String getMaQuyCachVaTT() {
        return MaQuyCachVaTT;
    }

    public void setMaQuyCachVaTT(String maQuyCachVaTT) {
        MaQuyCachVaTT = maQuyCachVaTT;
    }

    public String getTenQuyCachVaTT() {
        return TenQuyCachVaTT;
    }

    public void setTenQuyCachVaTT(String tenQuyCachVaTT) {
        TenQuyCachVaTT = tenQuyCachVaTT;
    }
}
