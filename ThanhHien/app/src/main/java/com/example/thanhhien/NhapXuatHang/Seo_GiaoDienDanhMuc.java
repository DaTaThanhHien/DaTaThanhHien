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
import com.example.thanhhien.BanHang.Seo_BanHangLe.Adapter_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Model_ListSanPhamBanLe;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class Seo_GiaoDienDanhMuc extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView gridViewListSanPham;
    private Adapter_ListSanPhamBanLe adapter_listSanPhamBanLe;
    private ArrayList<Model_ListSanPhamBanLe> mListSanPhamBanLe;
    public static String IDDanhMuc="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_giaodiennhapxuathang);
        AnhXa();
        new NukeSSLCerts().nuke();
        mListSanPhamBanLe=new ArrayList<>();
        getAllDanhMucSanPham(Api_custom.listAllDanhMucSanPham);

        gridViewListSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IDDanhMuc=mListSanPhamBanLe.get(position).getIDSanPham();
                Intent intent=new Intent(Seo_GiaoDienDanhMuc.this,Seo_ListSanPhamNhapKho.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridViewListSanPham=(GridView) findViewById(R.id.gridViewListSanPham);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getAllDanhMucSanPham(String urlService){
        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

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
                                    mListSanPhamBanLe.add(new Model_ListSanPhamBanLe(jsonObject.getString("IDDanhMuc"),jsonObject.getString("TenDanhMuc")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter_listSanPhamBanLe=new Adapter_ListSanPhamBanLe(Seo_GiaoDienDanhMuc.this,R.layout.item_layoutsanphambanle,mListSanPhamBanLe);
                            gridViewListSanPham.setAdapter(adapter_listSanPhamBanLe);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_GiaoDienDanhMuc.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
}
