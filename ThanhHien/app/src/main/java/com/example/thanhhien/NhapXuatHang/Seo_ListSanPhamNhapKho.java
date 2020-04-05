package com.example.thanhhien.NhapXuatHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;

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
    private ArrayList<Model_ListSanPhamBanLe> mListSanPhamBanLe;
    private ArrayList<Model_QuyCachVaTinhChat>mListTinhChat;
    private ArrayList<Model_ListSanPhamBan> mListSanPhamBan;
    private int SoTienNo=0;
    public static String tenSP="";
    public static double tongTien=0;
    private String IDDanhMuc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_listsanphamnhapkho);
        AnhXa();
        new NukeSSLCerts().nuke();
        TongTien();
        mListQuyCach=new ArrayList<>();
        mListTinhChat=new ArrayList<>();
        adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutquycachvatinhchat,mListQuyCach);
        gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);

        //
        gridViewListQuyCach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String maHoaCode="";
                String tenQuyCach=mListQuyCach.get(position).getTenQuyCachVaTT();
                String QuyCachGoc=mListQuyCach.get(position).getTenQuyCachVaTT();
                try {
                    if(tenQuyCach.trim().equalsIgnoreCase("Trống")){
                        maHoaCode  = URLEncoder.encode(" ", "UTF-8");
                    } else {
                        maHoaCode  = URLEncoder.encode(mListQuyCach.get(position).getTenQuyCachVaTT(), "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                mListSanPhamBan.clear();
                if(Seo_ChonNhaCungCap.IDNhaCungCap.equalsIgnoreCase("0")){
                    getSanPhamTheoQuyCach(Api_custom.listSanPhamTheoQuyCachDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap,IDDanhMuc,tenSP,maHoaCode.replace("+","%20"),QuyCachGoc);
                }else{
                    getSanPhamTheoQuyCach(Api_custom.listSanPhamTheoQuyCachTheoNhaCungCapDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap,IDDanhMuc,tenSP,maHoaCode.replace("+","%20"),QuyCachGoc);
                }

//                getSanPhamTheoQuyCach(Api_custom.listSanPhamTheoQuyCachTheoNhaCungCapDanhMucSPTenSP,Seo_ChonNhaCungCap.IDNhaCungCap,Seo_GiaoDienNhapXuatHang.IDDanhMuc,tenSP,maHoaCode.replace("+","%20"),QuyCachGoc);

//                Model_QuyCachVaTinhChat model_quyCachVaTinhChat=mListQuyCach.get(position);
//                txtTenQuyCach.setText(model_quyCachVaTinhChat.getTenQuyCachVaTT());
//                if(model_quyCachVaTinhChat.getTenQuyCachVaTT().equals("Khác")){
//
//                    eventClickBanHang();
//                }
            }
        });
        //jjjsjsjs
        mListSanPhamBanLe=new ArrayList<>();
        getAllDanhMucSanPham(Api_custom.listAllDanhMucSanPham);
        if(Seo_ChonNhaCungCap.IDNhaCungCap.equalsIgnoreCase("0")){
            getSanPhamTheoQuyCach(Api_custom.listSanPhamTheoDanhMucSPKoNCCBanDau,Seo_ChonNhaCungCap.IDNhaCungCap, Seo_GiaoDienDanhMuc.IDDanhMuc,"a","a","a");
        }else{
            getSanPhamTheoQuyCach(Api_custom.listSanPhamTheoNhaCungCapDanhMucSPBanDau,Seo_ChonNhaCungCap.IDNhaCungCap, Seo_GiaoDienDanhMuc.IDDanhMuc,"a","a","a");
        }
        if(Seo_ChonNhaCungCap.IDNhaCungCap.equalsIgnoreCase("0")){
            getQuyCachTungSanPham(Api_custom.listQuyCachDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap, Seo_GiaoDienDanhMuc.IDDanhMuc);
        }else{
            getQuyCachTungSanPham(Api_custom.listQuyCachTheoNhaCungCapDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap, Seo_GiaoDienDanhMuc.IDDanhMuc);
        }
//        getSanPhamTheoNhaCungCapDanhMucSPLoaiBoTrungTen(Api_custom.listSanPhamTheoNhaCungCapDanhMucSPLoaiBoTrungTen,Seo_ChonNhaCungCap.IDNhaCungCap,Seo_GiaoDienNhapXuatHang.IDDanhMuc);
        gridViewListTinhChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String maHoaCode="";
//                try {
//                    maHoaCode  = URLEncoder.encode(mListSanPhamBanLe.get(position).getTenSanPham(), "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                mListQuyCach.clear();
                adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutquycachvatinhchat,mListQuyCach);
                gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);
                mListSanPhamBan.clear();
                adapter_listSanPhamBan=new Adapter_ListSanPhamNhap(Seo_ListSanPhamNhapKho.this,mListSanPhamBan);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ListSanPhamNhapKho.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewListSanPham.setLayoutManager(layoutManager);
                recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);
                IDDanhMuc=mListSanPhamBanLe.get(position).getIDSanPham();

                mListSanPhamBan.clear();
                adapter_listSanPhamBan=new Adapter_ListSanPhamNhap(Seo_ListSanPhamNhapKho.this,mListSanPhamBan);
                recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);


                if(Seo_ChonNhaCungCap.IDNhaCungCap.equalsIgnoreCase("0")){
                    getQuyCachTungSanPham(Api_custom.listQuyCachDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap,mListSanPhamBanLe.get(position).getIDSanPham());
                    getSanPhamTheoQuyCach(Api_custom.listSanPhamTheoDanhMucSPKoNCCBanDau,Seo_ChonNhaCungCap.IDNhaCungCap,IDDanhMuc,"a","a","a");
                }else{
                    getQuyCachTungSanPham(Api_custom.listQuyCachTheoNhaCungCapDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap,mListSanPhamBanLe.get(position).getIDSanPham());
                    getSanPhamTheoQuyCach(Api_custom.listSanPhamTheoNhaCungCapDanhMucSPBanDau,Seo_ChonNhaCungCap.IDNhaCungCap,IDDanhMuc,"a","a","a");
                }
//                getQuyCachTungSanPham(Api_custom.listQuyCachSanPhamTheoNhaCungCapDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap,Seo_GiaoDienNhapXuatHang.IDDanhMuc,maHoaCode.replace("+","%20"));
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
//                for (int i=0;i<Seo_ChonNhaCungCap.gioHang.size();i++){
//                    Log.d("polkmjnh", ""+Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham());
//                }
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
        for (int i=0;i<Seo_ChonNhaCungCap.gioHang.size();i++){
            tongTien=tongTien+(Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(i).getGiaSanPham())*Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong()));
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

    @Override
    protected void onRestart() {
        super.onRestart();
        TongTien();
    }

    private void eventClickBanHang() {

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
        final ArrayList<Model_QuyCachVaTinhChat> mListQuyCach2=new ArrayList<>();
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 60*2.5"));
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 20*2.5"));
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 50*2.5"));
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 60*2.5"));
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 30*2.5"));
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 10*2.5"));
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 55*2.5"));
        mListQuyCach2.add(new Model_QuyCachVaTinhChat("55","U 60*2.5"));
        adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutlistsanphambanle,mListQuyCach2);
        gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);
//        gridViewListQuyCach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String maHoaCode="";
//                try {
//                    maHoaCode  = URLEncoder.encode(tenSP, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                mListSanPhamBan.clear();
////                getSanPhamTheoQuyCach(Api_custom.listQuyCachSanPhamTheoNhaCungCapDanhMucSP,Seo_ChonNhaCungCap.IDNhaCungCap,Seo_GiaoDienNhapXuatHang.IDDanhMuc,maHoaCode.replace("+","%20"),mListQuyCach.get(position).getTenQuyCachVaTT(),mListQuyCach.get(position).getTenQuyCachVaTT());
//                Model_QuyCachVaTinhChat model_quyCachVaTinhChat=mListQuyCach2.get(position);
//                txtTenQuyCach.setText(model_quyCachVaTinhChat.getTenQuyCachVaTT());
//                mBottomSheetDialog.dismiss();
//            }
//        });

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
                    String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(SoTienNo+""));
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
    public void getSanPhamTheoQuyCach(String urlService, String IDNhaCungCap, String IDDanhMuc, String TenSanPham, final String QuyCach, final String QuyCachGoc){
        final RequestQueue requestQueue;
        if(urlService.equalsIgnoreCase(Api_custom.listSanPhamTheoDanhMucSPKoNCCBanDau)){
            urlService=urlService+IDDanhMuc;

        }else if(urlService.equalsIgnoreCase(Api_custom.listSanPhamTheoNhaCungCapDanhMucSPBanDau)){
            urlService=urlService+IDNhaCungCap+"/"+IDDanhMuc;

        }
        else if(urlService.equalsIgnoreCase(Api_custom.listSanPhamTheoQuyCachDanhMucSP)){
            urlService=urlService+IDDanhMuc+"/"+QuyCach;

        }else if(urlService.equalsIgnoreCase(Api_custom.listSanPhamTheoQuyCachTheoNhaCungCapDanhMucSP)){
            urlService=urlService+IDNhaCungCap+"/"+IDDanhMuc+"/"+QuyCach;

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
                                    String ThuocTinh="",TrongLuong="",DoDay="",Dai="",ThuocTinhKhac="";

                                    //1
                                    if(!jsonObject.getString("TrongLuong").isEmpty()||jsonObject.getString("TrongLuong").trim().length()!=0){
                                        TrongLuong="Nặng: "+jsonObject.getString("TrongLuong")+", ";
                                    }
                                    //2
                                    if(!jsonObject.getString("DoDay").isEmpty()||jsonObject.getString("DoDay").trim().length()!=0){
                                        DoDay="Dày: "+jsonObject.getString("DoDay")+", ";;
                                    }
                                    //3
                                    if(!jsonObject.getString("Dai").isEmpty()||jsonObject.getString("Dai").trim().length()!=0){
                                        Dai="Dài: "+jsonObject.getString("Dai")+", ";;
                                    }
                                    //4
                                    if(!jsonObject.getString("ThuocTinhKhac1").isEmpty()||jsonObject.getString("ThuocTinhKhac1").trim().length()!=0){
                                        ThuocTinhKhac="Khác: "+jsonObject.getString("ThuocTinhKhac1")+", ";;
                                    }
                                    //5
                                    if(!jsonObject.getString("ThuocTinhKhac2").isEmpty()||jsonObject.getString("ThuocTinhKhac2").trim().length()!=0){
                                        if(ThuocTinhKhac.length()==0){
                                            ThuocTinhKhac="Khác: "+jsonObject.getString("ThuocTinhKhac1");
                                        }else{
                                            ThuocTinhKhac=ThuocTinhKhac+", "+jsonObject.getString("ThuocTinhKhac1");
                                        }
                                    }
                                    ThuocTinh=TrongLuong+DoDay+Dai+ThuocTinhKhac;
                                    String TenSanPham=jsonObject.getString("TenSP")+" "+jsonObject.getString("QuyCach");
                                    mListSanPhamBan.add(new Model_ListSanPhamBan(jsonObject.getString("IDSanPham"),TenSanPham,jsonObject.getString("SoLuong"),jsonObject.getString("DonGia1"),jsonObject.getString("DonViTinh"),ThuocTinh,QuyCachGoc));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
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
                        Toast.makeText(Seo_ListSanPhamNhapKho.this, "error getSanPhamTheoQuyCach", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
    public void getQuyCachTungSanPham(String urlService,String IDNhaCungCap,String IDDanhMuc){
        RequestQueue requestQueue;
        if(urlService.equalsIgnoreCase(Api_custom.listQuyCachTheoNhaCungCapDanhMucSP)){
            urlService=urlService+IDNhaCungCap+"/"+IDDanhMuc;
        }else if(urlService.equalsIgnoreCase(Api_custom.listQuyCachDanhMucSP)){
            urlService=urlService+IDDanhMuc;
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
                                    if(jsonObject.getString("QuyCach").isEmpty()||jsonObject.getString("QuyCach").length()==0){
                                        mListQuyCach.add(new Model_QuyCachVaTinhChat("Trống"));
                                    }else{
                                        mListQuyCach.add(new Model_QuyCachVaTinhChat(jsonObject.getString("QuyCach")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(mListQuyCach.size()>=6){
                                mListQuyCach.add(new Model_QuyCachVaTinhChat("CODESEONE","Khác "));
                            }
                            adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutquycachvatinhchat,mListQuyCach);
                            gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamNhapKho.this, "error getQuyCachTungSanPham", Toast.LENGTH_SHORT).show();
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
                                    mListSanPhamBanLe.add(new Model_ListSanPhamBanLe(jsonObject.getString("IDDanhMuc"),jsonObject.getString("TenDanhMuc")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter_listSanPhamBanLe=new Adapter_ListSanPhamBanLe(Seo_ListSanPhamNhapKho.this,R.layout.item_layoutsanphambanle2,mListSanPhamBanLe);
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
