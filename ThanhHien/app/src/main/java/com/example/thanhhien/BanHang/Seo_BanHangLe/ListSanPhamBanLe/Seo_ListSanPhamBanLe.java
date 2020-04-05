package com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.example.thanhhien.BanHang.Seo_BanHangLe.Model_GoiYKhachHang;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Seo_BanHangLe;
import com.example.thanhhien.BanHang.Seo_SanPhamDaChon.Seo_BanHang;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Adapter_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Adapter_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Model_ListSanPhamBanLe;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NukeSSLCerts;
import com.example.thanhhien.R;
import com.example.thanhhien.Seo_GiaoDienChinh;
import com.facebook.shimmer.ShimmerFrameLayout;
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
/*
 * Người viết: Nguyễn Hữu Hai,Nguyễn Văn Thành
 * Date: 2019/12/20
 * Cái này seo viết đó nha
 *
 * */
public class Seo_ListSanPhamBanLe extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView gridViewListQuyCach;
    private GridView gridViewListTinhChat;
    public static TextView txtTenQuyCach;
    public static Button btnGioHang,btnTrove,btnThanhToan;
    private RecyclerView recyclerViewListSanPham;
    private Adapter_QuyCachVaTinhChat adapter_quyCachVaTinhChat;
    private  Adapter_QuyCachVaTinhChat adapter_quyCachVaTinhChat2;
    private Adapter_ListSanPhamBanLe adapter_listSanPhamBanLe;
    private ArrayList<Model_ListSanPhamBanLe> mListSanPhamBanLe;
    private Adapter_ListSanPhamBan adapter_listSanPhamBan;
    private ArrayList<Model_QuyCachVaTinhChat>mListQuyCach;
    private ArrayList<Model_QuyCachVaTinhChat>mListQuyCachXemThem;
    private ArrayList<Model_ListSanPhamBan> mListSanPhamBan;
    private  int SoTienNo=0;
    private SweetAlertDialog pDialog;
    private String QuyCachGoc="";
    private String SaveDanhMuchSanPham;
    private Adapter_GoiYKhachHang adapter_goiYKhachHang;
    private String MaKhachHang;
    long TongTienTra=0;
    int dem=0;
    private ShimmerFrameLayout container,shimmer_view_danhmuc,shimmer_view_quycach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_listsanphambanle);
        final Intent intent=getIntent();
        new NukeSSLCerts().nuke();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        setTitle(intent.getStringExtra("TenSanPham"));
        SaveDanhMuchSanPham=intent.getStringExtra("MaDanhMucSanPham");
        AnhXa();
        mListQuyCach=new ArrayList<>();
        mListQuyCachXemThem=new ArrayList<>();
        mListQuyCach.add(new Model_QuyCachVaTinhChat("jjsjs"));
        //getQuyCachTungSanPham(Api_custom.listQuyCachDanhMucSP,SaveDanhMuchSanPham);
        adapter_quyCachVaTinhChat=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamBanLe.this,R.layout.item_layoutquycachvatinhchat,mListQuyCach);
        gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat);
        // sự kiện click vào quy cách lấy sản phẩm theo quy cách đó
        gridViewListQuyCach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_QuyCachVaTinhChat model_quyCachVaTinhChat=mListQuyCach.get(position);
                txtTenQuyCach.setText(model_quyCachVaTinhChat.getTenQuyCachVaTT());
                if(model_quyCachVaTinhChat.getTenQuyCachVaTT().equalsIgnoreCase("Khác")){
                    eventClickXemThemQuyCach();

                }else {
                    // sự kiện lấy sản phẩm theo quy cách và danh mục sản phẩm
                    recyclerViewListSanPham.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                    mListSanPhamBan.clear();
                    //GetTatCaSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoQuyCachDanhMucSP,SaveDanhMuchSanPham,model_quyCachVaTinhChat.getTenQuyCachVaTT());
                    adapter_listSanPhamBan.notifyDataSetChanged();
                }
            }
        });
        //list sản phẩm
        mListSanPhamBanLe=new ArrayList<>();
        mListSanPhamBanLe.add(new Model_ListSanPhamBanLe("gsgs"));
        //getAllDanhMucSanPham(Api_custom.listAllDanhMucSanPham);
        adapter_listSanPhamBanLe=new Adapter_ListSanPhamBanLe(Seo_ListSanPhamBanLe.this,R.layout.item_layoutsanphambanle2,mListSanPhamBanLe);
        gridViewListTinhChat.setAdapter(adapter_listSanPhamBanLe);
        // sự kiện click danh mục sản phẩm
        gridViewListTinhChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recyclerViewListSanPham.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                gridViewListQuyCach.setVisibility(View.GONE);
                shimmer_view_quycach.setVisibility(View.VISIBLE);
              //  Toast.makeText(Seo_ListSanPhamBanLe.this, ""+SaveDanhMuchSanPham, Toast.LENGTH_SHORT).show();
                txtTenQuyCach.setText("Tất cả");
                Model_ListSanPhamBanLe model_listSanPhamBanLe=mListSanPhamBanLe.get(position);
                // clear list danh sách
                mListSanPhamBan.clear();
                mListQuyCachXemThem.clear();
                mListQuyCach.clear();
                // lấy dữ liệu
                getQuyCachTungSanPham(Api_custom.listQuyCachDanhMucSP,model_listSanPhamBanLe.getIDSanPham());
                //GetTatCaSanPhamTheoDanhMuc(Api_custom.GetTatCaSanPhamTheoDanhMuc,model_listSanPhamBanLe.getIDSanPham(),"Seo nè");
                // loading thay đổi dữ liệu
                adapter_quyCachVaTinhChat.notifyDataSetChanged();
                adapter_listSanPhamBan.notifyDataSetChanged();
            }
        });

        // list sản lấy sản phẩm theo danh mục sản phẩm
        mListSanPhamBan=new ArrayList<>();
        mListSanPhamBan.add(new Model_ListSanPhamBan("jsjs","jsasad","6000","6000"));
       // GetTatCaSanPhamTheoDanhMuc(Api_custom.GetTatCaSanPhamTheoDanhMuc,SaveDanhMuchSanPham,"Seo nè");
        adapter_listSanPhamBan=new Adapter_ListSanPhamBan(Seo_ListSanPhamBanLe.this,mListSanPhamBan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ListSanPhamBanLe.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);
        // sự kiện giỏ hàng
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Seo_BanHangLe.mListSanPhamDaChon.size()==0){
                    new SweetAlertDialog(Seo_ListSanPhamBanLe.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Lỗi...")
                            .setContentText("Chưa có sản phẩm trong giỏ!")
                            .show();
                }else {
                    Intent intent1=new Intent(Seo_ListSanPhamBanLe.this, Seo_BanHang.class);
                    startActivity(intent1);
                }

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
                if(Seo_BanHangLe.mListSanPhamDaChon.size()==0){
                    new SweetAlertDialog(Seo_ListSanPhamBanLe.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Lỗi...")
                            .setContentText("Chưa có sản phẩm trong giỏ!")
                            .show();
                }else {
                    eventClickThanhToan();
                }

            }
        });
    }
    private void AnhXa() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridViewListQuyCach=findViewById(R.id.gridViewListQuyCach);
        gridViewListTinhChat=findViewById(R.id.gridViewListTinhChat);
        recyclerViewListSanPham=findViewById(R.id.recyclerViewListSanPham);
        container= (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimmer_view_danhmuc=findViewById(R.id.shimmer_view_danhmuc);
        shimmer_view_quycach=findViewById(R.id.shimmer_view_quycach);
        txtTenQuyCach=findViewById(R.id.txtTenQuyCach);
        btnGioHang=findViewById(R.id.btnGioHang);
        btnTrove=findViewById(R.id.btnTrove);
        btnThanhToan=findViewById(R.id.btnThanhToan);
        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(Seo_BanHangLe.TongTien+""));
        btnThanhToan.setText("Thanh toán: "+TongTienChuyenDoi+ " VNĐ");

        recyclerViewListSanPham.setVisibility(View.VISIBLE);
        gridViewListQuyCach.setVisibility(View.VISIBLE);
        shimmer_view_quycach.setVisibility(View.GONE);
        shimmer_view_danhmuc.setVisibility(View.GONE);
        container.setVisibility(View.GONE);
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
    protected void onResume() {
        super.onResume();

    }

    private void eventClickXemThemQuyCach() {
        View view = getLayoutInflater().inflate(R.layout.item_bottomdialogquycachkhac, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        GridView gridViewListQuyCach=view.findViewById(R.id.gridViewListQuyCach);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ListSanPhamBanLe.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
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
        adapter_quyCachVaTinhChat2=new Adapter_QuyCachVaTinhChat(Seo_ListSanPhamBanLe.this,R.layout.item_layoutlistsanphambanle,mListQuyCachXemThem);
        gridViewListQuyCach.setAdapter(adapter_quyCachVaTinhChat2);
        gridViewListQuyCach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_QuyCachVaTinhChat model_quyCachVaTinhChat=mListQuyCachXemThem.get(position);
                txtTenQuyCach.setText(model_quyCachVaTinhChat.getTenQuyCachVaTT());
                recyclerViewListSanPham.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                mListSanPhamBan.clear();
                GetTatCaSanPhamTheoDanhMuc(Api_custom.listSanPhamTheoQuyCachDanhMucSP,SaveDanhMuchSanPham,model_quyCachVaTinhChat.getTenQuyCachVaTT());
                adapter_listSanPhamBan.notifyDataSetChanged();
                mBottomSheetDialog.dismiss();
            }
        });

    }

    private void eventClickThanhToan() {
        final LayoutInflater inflater=(LayoutInflater) Seo_ListSanPhamBanLe.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layuotthanhtoanhoadon, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnNhapHang=view.findViewById(R.id.btnNhapHang);
        Button btnNhapHangVaIn=view.findViewById(R.id.btnNhapHangVaIn);
        final TextView txtTienThua=view.findViewById(R.id.txtTienThua);
        final TextView editDaTra=view.findViewById(R.id.editDaTra);
        TextView txtTongTien=view.findViewById(R.id.txtTongTien);
        final TextView txtNoTon=view.findViewById(R.id.txtNoTon);
        final EditText edit_TenKhachHnag=view.findViewById(R.id.edit_TenKhachHnag);
        final RadioButton rad_DatHang=view.findViewById(R.id.rad_DatHang);
        final RadioButton rad_HoanThanh=view.findViewById(R.id.rad_HoanThanh);
        final AutoCompleteTextView autoCompleteTextView=view.findViewById(R.id.autoCompleteTextView);
        btnNhapHang.setText("Thanh toán");
        btnNhapHangVaIn.setText("Thanh toán và In");
        adapter_goiYKhachHang = new Adapter_GoiYKhachHang(this, R.layout.item_goiykhachhang, Seo_BanHangLe.mListKhachHang);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_goiYKhachHang);
        // set du liệu
        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(Seo_BanHangLe.TongTien+""));
        txtTongTien.setText(TongTienChuyenDoi+" VNĐ");

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_GoiYKhachHang model_goiYKhachHang=Seo_BanHangLe.mListKhachHang.get(position);
                edit_TenKhachHnag.setText(model_goiYKhachHang.getTenKhachHang());
                String TongTienChuyenDoi2= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(model_goiYKhachHang.getNoTon()+""));
                txtNoTon.setText(TongTienChuyenDoi2+" VNĐ");
                MaKhachHang=model_goiYKhachHang.getMaKhachHang();
            }
        });
        editDaTra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editDaTra.getText().toString().trim().length()<=0){
                    editDaTra.setText("0 VNĐ");
                }
                else {
                    long TongTienTra=Long.parseLong(editDaTra.getText().toString())-Seo_BanHangLe.TongTien;
                    String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(TongTienTra+""));
                    txtTienThua.setText(TongTienChuyenDoi+" VNĐ");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Dialog mBottomSheetDialog = new Dialog(Seo_ListSanPhamBanLe.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
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
                final String MaHoaDon = dateFormatter.format(today);
                DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormatter2.setLenient(false);
                Date today2= new Date();
                final String NgayTao = dateFormatter2.format(today2);

                // sét điều kiện
                if(autoCompleteTextView.getText().toString().trim().length()==0){// sét điều kiện nếu số điện thoại trống
                    new SweetAlertDialog(Seo_ListSanPhamBanLe.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Lỗi...")
                            .setContentText("Vui lòng nhập số điện thoại")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }else {
                    if(edit_TenKhachHnag.getText().toString().trim().length()==0){// sét điều kiện nếu tên khách hàng trống
                        new SweetAlertDialog(Seo_ListSanPhamBanLe.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Lỗi...")
                                .setContentText("Vui lòng nhập tên khách hàng")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }else {
                        if(rad_DatHang.isChecked()==false && rad_HoanThanh.isChecked()==false){
                            new SweetAlertDialog(Seo_ListSanPhamBanLe.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Lỗi...")
                                    .setContentText("Vui lòng chọn tình trạng đơn")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }else {
                            new SweetAlertDialog(Seo_ListSanPhamBanLe.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Xác nhận")
                                    .setContentText("Xác nhận thanh toán")
                                    .setConfirmText("Xác nhận")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            mBottomSheetDialog.dismiss();
                                            String TinhTrangDon = "";
                                            if(rad_DatHang.isChecked()==true){
                                                TinhTrangDon="0";
                                            }else if(rad_HoanThanh.isChecked()==true) {
                                                TinhTrangDon="1";
                                            }
                                            ThemHoaDon(Api_custom.ThemHoaDon,MaHoaDon,MaKhachHang,editDaTra.getText().toString(),NgayTao,TinhTrangDon);

                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }
                    }
                }//end hàm điều kiện
            }
        });


    }
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
                                        mListQuyCachXemThem.add(new Model_QuyCachVaTinhChat("Trống"));

                                    }else{

                                        mListQuyCach.add(new Model_QuyCachVaTinhChat(jsonObject.getString("QuyCach")));
                                        mListQuyCachXemThem.add(new Model_QuyCachVaTinhChat(jsonObject.getString("QuyCach")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(mListQuyCach.size()==5){
                                    mListQuyCach.add(new Model_QuyCachVaTinhChat("CODESEONE","Khác"));
                                }
                            }
                        }
                        gridViewListQuyCach.setVisibility(View.VISIBLE);
                        shimmer_view_quycach.setVisibility(View.GONE);
                        adapter_quyCachVaTinhChat.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamBanLe.this, "error getQuyCachTungSanPham", Toast.LENGTH_SHORT).show();
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
                                    mListSanPhamBanLe.add(new Model_ListSanPhamBanLe(jsonObject.getString("IDDanhMuc"),jsonObject.getString("TenDanhMuc")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter_listSanPhamBanLe.notifyDataSetChanged();

                        }
                        gridViewListTinhChat.setVisibility(View.VISIBLE);
                        shimmer_view_danhmuc.setVisibility(View.GONE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamBanLe.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end

    //<===============================Lấy tất cả sản phẩm theo danh mục sản phẩm======================>
    public void GetTatCaSanPhamTheoDanhMuc(String urlService, String MaDanhMuc,String QuyCach){
        final RequestQueue requestQueue;
        if(urlService.equalsIgnoreCase(Api_custom.GetTatCaSanPhamTheoDanhMuc)){
            urlService=urlService+"/"+MaDanhMuc;
        }else if(urlService.equalsIgnoreCase(Api_custom.listSanPhamTheoQuyCachDanhMucSP)){
            urlService=urlService+MaDanhMuc+"/"+QuyCach;

        }
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
                                    String TenSanPham=jsonObject.getString("TenSP")+" - "+jsonObject.getString("QuyCach");
                                    //Model_ListSanPhamBan(String maSanPham, String tenSanPham, String soLuong, String giaSanPham, String thuocTinh, String nhaCungCap,  String donViTinh,String quyCach)
                                    mListSanPhamBan.add(new Model_ListSanPhamBan(jsonObject.getString("IDSanPham"),TenSanPham,jsonObject.getString("SoLuong"),jsonObject.getString("DonGia1"),ThuocTinh,jsonObject.getString("TenNhaCungCap"),jsonObject.getString("DonViTinh"),QuyCachGoc));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            adapter_listSanPhamBan.notifyDataSetChanged();
                        }
                        container.setVisibility(View.GONE);
                        recyclerViewListSanPham.setVisibility(View.VISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamBanLe.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end

    private  void ThemHoaDon(String urlThemHoaDon, final String MaHoaDon,final String MaKhacHang,final String SoTienKhachTra,final String NgayThanhToan,final String TinhTrangDon){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.getCache().clear();
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                urlThemHoaDon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("ok")){
                            for(int i=0;i<Seo_BanHangLe.mListSanPhamDaChon.size();i++){
                                String TongThanhTien=Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoTienNhoLe())+(Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getDonGia())*Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuong()))+"";
                                ThemCTHoaDon(Api_custom.ThemCTHoaDon,MaHoaDon,Seo_BanHangLe.mListSanPhamDaChon.get(i).getMaSanPham(),
                                        Seo_BanHangLe.mListSanPhamDaChon.get(i).getDonGia(),Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuong(),
                                        Seo_BanHangLe.mListSanPhamDaChon.get(i).getDonViTinh(),Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuongNhoLe(),Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoTienNhoLe(),TongThanhTien);
                                dem++;

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ListSanPhamBanLe.this, "Lỗi thêm  hóa đơn", Toast.LENGTH_SHORT).show();
                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDHoaDon","HD-"+MaHoaDon+"A");
                params.put("IDKhachHang",MaKhacHang);
                params.put("TongThanhToan",Seo_BanHangLe.TongTien+"");
                params.put("SoTienTraTruoc",SoTienKhachTra);
                params.put("TongTienConLai",TongTienTra+"");
                params.put("NgayThanhToan",NgayThanhToan);
                params.put("ThongTinHoaDon","Hiện tại chưa có thông tin nha");
                params.put("TrangThaiHoaDon",TinhTrangDon);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//end hàm thêm hóa đơn
    private  void ThemCTHoaDon(String urlThemCTHoaDon, final String MaHoaDon,final String MaSanPham, final String DonGiaBan,final String SoLuongSPBan,final String DonViTinh, final String SoLuongLe, final String TongTienBanLe,final String TongThanhTien ){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.getCache().clear();
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                urlThemCTHoaDon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("ok")){
                            CapNhatSoLuongSanPham(Api_custom.CapNhatSoLuongSanPham,MaSanPham,SoLuongSPBan);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getApplicationContext(), "Lỗi thêm chi tiết hóa đơn !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDHoaDon","HD-"+MaHoaDon+"A");
                params.put("IDSP",MaSanPham);
                params.put("DonGiaBan",DonGiaBan);
                params.put("SoLuongSPBan",SoLuongSPBan);
                params.put("DoViTinh",DonViTinh);
                params.put("SoLuongLe",SoLuongLe);
                params.put("TongTienBanLe",TongTienBanLe);
                params.put("ThanhTien",TongThanhTien);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//end hàm  thêm ct hóa đơn

    private  void CapNhatSoLuongSanPham(String urlThemCTHoaDon, final String MaSanPham,final String SoLuong){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.getCache().clear();
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                urlThemCTHoaDon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("ok")){
                            if(dem==Seo_BanHangLe.mListSanPhamDaChon.size()){
                                new SweetAlertDialog(Seo_ListSanPhamBanLe.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thành công")
                                        .setContentText("Bạn có muốn bán tiếp")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                Seo_BanHangLe.TongTien=0;
                                                Seo_BanHangLe.mListSanPhamDaChon.clear();
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                Seo_BanHangLe.TongTien=0;
                                                Seo_BanHangLe.mListSanPhamDaChon.clear();
                                                Intent intent=new Intent(Seo_ListSanPhamBanLe.this, Seo_GiaoDienChinh.class);
                                                startActivity(intent);
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TastyToast.makeText(getApplicationContext(), "Lỗi cập nhật số lượng sản phẩm !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDSP",MaSanPham);
                params.put("SoLuong",SoLuong);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//end hàm thêm hóa đơn
}
