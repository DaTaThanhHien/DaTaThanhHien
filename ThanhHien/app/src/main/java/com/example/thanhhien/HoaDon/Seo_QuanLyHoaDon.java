package com.example.thanhhien.HoaDon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.thanhhien.LichSuNhapXuat.Adapter_LichSuNhapXuat;
import com.example.thanhhien.LichSuNhapXuat.Model_LichSuNhapXuat;
import com.example.thanhhien.LichSuNhapXuat.Seo_LichSuNhapXuat;
import com.example.thanhhien.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_QuanLyHoaDon extends AppCompatActivity {
    private Toolbar toolbar;
    private Adapter_HoaDon adapter_hoaDon;
    private ArrayList<Model_HoaDon> mListHoaDon;
    private RecyclerView  recyclerViewListHoaDon;
    private ShimmerFrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_quanlyhoadon);
        setTitle("Quản lý hóa đơn");
        AnhXa();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        //loading khi chưa có dữ liệu tải lên


        // lấy dữ liệu đổ lên danh sách
        mListHoaDon=new ArrayList<>();
        getAllHoaDon(Api_custom.ListToanBoHoaDon);
        adapter_hoaDon=new Adapter_HoaDon(Seo_QuanLyHoaDon.this,mListHoaDon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_QuanLyHoaDon.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListHoaDon.setLayoutManager(layoutManager);
        recyclerViewListHoaDon.setAdapter(adapter_hoaDon);

    }
    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewListHoaDon=findViewById(R.id.recyclerViewListHoaDon);
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
    public void getAllHoaDon(String urlService){
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
                                    mListHoaDon.add(new Model_HoaDon(jsonObject.getString("IDHoaDon"),jsonObject.getString("TenKH"),jsonObject.getString("NgayThanhToan"),jsonObject.getString("TongThanhToan"),jsonObject.getString("SoTienTraTruoc"),jsonObject.getString("TongTienConLai"),jsonObject.getString("DC"),jsonObject.getString("SDT"),jsonObject.getString("TrangThaiHoaDon")));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            container.setVisibility(View.GONE);
                            recyclerViewListHoaDon.setVisibility(View.VISIBLE);
                        }
                        adapter_hoaDon.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getApplicationContext(), "Lỗi lấy hóa đơn !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        onBackPressed();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
}
