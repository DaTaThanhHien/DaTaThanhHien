package com.example.thanhhien.LichSuNhapXuat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Adapter_GoiYKhachHang;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Model_GoiYKhachHang;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Seo_BanHangLe;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.HoaDon.Adapter_ChiTietHoaDon;
import com.example.thanhhien.HoaDon.Model_ChiTietHoaDon;
import com.example.thanhhien.HoaDon.Seo_ChiTietHoaDon;
import com.example.thanhhien.NhapXuatHang.Seo_ChonNhaCungCap;
import com.example.thanhhien.NhapXuatHang.Seo_ThanhToanNhapHang;
import com.example.thanhhien.R;
import com.google.android.material.tabs.TabLayout;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_ChiTietNhapHang extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtNhaCungCap,txtNgayTao,txtNgayNhap,
            txtTinhTrangPhieuNhap,txtTongTien,txtTongTienDaTra,
            txtMaPhieuNhap,TinhTrangPhieuNhap,TongTien;
    private Button btnHoanThanhDon,btnSuaDonNhapHang,btnXoaPhieuNhap;
    private RecyclerView recyclerViewListSanPham,recyclerViewListSanPhamNhap;
    private EditText editThanhToan;
    private ArrayList<Model_ChiTietHoaDon> mListChiTietHoaDon;
    private ArrayList<Model_ListSanPhamBan> mListSanPhamChiTiet;
    private Adapter_ChiTietHoaDon adapter_chiTietHoaDon;
    private LinearLayout layoutThongTin,layoutSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_chitietnhaphang);
        AnhXa();
        Intent intent=getIntent();
        setTitle(intent.getStringExtra("MaPhieuNhap")+"");
//        txtNhaCungCap.setText(intent.getStringExtra("NhaCungCap"));
        txtNgayNhap.setText(intent.getStringExtra("NgayNhap"));
        txtNgayTao.setText(intent.getStringExtra("NgayTaoPhieu"));
        txtTongTien.setText(intent.getStringExtra("TongTien") +"VNĐ");
        txtTongTienDaTra.setText(intent.getStringExtra("TongTienDaTra") +" VNĐ");
        txtMaPhieuNhap.setText(intent.getStringExtra("MaPhieuNhap"));
        if(intent.getStringExtra("TinhTrang").equals("-1")){
            txtTinhTrangPhieuNhap.setText("Đang sử lý");
            btnHoanThanhDon.setVisibility(View.VISIBLE);

        }else {
            txtTinhTrangPhieuNhap.setText("Hoàn thành");
            btnHoanThanhDon.setVisibility(View.GONE);

        }
        // gọi hàm sự kiện onclick
        onClick();
        // lấy chi tiết phiếu nhập
        mListSanPhamChiTiet=new ArrayList<>();
        mListChiTietHoaDon=new ArrayList<>();
        getChiTietPhieuNhap(Api_custom.ListChiTietPhieuNhap,intent.getStringExtra("MaPhieuNhap"));
        adapter_chiTietHoaDon=new Adapter_ChiTietHoaDon(Seo_ChiTietNhapHang.this,mListChiTietHoaDon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ChiTietNhapHang.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        recyclerViewListSanPham.setAdapter(adapter_chiTietHoaDon);
    }

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewListSanPham=findViewById(R.id.recyclerViewListSanPham);
        txtNhaCungCap=findViewById(R.id.txtNhaCungCap);
        txtNgayTao=findViewById(R.id.txtNgayTao);
        txtNgayNhap=findViewById(R.id.txtNgayNhap);
        txtTinhTrangPhieuNhap=findViewById(R.id.txtTinhTrangPhieuNhap);
        txtTongTien=findViewById(R.id.txtTongTien);
        txtTongTienDaTra=findViewById(R.id.txtTongTienDaTra);
        btnHoanThanhDon=findViewById(R.id.btnHoanThanhDon);
        btnSuaDonNhapHang=findViewById(R.id.btnSuaDonNhapHang);
        btnXoaPhieuNhap=findViewById(R.id.btnXoaPhieuNhap);
        txtMaPhieuNhap=findViewById(R.id.txtMaPhieuNhap);

    }
    private void onClick(){
        btnHoanThanhDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(Seo_ChiTietNhapHang.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Xác nhận hoàn thành phiếu nhập")
                        .setConfirmText("Xác nhận")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                CapNhatHoanThanhDon(Api_custom.CapNhatHoanThanhDon,"2");
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

        // sự kiện qua trang sử đơn
        btnSuaDonNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBottomSuaHoaDonNhap();

            }
        });
        btnXoaPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(Seo_ChiTietNhapHang.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Xác nhận xóa phiếu nhập")
                        .setConfirmText("Xác nhận")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                CapNhatHoanThanhDon(Api_custom.CapNhatHoanThanhDon,"3");
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
    private void onClickBottomSuaHoaDonNhap() {
        final LayoutInflater inflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.seo_suadonhangnhap, null);
        // khái báo ánh xạ
        final TabLayout tabLayout = view.findViewById(R.id.tabs);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnCapNhat=view.findViewById(R.id.btnCapNhat);
        EditText edit_TenNhaCungCap=view.findViewById(R.id.edit_TenNhaCungCap);
        TinhTrangPhieuNhap=view.findViewById(R.id.txtTinhTrangPhieuNhap);
        TongTien=view.findViewById(R.id.txtTongTien);
        layoutThongTin=view.findViewById(R.id.layoutThongTin);
        layoutSanPham=view.findViewById(R.id.layoutSanPham);
        editThanhToan=view.findViewById(R.id.editThanhToan);
        recyclerViewListSanPhamNhap=view.findViewById(R.id.recyclerViewListSanPhamNhap);
        Adapter_SanPhamPhieuNhap adapter_sanPhamPhieuNhap=new Adapter_SanPhamPhieuNhap(Seo_ChiTietNhapHang.this,mListSanPhamChiTiet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ChiTietNhapHang.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPhamNhap.setLayoutManager(layoutManager);
        recyclerViewListSanPhamNhap.setAdapter(adapter_sanPhamPhieuNhap);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ChiTietNhapHang.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        btnclosedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        // set tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    layoutSanPham.setVisibility(View.VISIBLE);
                    layoutThongTin.setVisibility(View.GONE);
                }else {
                    layoutSanPham.setVisibility(View.GONE);
                    layoutThongTin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // sự kiện cli
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // set dữ liệu vào các biến
        edit_TenNhaCungCap.setText(txtNhaCungCap.getText().toString());
        TinhTrangPhieuNhap.setText(txtTinhTrangPhieuNhap.getText().toString());
        TongTien.setText(txtTongTien.getText().toString());
        editThanhToan.setText(txtTongTienDaTra.getText());
    }// kết thúc hàm

    private  void CapNhatHoanThanhDon(String url, final String TingTrangPhieu){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
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
                        new SweetAlertDialog(Seo_ChiTietNhapHang.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Thành công")
                                .setContentText("Hoàn thành đơn")
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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(Seo_ChiTietNhapHang.this,"Lỗi không thể hoàn thành đơn",TastyToast.LENGTH_SHORT,TastyToast.ERROR);

                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDPhieuNhap",txtMaPhieuNhap.getText().toString());
                params.put("TrangThaiPhieuNhap",TingTrangPhieu);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//kết thúc hàm

    // lấy chi tiết phiếu nhập
    public void getChiTietPhieuNhap(String urlService,String MaPhieuNhap){
        urlService=urlService+MaPhieuNhap;
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
                                    mListChiTietHoaDon.add(new Model_ChiTietHoaDon(jsonObject.getString("TenSP"),jsonObject.getString("SoLuongSP"),jsonObject.getString("DonGiaNhapSP"),jsonObject.getString("TongTienCTPhieuNhap")));
                                    mListSanPhamChiTiet.add(new Model_ListSanPhamBan(jsonObject.getString("IDSanPham"),jsonObject.getString("TenSP"),jsonObject.getString("SoLuongSP"),jsonObject.getString("DonGiaNhapSP")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        adapter_chiTietHoaDon.notifyDataSetChanged();
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
        requestQueue.add(jsonArrayRequest);
    }// kết thúc hàm


}
