package com.example.thanhhien.NhapXuatHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.R;

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
    private LinearLayout llChuaBTN;
    private Button btnNhapHang,btnNhapHangLe,btnNhapHangVaIn;
    private EditText edit_TenNhaCungCap,editThanhToan,editNgayNhap,edit_ThongTinNCCLe;
    private TextView txtTongTien,txtTienConLai;
    private RecyclerView recyclerViewListSanPham;
    ArrayList<Model_ListSanPhamBan> sanphamArrayList;
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
        if(Seo_ChonNhaCungCap.gioHang.size()==0){
            llChuaBTN.setVisibility(View.GONE);
        }else{
            llChuaBTN.setVisibility(View.VISIBLE);
        }
        if(Seo_ChonNhaCungCap.IDNhaCungCap.equalsIgnoreCase("0")){
            edit_TenNhaCungCap.setVisibility(View.GONE);
            btnNhapHang.setVisibility(View.GONE);
        }else{
            edit_ThongTinNCCLe.setVisibility(View.GONE);
            btnNhapHangLe.setVisibility(View.GONE);
            edit_TenNhaCungCap.setText(Seo_ChonNhaCungCap.TenNCC);
        }
        editThanhToan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(editThanhToan.getText().toString().trim().length()==0){
                    editThanhToan.setText("0");
                    txtTienConLai.setText(Seo_ListSanPhamNhapKho.tongTien+" VNĐ");
                }else {
                    txtTienConLai.setText((Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+" VNĐ");
                }

            }
        });
        btnNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
                dateFormatter.setLenient(false);
                Date today = new Date();
                String MaPN = dateFormatter.format(today);
                ThemPhieuNhap(Api_custom.ThemPhieuNhap,MaPN,"",Seo_ChonNhaCungCap.IDNhaCungCap,Seo_ListSanPhamNhapKho.tongTien+"",editThanhToan.getText().toString().trim(),(Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+"","",1);
            }
        });
        btnNhapHangLe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_ThongTinNCCLe.getText().toString().trim().isEmpty()){
                    Toast.makeText(Seo_ThanhToanNhapHang.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else{
                    DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String MaPN = dateFormatter.format(today);
                    ThemPhieuNhap(Api_custom.ThemPhieuNhap,MaPN,edit_ThongTinNCCLe.getText().toString().trim(),"0",Seo_ListSanPhamNhapKho.tongTien+"",editThanhToan.getText().toString().trim(),(Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+"","",1);
                }
            }
        });
        btnNhapHangVaIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnNhapHang.getVisibility() == View.GONE) {
                    if(edit_ThongTinNCCLe.getText().toString().trim().isEmpty()){
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else{
                        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
                        dateFormatter.setLenient(false);
                        Date today = new Date();
                        String MaPN = dateFormatter.format(today);
                        ThemPhieuNhap(Api_custom.ThemPhieuNhap,MaPN,edit_ThongTinNCCLe.getText().toString().trim(),"0",Seo_ListSanPhamNhapKho.tongTien+"",editThanhToan.getText().toString().trim(),(Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+"","",2);
                    }
                } else if(btnNhapHangLe.getVisibility() == View.GONE){
                    DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String MaPN = dateFormatter.format(today);
                    ThemPhieuNhap(Api_custom.ThemPhieuNhap,MaPN,"",Seo_ChonNhaCungCap.IDNhaCungCap,Seo_ListSanPhamNhapKho.tongTien+"",editThanhToan.getText().toString().trim(),(Seo_ListSanPhamNhapKho.tongTien-Double.parseDouble(editThanhToan.getText().toString().trim()))+"","",2);
                }
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

        Adapter_ThanhToanNhapHang_GioHang adapterSPGioHang=new Adapter_ThanhToanNhapHang_GioHang(Seo_ThanhToanNhapHang.this,Seo_ChonNhaCungCap.gioHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_ThanhToanNhapHang.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        recyclerViewListSanPham.setAdapter(adapterSPGioHang);
    }

    private void AnhXa() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        llChuaBTN=findViewById(R.id.llChuaBTN);
        btnNhapHang=findViewById(R.id.btnNhapHang);
        btnNhapHangLe=findViewById(R.id.btnNhapHangLe);
        btnNhapHangVaIn=findViewById(R.id.btnNhapHangVaIn);
        edit_TenNhaCungCap=findViewById(R.id.edit_TenNhaCungCap);
        editThanhToan=findViewById(R.id.editThanhToan);
        txtTongTien=findViewById(R.id.txtTongTien);
        txtTienConLai=findViewById(R.id.txtTienConLai);
        editNgayNhap=findViewById(R.id.editNgayNhap);
        edit_ThongTinNCCLe=findViewById(R.id.edit_ThongTinNCCLe);
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
    private  void ThemPhieuNhap(String url, final String IDPhieuNhap,final String TenPhieuNhap, final String IDNhacCungCap, final String TongPhieuNhap, final String SoTienTraTruoc, final String TongTienConLai, final String GhiChuPhieuNhap, final int TrangThaiPhieuNhap){
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
                        if (btnNhapHang.getVisibility() == View.GONE) {
                            for (int i=0;i<Seo_ChonNhaCungCap.gioHang.size();i++){
                                ThemChiTietPN(Api_custom.ThemChiTietPhieuNhap,IDPhieuNhap,Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getTenSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong(),Seo_ChonNhaCungCap.gioHang.get(i).getGiaSanPham(),(Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(i).getGiaSanPham())*Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong()))+"");
                                if(i==(Seo_ChonNhaCungCap.gioHang.size()-1)){
                                    new SweetAlertDialog(Seo_ThanhToanNhapHang.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Thành công")
                                            .setContentText("Nhập hàng thành công")
                                            .show();
                                    Seo_ChonNhaCungCap.gioHang.clear();
                                    onBackPressed();
                                }
                            }
                        } else if(btnNhapHangLe.getVisibility() == View.GONE){
                            for (int i=0;i<Seo_ChonNhaCungCap.gioHang.size();i++){
                                ThemChiTietPN(Api_custom.ThemChiTietPhieuNhap,IDPhieuNhap,Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getTenSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong(),Seo_ChonNhaCungCap.gioHang.get(i).getGiaSanPham(),(Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(i).getGiaSanPham())*Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong()))+"");
                                getSoLuongSanPhamTheoIDSanPham(Api_custom.listSanPhamTheoIDSanPham,Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong());
                                if(i==(Seo_ChonNhaCungCap.gioHang.size()-1)){
                                    new SweetAlertDialog(Seo_ThanhToanNhapHang.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Thành công")
                                            .setContentText("Nhập hàng thành công")
                                            .show();
                                    Seo_ChonNhaCungCap.gioHang.clear();
                                    onBackPressed();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "error", Toast.LENGTH_SHORT).show();
                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDPhieuNhap","PN-"+IDPhieuNhap+"-A");
                params.put("TenPhieuNhap",TenPhieuNhap);
                params.put("IDNhacCungCap",IDNhacCungCap);
                params.put("TongPhieuNhap",TongPhieuNhap);
                params.put("SoTienTraTruoc",SoTienTraTruoc);
                params.put("TongTienConLai",TongTienConLai);
                params.put("GhiChuPhieuNhap",GhiChuPhieuNhap);
                params.put("TrangThaiPhieuNhap",TrangThaiPhieuNhap+"");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }// kết thúc hàm
    private  void ThemChiTietPN(String url, final String IDPhieuNhap, final String IDSanPham, final String TenSP, final String SoLuongSP, final String DonGiaNhapSP, final String TongTienCTPhieuNhap){
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

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "error Seo_ThanhToanNhapHang", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDPhieuNhap","PN-"+IDPhieuNhap+"A");
                params.put("IDSanPham",IDSanPham);
                params.put("TenSP",TenSP);
                params.put("SoLuongSP",SoLuongSP);
                params.put("DonGiaNhapSP",DonGiaNhapSP);
                params.put("TongTienCTPhieuNhap",TongTienCTPhieuNhap);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }//end
    private  void CapNhatSoLuongSanPhamKhiNhap(String url, final String IDSanPham,final String SoLuong){
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "error CapNhatSoLuongSanPhamKhiNhap", Toast.LENGTH_SHORT).show();
                    }

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDSanPham",IDSanPham);
                params.put("SoLuong",SoLuong);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void getSoLuongSanPhamTheoIDSanPham(String urlService,final String IDSanPham, final String SoLuongNhap){
        RequestQueue requestQueue;
        urlService=urlService+IDSanPham;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
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

                                    CapNhatSoLuongSanPhamKhiNhap(Api_custom.CapNhatSoLuongSanPhamKhiNhap,IDSanPham,""+(Double.parseDouble(jsonObject.getString("SoLuong"))+Double.parseDouble(SoLuongNhap)));
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
                        Toast.makeText(Seo_ThanhToanNhapHang.this, "error getSoLuongSanPhamTheoIDSanPham", Toast.LENGTH_SHORT).show();
                    }
                }
        );
// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end
}
