package com.example.thanhhien.NhapXuatHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.LichSuNhapXuat.Adapter_SanPhamPhieuNhap;
import com.example.thanhhien.LichSuNhapXuat.Seo_ChiTietNhapHang;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Adapter_ThuocTinhSanPham;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Model_ListThuocTinhSanPham;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Seo_ThemSanPhamMoi;
import com.example.thanhhien.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_ThanhToanNhapHang extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout llChuaBTN,llSDT,llDiaChi,llNhaCungCap;
    private Button btnNhapHang,btnNhapHangVaIn;
    private EditText edit_TenNhaCungCap,edit_DiaChi,edit_SDT,editThanhToan,editNgayNhap,edit_ThongTinNCCLe;
    private TextView txtTongTien,txtTienConLai,btnBottomChonNhaCungCapKhac;
    private RecyclerView recyclerViewListSanPham;
    ArrayList<Model_ListSanPhamBan> sanphamArrayList;
    private ArrayList<Model_ListThuocTinhSanPham> mListNhaCungCap;
    private DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo__thanhtoannhaphang);
        setTitle("Thanh toán");
        AnhXa();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        mListNhaCungCap=new ArrayList<>();
        getAllNhaCungCap(Api_custom.GetTaCaNhaCungCap);
//        if(Seo_GiaoDienDanhMuc.gioHang.size()==0){
//            llChuaBTN.setVisibility(View.GONE);
//        }else{
//            llChuaBTN.setVisibility(View.VISIBLE);
//        }
//        edit_TenNhaCungCap.setText(Seo_GiaoDienDanhMuc.IDNhaCungCap);
//        if(Seo_GiaoDienDanhMuc.IDNhaCungCap.equalsIgnoreCase("Khac")){
//            llDiaChi.setVisibility(View.VISIBLE);
//            llSDT.setVisibility(View.VISIBLE);
//            llNhaCungCap.setVisibility(View.GONE);
//        }else{
//            llDiaChi.setVisibility(View.GONE);
//            llSDT.setVisibility(View.GONE);
//            llNhaCungCap.setVisibility(View.VISIBLE);
//        }
        editThanhToan.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(editThanhToan.getText().toString().trim().length()==0){
                    editThanhToan.setText("0");
                    txtTienConLai.setText(Seo_ListSanPhamNhapKho.tongTien+" VNĐ");
                }else {
                    txtTienConLai.setText((Seo_ListSanPhamNhapKho.tongTien-Long.parseLong(editThanhToan.getText().toString().trim()))+" VNĐ");
                }
                return false;
            }
        });
        btnNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Seo_GiaoDienDanhMuc.IDNhaCungCap.equalsIgnoreCase("Khac")){
                    if(edit_DiaChi.getText().toString().trim().length()==0||edit_SDT.getText().toString().trim().length()==0){
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "Bạn chưa nhập thông tin bên bán", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
                        dateFormatter.setLenient(false);
                        Date today = new Date();
                        String MaPN = dateFormatter.format(today);
                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(Seo_GiaoDienDanhMuc.gioHang).getAsJsonArray();

                        ThemPhieuNhap(Api_custom.ThemPhieuNhap,MaPN,"",Seo_GiaoDienDanhMuc.IDNhaCungCap,Seo_ListSanPhamNhapKho.tongTien+"",editThanhToan.getText().toString().trim(),(Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+"","Số điện thoại: "+edit_SDT.getText().toString()+"\n"+"Địa chỉ:"+edit_DiaChi.getText().toString(),1,myCustomArray+"");
                    }
                }else{
                    DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String MaPN = dateFormatter.format(today);
                    Gson gson = new GsonBuilder().create();
                    JsonArray myCustomArray = gson.toJsonTree(Seo_GiaoDienDanhMuc.gioHang).getAsJsonArray();
                    Log.d("thanhtestcode", myCustomArray+"");

                    ThemPhieuNhap(Api_custom.ThemPhieuNhap,MaPN,"",Seo_GiaoDienDanhMuc.IDNhaCungCap,Seo_ListSanPhamNhapKho.tongTien+"",editThanhToan.getText().toString().trim(),(Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+"","",1,myCustomArray+"");
                }
            }
        });

        btnNhapHangVaIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final Calendar calendar=Calendar.getInstance();
        final int ngay=calendar.get(Calendar.DATE);
        final int thang=calendar.get(Calendar.MONTH);
        final int nam=calendar.get(Calendar.YEAR);
        editNgayNhap.setText(ngay+"-"+(thang+1)+"-"+nam);
        txtTongTien.setText(Seo_ListSanPhamNhapKho.tongTien+" VNĐ");
        txtTienConLai.setText((Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+" VNĐ");
        sanphamArrayList=new ArrayList<>();
        Adapter_ThanhToanNhapHang_GioHang adapterSPGioHang=new Adapter_ThanhToanNhapHang_GioHang(Seo_ThanhToanNhapHang.this,Seo_GiaoDienDanhMuc.gioHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ThanhToanNhapHang.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        recyclerViewListSanPham.setAdapter(adapterSPGioHang);
        btnBottomChonNhaCungCapKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBottomNcc();
            }
        });
    }
    private void AnhXa() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        llChuaBTN=findViewById(R.id.llChuaBTN);
        llSDT=findViewById(R.id.llSDT);
        llNhaCungCap=findViewById(R.id.llNhaCungCap);
        btnNhapHang=findViewById(R.id.btnNhapHang);
        btnNhapHangVaIn=findViewById(R.id.btnNhapHangVaIn);
        edit_TenNhaCungCap=findViewById(R.id.edit_TenNhaCungCap);
        edit_DiaChi=findViewById(R.id.edit_DiaChi);
        editThanhToan=findViewById(R.id.editThanhToan);
        txtTongTien=findViewById(R.id.txtTongTien);
        txtTienConLai=findViewById(R.id.txtTienConLai);
        editNgayNhap=findViewById(R.id.editNgayNhap);
        btnBottomChonNhaCungCapKhac=findViewById(R.id.btnBottomChonNhaCungCapKhac);
        recyclerViewListSanPham=findViewById(R.id.recyclerViewListSanPham);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void getAllNhaCungCap(String urlService){
        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        final Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlService,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null&&response.length()!=0){
                            mListNhaCungCap.clear();
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    mListNhaCungCap.add(new Model_ListThuocTinhSanPham(jsonObject.getString("TenNhaCungCap")));
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
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "error getAllNhaCungCap", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }//end getAllNhaCungCap
    private void onClickBottomNcc() {
        final LayoutInflater inflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_layoutthongtinnhacungcaple, null);
        // khái báo ánh xạ
        final TabLayout tabLayout = view.findViewById(R.id.tabs2);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        final LinearLayout layoutThongTinNCC= view.findViewById(R.id.layoutThongTinNCC);
        final LinearLayout DanhSachNCC= view.findViewById(R.id.DanhSachNCC);
        LinearLayout btnNgayThem=view.findViewById(R.id.btnNgayThem);
        ListView listViewNhaCungCap=view.findViewById(R.id.listViewThuocTinh);
        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThanhToanNhapHang.this,R.layout.item_layoutspiner,mListNhaCungCap);
        listViewNhaCungCap.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThanhToanNhapHang.this, R.style.MaterialDialogSheet);
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

                    layoutThongTinNCC.setVisibility(View.GONE);
                    DanhSachNCC.setVisibility(View.VISIBLE);
                }else {

                    layoutThongTinNCC.setVisibility(View.VISIBLE);
                    DanhSachNCC.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        btnNgayThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Seo_ThanhToanNhapHang.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }// kết thúc hàm
    private  void ThemPhieuNhap(String url, final String IDPhieuNhap,final String TenPhieuNhap, final String IDNhaCungCap, final String TongPhieuNhap, final String SoTienTraTruoc, final String TongTienConLai, final String GhiChuPhieuNhap, final int TrangThaiPhieuNhap, final String jsonObjectt){
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
                        if(response.equalsIgnoreCase(("err"))){
                            Toast.makeText(Seo_ThanhToanNhapHang.this, "Lỗi nhập hàng", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Seo_ThanhToanNhapHang.this, "Nhập hàng thành công", Toast.LENGTH_SHORT).show();
                            Seo_GiaoDienDanhMuc.gioHang.clear();
                            Seo_ListSanPhamNhapKho.tongTien=0;
                            Seo_GiaoDienDanhMuc.IDNhaCungCap="Null";
                            onBackPressed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "Lỗi nhập hàng 2", Toast.LENGTH_SHORT).show();
                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDPhieuNhap","PN-"+IDPhieuNhap+"-A");
                params.put("TenPhieuNhap",TenPhieuNhap);
                params.put("IDNhaCungCap",IDNhaCungCap);
                params.put("TongPhieuNhap",TongPhieuNhap);
                params.put("SoTienTraTruoc",SoTienTraTruoc);
                params.put("TongTienConLai",TongTienConLai);
                params.put("GhiChuPhieuNhap",GhiChuPhieuNhap);
                params.put("TrangThaiPhieuNhap",TrangThaiPhieuNhap+"");
                params.put("jsonObjectt",jsonObjectt+"");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }// kết thúc hàm

}
