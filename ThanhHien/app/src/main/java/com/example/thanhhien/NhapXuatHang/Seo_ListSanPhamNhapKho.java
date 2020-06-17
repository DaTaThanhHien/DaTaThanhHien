package com.example.thanhhien.NhapXuatHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Adapter_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Model_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Adapter_QuyCachVaTinhChat;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_QuyCachVaTinhChat;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.QuanLyKho.SanPhamKho.Model_SanPhamKho;
import com.example.thanhhien.R;
import com.example.thanhhien.SeoCheckConnection;
import com.example.thanhhien.Seo_GiaoDienLogin;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_ListSanPhamNhapKho extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout llListSanPham;
    private GridView gridViewListQuyCach;
    private GridView gridViewListTinhChat;
    private Button btnGioHang,btnTrove;
    public static Button btnThanhToan;
    public static TextView txtTenQuyCach;
    private RecyclerView recyclerViewListSanPham;
    private Adapter_QuyCachVaTinhChat adapter_quyCachVaTinhChat;
    private Adapter_QuyCachVaTinhChat adapter_quyCachVaTinhChat2;
    private Adapter_ListSanPhamNhap adapter_listSanPhamBan;
    private Adapter_ListSanPhamBanLe adapter_listSanPhamBanLe;
    private ArrayList<Model_QuyCachVaTinhChat> mListQuyCach;
    private ArrayList<Model_ListSanPhamBanLe> mListDanhMuc;
    private ArrayList<Model_QuyCachVaTinhChat>mListTinhChat;
    private ArrayList<Model_QuyCachVaTinhChat> mListQuyCach2;
    private ArrayList<Model_ListSanPhamBan> mListSanPhamBan;
    private int SoTienNo=0;
    public static String tenSP="";
    public static long tongTien=0;
    private boolean isCheck=false;
    private String IDDanhMuc="Null";
    private ShimmerFrameLayout container,shimmer_view_danhmuc,shimmer_view_quycach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_listsanphamnhapkho);
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        AnhXa();
       TongTien();
        mListQuyCach=new ArrayList<>();
        mListQuyCach2=new ArrayList<>();
        mListTinhChat=new ArrayList<>();
        mListDanhMuc=new ArrayList<>();
        // lấy quy cách của từng sản phẩm theo tên danh mục của sản phẩm đó
        if(isOnline()==false){
            Intent intent=new Intent(Seo_ListSanPhamNhapKho.this, SeoCheckConnection.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }else {
            getQuyCachTungSanPham(Api_custom.listQuyCachDanhMucSP, Seo_GiaoDienDanhMuc.IDDanhMuc);
            getAllDanhMucSanPham(Api_custom.listAllDanhMucSanPham);
            getSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoDanhMuc, Seo_GiaoDienDanhMuc.IDDanhMuc,"NULLQUYCACH");
        }

        adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutquycachvatinhchat,mListQuyCach);
        gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);

        //
        gridViewListQuyCach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(mListQuyCach.get(position).getMaQuyCachVaTT()=="CODESEONE"){
                    eventViewMoreQuyCach();
                }else {
                    //get sản phẩm theo quy cách
                    mListSanPhamBan.clear();
                    recyclerViewListSanPham.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                    if(isCheck==false){
                        getSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoQuyCachDanhMucSP,Seo_GiaoDienDanhMuc.IDDanhMuc,mListQuyCach.get(position).getTenQuyCachVaTT());
                    }else {
                        getSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoQuyCachDanhMucSP,IDDanhMuc,mListQuyCach.get(position).getTenQuyCachVaTT());
                    }
                }

            }
        });
        //jjjsjsjs

        gridViewListTinhChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListQuyCach.clear();
                mListQuyCach2.clear();
                adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutquycachvatinhchat,mListQuyCach);
                gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);
                mListSanPhamBan.clear();
                adapter_listSanPhamBan=new Adapter_ListSanPhamNhap(Seo_ListSanPhamNhapKho.this,mListSanPhamBan);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ListSanPhamNhapKho.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewListSanPham.setLayoutManager(layoutManager);
                recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);
                IDDanhMuc=mListDanhMuc.get(position).getTenSanPham();
                mListSanPhamBan.clear();
                adapter_listSanPhamBan=new Adapter_ListSanPhamNhap(Seo_ListSanPhamNhapKho.this,mListSanPhamBan);
                recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);
                // sự kiện loading
                recyclerViewListSanPham.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                gridViewListQuyCach.setVisibility(View.GONE);
                shimmer_view_quycach.setVisibility(View.VISIBLE);
                // lấy sản phẩm theo danh mục
                isCheck=true;
                getSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoDanhMuc, IDDanhMuc,"NULLQUYCACH");
                getQuyCachTungSanPham(Api_custom.listQuyCachDanhMucSP, IDDanhMuc);

            }
        });

        // list sản phẩm
        mListSanPhamBan=new ArrayList<>();
        adapter_listSanPhamBan=new Adapter_ListSanPhamNhap(Seo_ListSanPhamNhapKho.this,mListSanPhamBan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ListSanPhamNhapKho.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);

        // sự kiện khác
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Seo_ListSanPhamNhapKho.this,Seo_BanHang_GioHang.class));
            }
        });
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Seo_ListSanPhamNhapKho.this,Seo_ThanhToanNhapHang.class);
                startActivity(intent);
            }
        });
    }
    private void TongTien(){
        tongTien=0;
        for (int i=0;i<Seo_GiaoDienDanhMuc.gioHang.size();i++){
            tongTien=tongTien+(Long.parseLong(Seo_GiaoDienDanhMuc.gioHang.get(i).getGiaSanPham())*Long.parseLong(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong()));
        }
        btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(tongTien)+" VNĐ");
    }
    private void AnhXa() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridViewListQuyCach=findViewById(R.id.gridViewListQuyCach);
        gridViewListTinhChat=findViewById(R.id.gridViewListTinhChat);
        recyclerViewListSanPham=findViewById(R.id.recyclerViewListSanPham);
        llListSanPham=findViewById(R.id.llListSanPham);
        txtTenQuyCach=findViewById(R.id.txtTenQuyCach);
        btnGioHang=findViewById(R.id.btnGioHang);
        btnTrove=findViewById(R.id.btnTrove);
        btnThanhToan=findViewById(R.id.btnThanhToan);
        container= (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimmer_view_danhmuc=findViewById(R.id.shimmer_view_danhmuc);
        shimmer_view_quycach=findViewById(R.id.shimmer_view_quycach);
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

        if(isOnline()==false){
            Intent intent=new Intent(Seo_ListSanPhamNhapKho.this, SeoCheckConnection.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }else {
            mListQuyCach.clear();
            mListQuyCach2.clear();
            mListTinhChat.clear();
            mListSanPhamBan.clear();
            mListDanhMuc.clear();
            getQuyCachTungSanPham(Api_custom.listQuyCachDanhMucSP, Seo_GiaoDienDanhMuc.IDDanhMuc);
            getAllDanhMucSanPham(Api_custom.listAllDanhMucSanPham);
            getSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoDanhMuc, Seo_GiaoDienDanhMuc.IDDanhMuc,"NULLQUYCACH");
        }
        TongTien();
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
    private void eventViewMoreQuyCach() {
        View view = getLayoutInflater().inflate(R.layout.item_bottomdialogquycachkhac, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        GridView gridViewListQuyCach=view.findViewById(R.id.gridViewListQuyCach);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ListSanPhamNhapKho.this, R.style.MaterialDialogSheet);
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
        adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutlistsanphambanle,mListQuyCach2);
        gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);
        gridViewListQuyCach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mListSanPhamBan.clear();
                if(isCheck==false){
                    recyclerViewListSanPham.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                    getSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoQuyCachDanhMucSP,Seo_GiaoDienDanhMuc.IDDanhMuc,mListQuyCach2.get(position).getTenQuyCachVaTT());
                }else {
                    recyclerViewListSanPham.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                    getSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoQuyCachDanhMucSP,IDDanhMuc,mListQuyCach2.get(position).getTenQuyCachVaTT());
                }
                Model_QuyCachVaTinhChat model_quyCachVaTinhChat=mListQuyCach2.get(position);
                txtTenQuyCach.setText(model_quyCachVaTinhChat.getTenQuyCachVaTT());
                mBottomSheetDialog.dismiss();
            }
        });

    }

    private void eventClickThanhToan() {
        final LayoutInflater inflater=(LayoutInflater) Seo_ListSanPhamNhapKho.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layuotthanhtoanhoadon, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnNhapHang=view.findViewById(R.id.btnNhapHang);
        Button btnNhapHangVaIn=view.findViewById(R.id.btnNhapHangVaIn);
        final TextView txtTienThua=view.findViewById(R.id.txtTienThua);
        final TextView editDaTra=view.findViewById(R.id.editDaTra);
        TextView txtTongTien=view.findViewById(R.id.txtTongTien);
        btnNhapHang.setText("Thanh toán");
        btnNhapHangVaIn.setText("Thanh toán và In");

        editDaTra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editDaTra.getText().toString().trim().length()<=0){
                    editDaTra.setText("0");
                }
                else {
                    SoTienNo=0;
                    String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(SoTienNo+""));
                    txtTienThua.setText(TongTienChuyenDoi);
                    if(SoTienNo<=0){
                        txtTienThua.setText("0");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Dialog mBottomSheetDialog = new Dialog(Seo_ListSanPhamNhapKho.this, R.style.MaterialDialogSheet);
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
        btnNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
                dateFormatter.setLenient(false);
                Date today = new Date();
                final String MaHoaDonBanLe = dateFormatter.format(today);
                DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormatter2.setLenient(false);
                Date today2= new Date();
                final String NgayTao = dateFormatter2.format(today2);

                new SweetAlertDialog(Seo_ListSanPhamNhapKho.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Xác nhận thanh toán")
                        .setConfirmText("Xác nhận")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                mBottomSheetDialog.dismiss();
                                SoTienNo=0;
                                if(editDaTra.getText().toString().equals("0")){
                                }
                                sDialog.dismissWithAnimation();
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
    public void getSanPhamTheoDanhMuc(String urlService,  String IDDanhMuc,String QuyCach){
        final RequestQueue requestQueue;
        if(QuyCach.equalsIgnoreCase("NULLQUYCACH")){
            urlService=urlService+IDDanhMuc;
        }else {
            urlService=urlService+IDDanhMuc+"/"+QuyCach;
        }
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

                                    mListSanPhamBan.add(new Model_ListSanPhamBan(jsonObject.getString("IDSanPham"),jsonObject.getString("TenSP")+" - "+QuyCach,jsonObject.getString("SoLuong"),jsonObject.getString("giasi"),"Dày: "+Day+mangDonVi[0]+" - Dài: "+Dai+mangDonVi[1]+" - Nặng: "+Nang+mangDonVi[2]+" - Khác: "+Khac,jsonObject.getString("tenncc"),mangDonVi[3],QuyCach,jsonObject.getString("DonViTinh"),jsonObject.getString("TrongLuong"),jsonObject.getString("Dai")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            container.setVisibility(View.GONE);
                            recyclerViewListSanPham.setVisibility(View.VISIBLE);
                            adapter_listSanPhamBan=new Adapter_ListSanPhamNhap(Seo_ListSanPhamNhapKho.this,mListSanPhamBan);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ListSanPhamNhapKho.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerViewListSanPham.setLayoutManager(layoutManager);
                            recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamNhapKho.this, "Lỗi không thể lấy sản phẩm theo danh mục", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
    public void getQuyCachTungSanPham(String urlService,String IDDanhMuc){
        RequestQueue requestQueue;
        urlService=urlService+IDDanhMuc;
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
                                    if(jsonObject.getString("QuyCach").isEmpty()||jsonObject.getString("QuyCach").length()==0){
                                        mListQuyCach.add(new Model_QuyCachVaTinhChat("Trống"));
                                        mListQuyCach2.add(new Model_QuyCachVaTinhChat("Trống"));
                                    }else{
                                        mListQuyCach.add(new Model_QuyCachVaTinhChat(jsonObject.getString("QuyCach")));
                                        mListQuyCach2.add(new Model_QuyCachVaTinhChat(jsonObject.getString("QuyCach")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(mListQuyCach.size()==5){
                                    mListQuyCach.add(new Model_QuyCachVaTinhChat("CODESEONE","Khác "));
                                }
                            }
                            gridViewListQuyCach.setVisibility(View.VISIBLE);
                            shimmer_view_quycach.setVisibility(View.GONE);
                            adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutquycachvatinhchat,mListQuyCach);
                            gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamNhapKho.this, "Lỗi get quy cách sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end


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
                                    if(i==0){
                                        IDDanhMuc=jsonObject.getString("IDDanhMuc");
                                    }
                                    mListDanhMuc.add(new Model_ListSanPhamBanLe(jsonObject.getString("IDDanhMuc"),jsonObject.getString("TenDanhMuc")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            gridViewListTinhChat.setVisibility(View.VISIBLE);
                            shimmer_view_danhmuc.setVisibility(View.GONE);
                            adapter_listSanPhamBanLe=new Adapter_ListSanPhamBanLe(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutsanphambanle2,mListDanhMuc);
                            gridViewListTinhChat.setAdapter(adapter_listSanPhamBanLe);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamNhapKho.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
}
