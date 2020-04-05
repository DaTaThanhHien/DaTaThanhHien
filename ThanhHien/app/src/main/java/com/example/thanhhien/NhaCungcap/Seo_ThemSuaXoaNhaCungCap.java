package com.example.thanhhien.NhaCungcap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NhapXuatHang.Seo_ThanhToanNhapHang;
import com.example.thanhhien.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_ThemSuaXoaNhaCungCap extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnThemNhaCungCap,btnSuaNhaCungCap;
    private EditText edit_TenNhaCungCap,edit_SoDienThoai,edit_DiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_themsuaxoanhacungcap);

        AnhXa();
        Intent intent=getIntent();
        if(intent.getStringExtra("Ma").equals("0")){
            btnSuaNhaCungCap.setVisibility(View.GONE);
            setTitle("Thêm nhà cung cấp");
        }else if(intent.getStringExtra("Ma").equals("1")){
            btnThemNhaCungCap.setVisibility(View.GONE);
            setTitle("Sửa nhà cung cấp");
        }
        btnThemNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(Seo_ThemSuaXoaNhaCungCap.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Xác nhận thêm nhà cung cấp")
                        .setConfirmText("Thêm")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
                                dateFormatter.setLenient(false);
                                Date today = new Date();
                                final String MaNhaCungCap = dateFormatter.format(today);
                                ThemNhaCungCap(Api_custom.ThemNhaCungCap,MaNhaCungCap,edit_TenNhaCungCap.getText().toString(),edit_SoDienThoai.getText().toString(),edit_DiaChi.getText().toString());
                            }
                        })
                        .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

    }

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnThemNhaCungCap=findViewById(R.id.btnThemNhaCungCap);
        btnSuaNhaCungCap=findViewById(R.id.btnSuaNhaCungCap);
        edit_TenNhaCungCap=findViewById(R.id.edit_TenNhaCungCap);
        edit_SoDienThoai=findViewById(R.id.edit_SoDienThoai);
        edit_DiaChi=findViewById(R.id.edit_DiaChi);
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

    // hàm thêm nhà cung cấp
    private  void ThemNhaCungCap(String url, final String MaNhaCungCap, final String TenNhaCungCap, final String SoDienThoai, final String DiaChi){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.getCache().clear();
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("successed")){
                            new SweetAlertDialog(Seo_ThemSuaXoaNhaCungCap.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setContentText("Thành công")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            onBackPressed();
                                        }
                                    })
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThemSuaXoaNhaCungCap.this, "Lỗi thêm nhà cung cấp không thành công", Toast.LENGTH_SHORT).show();
                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("MaNhaCungCap","NCC-"+MaNhaCungCap+"A");
                params.put("TenNhaCungCap",TenNhaCungCap);
                params.put("SoDienThoai",SoDienThoai);
                params.put("DiaChi",DiaChi);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//end
}
