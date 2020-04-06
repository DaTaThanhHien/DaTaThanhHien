package com.example.thanhhien.QuanLyKho.SanPhamKho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_QuyCachVaTinhChat;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.MainActivity;
import com.example.thanhhien.NhapXuatHang.Seo_GiaoDienDanhMuc;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Model_ListThuocTinhSanPham;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Seo_SuaXoaSanPham;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Seo_ThemSanPhamMoi;
import com.example.thanhhien.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionButton;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_QuanLySanPhamKho extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edit_TimKiem;
    private RecyclerView recyclerViewListSanPham;
    private ArrayList<Model_SanPhamKho> sanPhamArrayList;
    private Adapter_SanPhamKho adapter_sanPhamKho;
    private ShimmerFrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_quanlykho);
        AnhXa();
        onClick();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        sanPhamArrayList=new ArrayList<>();
        //GetTatCaSanPham(Api_custom.ListToanBoSanPham);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        adapter_sanPhamKho=new Adapter_SanPhamKho(Seo_QuanLySanPhamKho.this,sanPhamArrayList);
        recyclerViewListSanPham.setAdapter(adapter_sanPhamKho);
        getAllSanPham(Api_custom.GetTatCaSanPham);
    }
    private void onClick() {
        edit_TimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter_sanPhamKho.getFilter().filter((edit_TimKiem.getText().toString().trim()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_TimKiem=findViewById(R.id.edit_TimKiem);
        recyclerViewListSanPham=findViewById(R.id.recyclerViewListSanPham);
        container= (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);

        container.setVisibility(View.GONE);
        recyclerViewListSanPham.setVisibility(View.VISIBLE);
        container.stopShimmer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.nav_themsanpham:
                Intent intent=new Intent(Seo_QuanLySanPhamKho.this, Seo_ThemSanPhamMoi.class);
                startActivity(intent);

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_themsanpham,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void getAllSanPham(String urlService){
        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        final Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

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
                                    String mangDonVi[]=jsonObject.getString("DonViTinh").split(";");
                                    String QuyCach="";
                                    String Day="";
                                    String Dai="";
                                    String Nang="";
                                    String Khac="";
                                    if(jsonObject.getString("QuyCach").trim().length()==0){
                                        QuyCach="Không";
                                    }else {QuyCach=jsonObject.getString("QuyCach").trim();}
                                    if(jsonObject.getString("DoDay").trim().length()==0){
                                        Day="Không";
                                    }else {Day=jsonObject.getString("DoDay").trim();}
                                    if(jsonObject.getString("Dai").trim().length()==0){
                                        Dai="Không";
                                    }else {Dai=jsonObject.getString("Dai").trim();}
                                    if(jsonObject.getString("TrongLuong").trim().length()==0){
                                        Nang="Không";
                                    }else {Nang=jsonObject.getString("TrongLuong").trim();}
                                    if(jsonObject.getString("thuoctinhkhac").trim().length()==0){
                                        Khac="Không";
                                    }else {Khac=jsonObject.getString("thuoctinhkhac").trim();}

                                    sanPhamArrayList.add(new Model_SanPhamKho(jsonObject.getString("IDSanPham"),jsonObject.getString("TenSP"),jsonObject.getString("giasi"),jsonObject.getString("SoLuong"),jsonObject.getString("tenncc"),"Quy cách: "+QuyCach+"- dày: "+Day+mangDonVi[0]+" - Dài: "+Dai+mangDonVi[1]+" - Nặng: "+Nang+mangDonVi[2]+" - Khác: "+Khac,jsonObject.getString("DonViTinh")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter_sanPhamKho=new Adapter_SanPhamKho(Seo_QuanLySanPhamKho.this,sanPhamArrayList);
                            recyclerViewListSanPham.setAdapter(adapter_sanPhamKho);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_QuanLySanPhamKho.this, "error getAllSanPham", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end getAllSanPham
}
