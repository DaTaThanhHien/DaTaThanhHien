package com.example.thanhhien;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.thanhhien.BanHang.Seo_BanHangLe.Seo_BanHangLe;
import com.example.thanhhien.HoaDon.Seo_QuanLyHoaDon;
import com.example.thanhhien.LichSuNhapXuat.Seo_LichSuNhapXuat;
import com.example.thanhhien.NhaCungcap.Seo_QuanLyNhaCungCap;
import com.example.thanhhien.NhapXuatHang.Seo_ChonNhaCungCap;
import com.example.thanhhien.NhapXuatHang.Seo_GiaoDienDanhMuc;
import com.example.thanhhien.NhapXuatHang.Seo_ListSanPhamNhapKho;
import com.example.thanhhien.QuanLyKho.SanPhamKho.Model_LichSuNhapXuatChiTiet;
import com.example.thanhhien.QuanLyKho.SanPhamKho.Seo_QuanLySanPhamKho;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Seo_ThemSanPhamMoi;
import com.example.thanhhien.ThongKe.Seo_QuanLyThongKe;

import com.google.android.material.navigation.NavigationView;
import com.sdsmdg.tastytoast.TastyToast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Seo_GiaoDienChinh extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private LinearLayout btnBanHang,btnHoaDon,btnKhachHang,btnKho,btnThongKe,btnlichSuPhieuNhapXuat,btnNhaCungCap,btnThongKeNgay,btnNhapHang,btnThemSanPham;
    private TextView txtTongTienTheoNgay;
    private int TongTien;
    private int NgayTrongTuan;
    private BarChart mBarChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_giaodienchinh);
        setTitle("Trang chủ");
        AnhXa();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.imgmenudrawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        onClick();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        Date today = new Date();
        final String NgayHomNay = dateFormatter.format(today);
        if(isOnline()==false){
            Intent intent=new Intent(Seo_GiaoDienChinh.this,SeoCheckConnection.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }else {
            GetThuNhapHomNay(Api_custom.ThuNhapHomNay,NgayHomNay);
        }


        // dữ liệu biểu đồ
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

    }
    private void onClick() {
        btnBanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_BanHangLe.class);
                startActivity(intent);
            }
        });
        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_QuanLyHoaDon.class);
                startActivity(intent);
            }
        });
        btnNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_QuanLyNhaCungCap.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        btnKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_QuanLySanPhamKho.class);
                startActivity(intent);
            }
        });

        btnlichSuPhieuNhapXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_LichSuNhapXuat.class);
                startActivity(intent);

            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_QuanLyThongKe.class);
                startActivity(intent);
            }
        });
        btnThongKeNgay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v) {
                TongTien=0;

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_QuanLyThongKe.class);
                startActivity(intent);
            }
        });

        btnNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_ChonNhaCungCap.class);
                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_GiaoDienDanhMuc.class);
                startActivity(intent);

            }
        });
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Seo_GiaoDienChinh.this, Seo_ThemSanPhamMoi.class);
                startActivity(intent);

            }
        });

    }

    private void AnhXa() {
        mDrawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBarChart= (BarChart) findViewById(R.id.barchart);
        txtTongTienTheoNgay=findViewById(R.id.txtTongTienTheoNgay);
        btnBanHang=findViewById(R.id.btnBanHang);
        btnHoaDon=findViewById(R.id.btnHoaDon);
        btnKhachHang=findViewById(R.id.btnKhachHang);
        btnKho=findViewById(R.id.btnKho);
        btnThongKe=findViewById(R.id.btnKho);
        btnlichSuPhieuNhapXuat=findViewById(R.id.btnlichSuPhieuNhapXuat);
        btnNhaCungCap=findViewById(R.id.btnNhaCungCap);
        btnThongKe=findViewById(R.id.btnThongKe);
        btnThongKeNgay=findViewById(R.id.btnThongKeNgay);
        btnNhapHang=findViewById(R.id.btnNhapHang);
        btnThemSanPham=findViewById(R.id.btnThemSanPham);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.nav_thongbao:

                Intent intent=new Intent(Seo_GiaoDienChinh.this,Seo_TrangThongBao.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
                default:
                    break;

        }
    return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thongbao,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if(isOnline()==false){
            Intent intent=new Intent(Seo_GiaoDienChinh.this,SeoCheckConnection.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }else {
            mBarChart.clearChart();
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatter.setLenient(false);
            Date today = new Date();
            final String NgayHomNay = dateFormatter.format(today);
            GetThuNhapHomNay(Api_custom.ThuNhapHomNay,NgayHomNay);
        }
    }
    // check connect
    public boolean isOnline() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 1);
        }
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    public void GetThuNhapHomNay(String urlService,String NgayHomNay){
        urlService=urlService+NgayHomNay;
        final RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.getCache().clear();
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
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
                                    if(jsonObject.getString("ThuNhapHomNay").equalsIgnoreCase("null")){
                                        txtTongTienTheoNgay.setText("0 VNĐ");
                                        mBarChart.addBar(new BarModel("T 2",50000,0xFF123456));
                                        mBarChart.addBar(new BarModel("T 3",5000000,0xFF2E2EFE));
                                        mBarChart.addBar(new BarModel("T 4", 2000000, 0xFFFF8000));
                                        mBarChart.addBar(new BarModel("T 5", 4000000,0xFFFF0040));
                                        mBarChart.addBar(new BarModel("T 6",2600000, 0xFF56B7F1));
                                        mBarChart.addBar(new BarModel("T 7",2000000,  0xFF343456));
                                        mBarChart.addBar(new BarModel("CN",6000000, 0xFF1FF4AC));
                                    }else {
                                        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(jsonObject.getString("ThuNhapHomNay")+""));
                                        txtTongTienTheoNgay.setText(TongTienChuyenDoi+ " VNĐ");
                                        mBarChart.addBar(new BarModel("T 2",Integer.parseInt(jsonObject.getString("ThuNhapHomNay")),0xFF123456));
                                        mBarChart.addBar(new BarModel("T 3",5000000,0xFF2E2EFE));
                                        mBarChart.addBar(new BarModel("T 4", 2000000, 0xFFFF8000));
                                        mBarChart.addBar(new BarModel("T 5", 4000000,0xFFFF0040));
                                        mBarChart.addBar(new BarModel("T 6",2600000, 0xFF56B7F1));
                                        mBarChart.addBar(new BarModel("T 7",2000000,  0xFF343456));
                                        mBarChart.addBar(new BarModel("CN",6000000, 0xFF1FF4AC));

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mBarChart.startAnimation();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getApplicationContext(), "Lỗi lấy thu nhập hôm nay !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        onBackPressed();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }//end






}
