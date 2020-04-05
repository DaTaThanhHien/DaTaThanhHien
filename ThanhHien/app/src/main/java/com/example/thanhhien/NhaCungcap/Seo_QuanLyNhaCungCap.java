package com.example.thanhhien.NhaCungcap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NhapXuatHang.Seo_ChonNhaCungCap;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Seo_QuanLyNhaCungCap extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerViewListNCC;
    private Adapter_NhaCungCap adapter_nhaCungCap;
    private ArrayList<Model_NhaCungCap> listNhaCungCap;
    private ShimmerFrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_quanlynhacungcap);
        setTitle("Nhà cung cấp");
        AnhXa();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        listNhaCungCap=new ArrayList<>();
        getAllNhaCungCap(Api_custom.GetTaCaNhaCungCap);
        adapter_nhaCungCap=new Adapter_NhaCungCap(Seo_QuanLyNhaCungCap.this,listNhaCungCap);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_QuanLyNhaCungCap.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListNCC.setLayoutManager(layoutManager);
        recyclerViewListNCC.setAdapter(adapter_nhaCungCap);

    }
    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewListNCC=findViewById(R.id.recyclerViewListNCC);
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
            case R.id.nav_themsanpham:

                Intent intent=new Intent(Seo_QuanLyNhaCungCap.this,Seo_ThemSuaXoaNhaCungCap.class);
                intent.putExtra("Ma","0");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_themsanpham,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listNhaCungCap.clear();
        getAllNhaCungCap(Api_custom.GetTaCaNhaCungCap);
        adapter_nhaCungCap.notifyDataSetChanged();
        container.setVisibility(View.VISIBLE);
        recyclerViewListNCC.setVisibility(View.GONE);

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
                                    listNhaCungCap.add(new Model_NhaCungCap(jsonObject.getString("IDNhaCungCap"),jsonObject.getString("TenNhaCungCap"),jsonObject.getString("SDT"),jsonObject.getString("DiaChi"),"650000"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        container.setVisibility(View.GONE);
                        recyclerViewListNCC.setVisibility(View.VISIBLE);
                        adapter_nhaCungCap.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_QuanLyNhaCungCap.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end

}
