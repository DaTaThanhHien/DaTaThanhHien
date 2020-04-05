package com.example.thanhhien.HoaDon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;

import java.util.ArrayList;

public class Seo_ChiTietHoaDon extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtMaHoaDon,txtTenKhachHang,txtNgayTao,txtNgayNhap,txtTongTien,txtTongTienDaTra;
    private Button btnHoanThanhDon;
    private RecyclerView recyclerViewListSanPham;
    private ArrayList<Model_ChiTietHoaDon> mListChiTietHoaDon;
    private Adapter_ChiTietHoaDon adapter_chiTietHoaDon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_chitiethoadon);
        setTitle("Chi tiết hóa đơn");
        Intent intent=getIntent();
        AnhXa();
        txtMaHoaDon.setText(intent.getStringExtra("MaHoaDon"));
        txtTenKhachHang.setText(intent.getStringExtra("TenKhachHang"));
        final  String NgayTaoDon= ChuyenDoiTongTien.formatDateTime(intent.getStringExtra("NgayTao"));
        txtNgayTao.setText(NgayTaoDon);
        final  String NgayNhap= ChuyenDoiTongTien.formatDateTime(intent.getStringExtra("NgayThanhToan"));
        txtNgayNhap.setText(NgayNhap);
        txtTongTien.setText(intent.getStringExtra("TongTien") +" VNĐ");
        txtTongTienDaTra.setText(intent.getStringExtra("DaTra") +" VNĐ");
        if(intent.getStringExtra("TinhTrang").equalsIgnoreCase("2")){
            btnHoanThanhDon.setVisibility(View.GONE);
        }
        //get sản phẩm chi tiết
        mListChiTietHoaDon=new ArrayList<>();
        adapter_chiTietHoaDon=new Adapter_ChiTietHoaDon(Seo_ChiTietHoaDon.this,mListChiTietHoaDon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ChiTietHoaDon.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        recyclerViewListSanPham.setAdapter(adapter_chiTietHoaDon);
    }

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnHoanThanhDon=findViewById(R.id.btnHoanThanhDon);
        txtMaHoaDon=findViewById(R.id.txtMaHoaDon);
        txtTenKhachHang=findViewById(R.id.txtTenKhachHang);
        txtNgayNhap=findViewById(R.id.txtNgayNhap);
        txtNgayTao=findViewById(R.id.txtNgayTao);
        txtTongTien=findViewById(R.id.txtTongTien);
        txtTongTienDaTra=findViewById(R.id.txtTongTienDaTra);
        recyclerViewListSanPham=findViewById(R.id.recyclerViewListSanPham);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
