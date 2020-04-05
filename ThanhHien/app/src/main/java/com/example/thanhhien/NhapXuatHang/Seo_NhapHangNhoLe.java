package com.example.thanhhien.NhapXuatHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.thanhhien.BanHang.Seo_BanHangLe.Adapter_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Model_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Seo_BanHangLe;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;

import java.util.ArrayList;

public class Seo_NhapHangNhoLe extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView gridViewListSanPham;
    private Adapter_ListSanPhamBanLe adapter_listSanPhamBanLe;
    private ArrayList<Model_ListSanPhamBanLe> mListSanPhamBanLe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_nhaphangnhole);
        setTitle("Nhập hàng nhỏ lẻ");
        AnhXa();
        new NukeSSLCerts().nuke();
        mListSanPhamBanLe=new ArrayList<>();
        mListSanPhamBanLe.add(new Model_ListSanPhamBanLe("01","Đinh"));
        mListSanPhamBanLe.add(new Model_ListSanPhamBanLe("02","Ốc vít"));
        mListSanPhamBanLe.add(new Model_ListSanPhamBanLe("03","Đinh rút"));
        mListSanPhamBanLe.add(new Model_ListSanPhamBanLe("04","Tắc kê"));
        mListSanPhamBanLe.add(new Model_ListSanPhamBanLe("05","Bu lông lục"));
        adapter_listSanPhamBanLe=new Adapter_ListSanPhamBanLe(Seo_NhapHangNhoLe.this,R.layout.item_layoutsanphambanle,mListSanPhamBanLe);
        gridViewListSanPham.setAdapter(adapter_listSanPhamBanLe);
        gridViewListSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Seo_NhapHangNhoLe.this, "Chọn sản phẩm nhỏ lẻ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridViewListSanPham=findViewById(R.id.gridViewListSanPham);
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
