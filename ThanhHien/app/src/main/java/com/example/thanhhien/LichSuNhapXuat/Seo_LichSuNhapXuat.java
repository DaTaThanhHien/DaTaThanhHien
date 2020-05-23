package com.example.thanhhien.LichSuNhapXuat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NhaCungcap.Model_NhaCungCap;
import com.example.thanhhien.NhapXuatHang.Adapter_NhaCungCap;
import com.example.thanhhien.NhapXuatHang.Seo_ChonNhaCungCap;
import com.example.thanhhien.NhapXuatHang.Seo_ListSanPhamNhapKho;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.roger.catloadinglibrary.CatLoadingView;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_LichSuNhapXuat extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerViewListPhieuNhap;
    private ArrayList<Model_LichSuNhapXuat> mListNhapXuat;
    private Adapter_LichSuNhapXuat adapter_lichSuNhapXuat;
    private ShimmerFrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_lichsunhapxuat);
        setTitle("Lịch sử nhập hàng");
        AnhXa();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        //loading khi chưa có dữ liệu tải lên
        // lấy dữ liệu đổ lên danh sách
        mListNhapXuat=new ArrayList<>();
        getAllPhieuNhap(Api_custom.listAllPhieuNhap);
        adapter_lichSuNhapXuat=new Adapter_LichSuNhapXuat(Seo_LichSuNhapXuat.this,mListNhapXuat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_LichSuNhapXuat.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListPhieuNhap.setLayoutManager(layoutManager);
        recyclerViewListPhieuNhap.setAdapter(adapter_lichSuNhapXuat);
        adapter_lichSuNhapXuat.setOnItemClickedListener(new Adapter_LichSuNhapXuat.OnItemClickedListener() {
            @Override
            public void onItemClick(String MaPhieuNhap, String NhaCungCap, String NgayTaoPhieu, String NgayNhap, String TongTien, String TongTienDaTra, String TinhTrang) {
                Intent intent=new Intent(Seo_LichSuNhapXuat.this,Seo_ChiTietNhapHang.class);
                intent.putExtra("MaPhieuNhap",MaPhieuNhap);
               intent.putExtra("NhaCungCap",NhaCungCap);
                intent.putExtra("NgayTaoPhieu",NgayTaoPhieu);
                intent.putExtra("NgayNhap",NgayNhap);
                intent.putExtra("TongTien",TongTien);
                intent.putExtra("TongTienDaTra",TongTienDaTra);
                intent.putExtra("TinhTrang",TinhTrang);
                startActivity(intent);
            }
        });
    }
    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewListPhieuNhap=findViewById(R.id.recyclerViewListPhieuNhap);
        container=findViewById(R.id.shimmer_view_container);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        container.setVisibility(View.VISIBLE);
        recyclerViewListPhieuNhap.setVisibility(View.GONE);
        mListNhapXuat.clear();
        getAllPhieuNhap(Api_custom.listAllPhieuNhap);
        adapter_lichSuNhapXuat.notifyDataSetChanged();
    }

    public void getAllPhieuNhap(String urlService){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.getCache().clear();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlService,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null&&response.length()!=0){
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    mListNhapXuat.add(new Model_LichSuNhapXuat(jsonObject.getString("IDPhieuNhap"),jsonObject.getString("TenNhaCungCap"),jsonObject.getString("NgayTaoPhieuNhap"),jsonObject.getString("TrangThaiPhieuNhap"),jsonObject.getString("TongPhieuNhap"),jsonObject.getString("SoTienTraTruoc"),jsonObject.getString("NgayTaoPhieuNhap")));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            container.setVisibility(View.GONE);
                            recyclerViewListPhieuNhap.setVisibility(View.VISIBLE);
                        }
                        adapter_lichSuNhapXuat.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getApplicationContext(), "Lỗi lấy hóa đơn nhập !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        onBackPressed();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end

}
