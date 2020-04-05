package com.example.thanhhien.BanHang.Seo_BanHangLe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
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
import com.android.volley.toolbox.Volley;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_SanPhamDaChon.Model_SanPhamDaChon;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
/*
 * Người viết: Nguyễn Hữu Hai,Nguyễn Văn Thành
 * Date: 2019/12/20
 * Cái này seo viết đó nha
 *
 * */
public class Seo_BanHangLe extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView gridViewListSanPham;
    private Adapter_ListSanPhamBanLe adapter_listSanPhamBanLe;
    private ArrayList<Model_ListSanPhamBanLe> mListSanPhamBanLe;
    public static ArrayList<Model_SanPhamDaChon> mListSanPhamDaChon;
    public static long TongTien=0;
    public static ArrayList<Model_GoiYKhachHang> mListKhachHang;
    private ShimmerFrameLayout shimmer_view_quycach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_banhangle);
        setTitle("Dang mục sản phẩm");
        AnhXa();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        mListSanPhamBanLe=new ArrayList<>();
        //getAllDanhMucSanPham(Api_custom.listAllDanhMucSanPham);
        mListSanPhamBanLe.add(new Model_ListSanPhamBanLe("Sắt U"));
        adapter_listSanPhamBanLe=new Adapter_ListSanPhamBanLe(Seo_BanHangLe.this,R.layout.item_layoutsanphambanle,mListSanPhamBanLe);
        gridViewListSanPham.setAdapter(adapter_listSanPhamBanLe);
        gridViewListSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Model_ListSanPhamBanLe model_listSanPhamBanLe=mListSanPhamBanLe.get(position);
                        Intent intent=new Intent(Seo_BanHangLe.this, Seo_ListSanPhamBanLe.class);
                        intent.putExtra("MaDanhMucSanPham",model_listSanPhamBanLe.getIDSanPham());
                        intent.putExtra("TenSanPham",model_listSanPhamBanLe.getTenSanPham());
                        startActivity(intent);
            }
        });
        // get all khách hàng
        mListKhachHang=new ArrayList<>();
        getAllTenKhachHang(Api_custom.ListToanBoKhachHang);

    }
    private void AnhXa() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shimmer_view_quycach=findViewById(R.id.shimmer_view_quycach);
        gridViewListSanPham=(GridView) findViewById(R.id.gridViewListSanPham);
        if(mListSanPhamDaChon!=null){

        }else {
            mListSanPhamDaChon=new ArrayList<>();
        }
        gridViewListSanPham.setVisibility(View.VISIBLE);
        shimmer_view_quycach.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                TongTien=0;
                mListSanPhamDaChon.clear();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                            gridViewListSanPham.setVisibility(View.VISIBLE);
                            shimmer_view_quycach.setVisibility(View.GONE);
                            adapter_listSanPhamBanLe.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getApplicationContext(), "Lỗi lấy danh mục sản phẩm !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        onBackPressed();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
    public void getAllTenKhachHang(String urlService){
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
                                    mListKhachHang.add(new Model_GoiYKhachHang(jsonObject.getString("IDKH"),jsonObject.getString("TenKH"),jsonObject.getString("SDT"),jsonObject.getString("TongDuNo")));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getApplicationContext(), "Lỗi không thể lấy danh sách khách hàng !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }//end
}
