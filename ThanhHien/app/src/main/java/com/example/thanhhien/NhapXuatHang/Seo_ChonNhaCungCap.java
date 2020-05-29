package com.example.thanhhien.NhapXuatHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.example.thanhhien.NhaCungcap.Model_NhaCungCap;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Seo_ChonNhaCungCap extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView gridViewListNhaCungCap;
    private  Adapter_NhaCungCap adapter_nhaCungCap;
    private ArrayList<Model_NhaCungCap> mListNhaCungCap;
    private Button btnNhapHangNhoLe;
    public static String IDNhaCungCap="0";
    public static String TenNCC="0";
    public static ArrayList<Model_ListSanPhamBan> gioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_chonnhacungcap);
        setTitle("Chọn nhà cung cấp");
        AnhXa();
        new NukeSSLCerts().nuke();
        gioHang=new ArrayList<>();
        mListNhaCungCap=new ArrayList<>();
       // getAllNhaCungCap(Api_custom.listAllNhaCungCap);

        gridViewListNhaCungCap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gioHang.clear();
                IDNhaCungCap=mListNhaCungCap.get(position).getIdNhaCungCap();
                TenNCC=mListNhaCungCap.get(position).getTenNhaCungCap();
                Intent intent=new Intent(Seo_ChonNhaCungCap.this, Seo_GiaoDienDanhMuc.class);
                startActivity(intent);
            }
        });
         btnNhapHangNhoLe.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 gioHang.clear();
                 IDNhaCungCap="0";
                 TenNCC="0";
                 Intent intent=new Intent(Seo_ChonNhaCungCap.this, Seo_GiaoDienDanhMuc.class);
                 startActivity(intent);
//                 Intent intent=new Intent(Seo_ChonNhaCungCap.this,Seo_NhapHangNhoLe.class);
//                 startActivity(intent);
             }
         });

    }
    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridViewListNhaCungCap=findViewById(R.id.gridViewListNhaCungCap);
        btnNhapHangNhoLe=findViewById(R.id.btnNhapHangNhoLe);
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
    public void getAllNhaCungCap(String urlService){
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
                                    mListNhaCungCap.add(new Model_NhaCungCap(jsonObject.getString("IDNhaCungCap"),jsonObject.getString("TenNhaCungCap")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter_nhaCungCap=new Adapter_NhaCungCap(Seo_ChonNhaCungCap.this,mListNhaCungCap);
                            gridViewListNhaCungCap.setAdapter(adapter_nhaCungCap);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ChonNhaCungCap.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
}
