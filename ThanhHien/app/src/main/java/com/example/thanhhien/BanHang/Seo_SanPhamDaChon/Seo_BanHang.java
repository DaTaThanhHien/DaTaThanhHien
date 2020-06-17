package com.example.thanhhien.BanHang.Seo_SanPhamDaChon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Adapter_GoiYKhachHang;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Model_GoiYKhachHang;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Seo_BanHangLe;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NhaCungcap.Seo_ThemSuaXoaNhaCungCap;
import com.example.thanhhien.R;
import com.example.thanhhien.Seo_GiaoDienChinh;
import com.example.thanhhien.Seo_GiaoDienLogin;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
/*
 * Người viết: Nguyễn Hữu Hai
 * Date: 2019/12/20
 * Cái này seo viết đó nha
 *
 * */
public class Seo_BanHang extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnTongTienThanhToan,btnTrove;
    private Adapter_SanPhamDaChon adapter_listSanPhamBan;
    private RecyclerView recyclerViewListSanPham;
    private ArrayList<Model_GoiYKhachHang> mList_goiYKhachHang;
    private Adapter_GoiYKhachHang adapter_goiYKhachHang;
    long TongTien=0;
    private String MaKhachHang;
    long TongTienTra=0;
    int dem=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_banhang);
        setTitle("Sản phẩm đã chọn");
        AnhXa();
        onClick();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        adapter_listSanPhamBan=new Adapter_SanPhamDaChon(Seo_BanHang.this, Seo_BanHangLe.mListSanPhamDaChon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Seo_BanHang.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSanPham.setLayoutManager(layoutManager);
        recyclerViewListSanPham.setAdapter(adapter_listSanPhamBan);

        for(int i=0;i<Seo_BanHangLe.mListSanPhamDaChon.size();i++){
           TongTien= TongTien+Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoTienNhoLe())+(Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getDonGia())*Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuong()));
        }
        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong( TongTien+""));
        btnTongTienThanhToan.setText("Thanh toán: "+TongTienChuyenDoi+ " VNĐ");
    }

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnTongTienThanhToan=findViewById(R.id.btnTongTienThanhToan);
        btnTrove=findViewById(R.id.btnTrove);
        recyclerViewListSanPham=findViewById(R.id.recyclerViewListSanPham);
    }
    private void onClick(){
        btnTongTienThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    eventClickBanHang();
            }
        });
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    // Hàm gửi dữ liệu về Activity1

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
               onBackPressed();
               TongTien=0;
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void eventClickBanHang() {
        View view = getLayoutInflater().inflate(R.layout.item_layuotthanhtoanhoadon, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnNhapHang=view.findViewById(R.id.btnNhapHang);
        final TextView txtTienThua=view.findViewById(R.id.txtTienThua);
        final TextView editDaTra=view.findViewById(R.id.editDaTra);
        TextView txtTongTien=view.findViewById(R.id.txtTongTien);
        final EditText edit_TenKhachHnag=view.findViewById(R.id.edit_TenKhachHnag);
        final RadioButton rad_DatHang=view.findViewById(R.id.rad_DatHang);
        final RadioButton rad_HoanThanh=view.findViewById(R.id.rad_HoanThanh);
        btnNhapHang.setText("Thanh toán");
        final AutoCompleteTextView autoCompleteTextView=view.findViewById(R.id.autoCompleteTextView);
        adapter_goiYKhachHang = new Adapter_GoiYKhachHang(this, R.layout.item_goiykhachhang, Seo_BanHangLe.mListKhachHang);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_goiYKhachHang);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_GoiYKhachHang model_goiYKhachHang=Seo_BanHangLe.mListKhachHang.get(position);
                edit_TenKhachHnag.setText(model_goiYKhachHang.getTenKhachHang());
                MaKhachHang=model_goiYKhachHang.getMaKhachHang();
            }
        });
        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(Seo_BanHangLe.TongTien+""));
        txtTongTien.setText(TongTienChuyenDoi+" VNĐ");
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
                    TongTienTra=Long.parseLong(editDaTra.getText().toString())-Seo_BanHangLe.TongTien;
                    String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(TongTienTra+""));
                    txtTienThua.setText(TongTienChuyenDoi+" VNĐ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Dialog mBottomSheetDialog = new Dialog(Seo_BanHang.this, R.style.MaterialDialogSheet);
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
                final String MaHoaDon = dateFormatter.format(today);
                DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormatter2.setLenient(false);
                Date today2= new Date();
                final String NgayTao = dateFormatter2.format(today2);
                if(autoCompleteTextView.getText().toString().trim().length()==0){// sét điều kiện nếu số điện thoại trống
                    new SweetAlertDialog(Seo_BanHang.this, SweetAlertDialog.ERROR_TYPE)
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
                        new SweetAlertDialog(Seo_BanHang.this, SweetAlertDialog.ERROR_TYPE)
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
                            new SweetAlertDialog(Seo_BanHang.this, SweetAlertDialog.ERROR_TYPE)
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
                            new SweetAlertDialog(Seo_BanHang.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Xác nhận")
                                    .setContentText("Xác nhận thanh toán")
                                    .setConfirmText("Xác nhận")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
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
                        TastyToast.makeText(getApplicationContext(), "Lỗi thêm hóa đơn !", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

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
                                new SweetAlertDialog(Seo_BanHang.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thành công")
                                        .setContentText("Bạn có muốn bán tiếp")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                onBackPressed();
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
                                                Intent intent=new Intent(Seo_BanHang.this,Seo_GiaoDienChinh.class);
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
