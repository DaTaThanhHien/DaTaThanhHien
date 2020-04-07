package com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import com.android.volley.toolbox.Volley;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Adapter_QuyCachVaTinhChat;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_QuyCachVaTinhChat;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.HttpsTrustManager;
import com.example.thanhhien.NhaCungcap.Seo_ThemSuaXoaNhaCungCap;
import com.example.thanhhien.R;
import com.example.thanhhien.Seo_GiaoDienLogin;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Seo_ThemSanPhamMoi extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout btnChuyenDoiNhapQuyCach,layoutQuyCach1,layoutQuyCach2,btnThemNhaCungCap,btnThemDanhMucSanPham;
    private boolean isChecked=false;
    private ImageView hinhchuyendoi;
    private Button btnThemSanPham;
    private EditText edit_TenSanPham,edit_QK1,edit_QK2,
            edit_QK3,edit_TrongLuong,edit_DoDay,
            edit_DoDai,edit_ThuocTinhKhac,
            edit_GiaSi,edit_GiaLe;
    private TextView btnChonNhaCungCap,btnChonDanhMucSanPham,
            btnTrongLuong,btnDoDay,btnDoDai,btnDonViCuaGiaSi,
            btnDonViCuaGiaLe;

    private ArrayList<Model_ListThuocTinhSanPham> mListNhaCungCap,mListDanhMucSanPham,
            mListTrongLuong,mListDoDai,mListDoDay,
            mListDonViGiaSi,mListDonViGiaLe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.seo_themsanphammoi);
        setTitle("Thêm sản phẩm");
        AnhXa();
        new HttpsTrustManager();
        HttpsTrustManager.allowAllSSL();
        onClick();
        // tạo mảng
        mListNhaCungCap=new ArrayList<>();
        mListDanhMucSanPham=new ArrayList<>();
        mListTrongLuong=new ArrayList<>();
        mListDoDai=new ArrayList<>();
        mListDoDay=new ArrayList<>();
        mListDonViGiaLe=new ArrayList<>();
        mListDonViGiaSi=new ArrayList<>();
        getAllDonViTinh(Api_custom.GetTaCaDonViTinh);
        getAllNhaCungCap(Api_custom.GetTaCaNhaCungCap);
        getAllDanhMuc(Api_custom.GetTaCaDanhMuc);
    }
    // ánh xạ khai báo đối tượng
    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnChuyenDoiNhapQuyCach=findViewById(R.id.btnChuyenDoiNhapQuyCach);
        layoutQuyCach1=findViewById(R.id.layoutQuyCach1);
        layoutQuyCach2=findViewById(R.id.layoutQuyCach2);
        hinhchuyendoi=findViewById(R.id.hinhchuyendoi);
        btnChonNhaCungCap=findViewById(R.id.btnChonNhaCungCap);
        btnThemNhaCungCap=findViewById(R.id.btnThemNhaCungCap);
        btnThemDanhMucSanPham=findViewById(R.id.btnThemDanhMucSanPham);
        btnChonDanhMucSanPham=findViewById(R.id.btnChonDanhMucSanPham);
        btnTrongLuong=findViewById(R.id.btnTrongLuong);
        btnDoDay=findViewById(R.id.btnDoDay);
        btnDoDai=findViewById(R.id.btnDoDai);
        btnDonViCuaGiaSi=findViewById(R.id.btnDonViCuaGiaSi);
        btnDonViCuaGiaLe=findViewById(R.id.btnDonViCuaGiaLe);
        btnThemSanPham=findViewById(R.id.btnThemSanPham);
        edit_TenSanPham=findViewById(R.id.edit_TenSanPham);
        edit_QK1=findViewById(R.id.edit_QK1);
        edit_QK2=findViewById(R.id.edit_QK2);
        edit_QK3=findViewById(R.id.edit_QK3);
        edit_TrongLuong=findViewById(R.id.edit_TrongLuong);
        edit_DoDay=findViewById(R.id.edit_DoDay);
        edit_DoDai=findViewById(R.id.edit_DoDai);
        edit_ThuocTinhKhac=findViewById(R.id.edit_ThuocTinhKhac);
        edit_GiaSi=findViewById(R.id.edit_GiaSi);
        edit_GiaLe=findViewById(R.id.edit_GiaLe);



    }
    private void onClick(){
        // sự kiện click thêm nhà cung cấp
        btnThemNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomThemNhaCungCap();
            }
        });
        //sự kiện chọn nhà cung cấp
        btnChonNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNhaCungCap();
            }
        });// kết thúc hàm

        // sự kiện thêm danh mục sản phẩm
        btnThemDanhMucSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomThemDanhMucSanPham();
            }
        });// kết thúc


        // sự kiện chọn danh mục
        btnChonDanhMucSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDanhMucSanPham();
            }
        });// kết thúc hàm
        // sự kiện click trọng lượng
        btnTrongLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomTrongLuong();
            }
        });// kết thúc hàm
        // sự kiện click độ dài
        btnDoDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDoDai();
            }
        });// kết thúc hàm
        // sự kiện click độ dày
        btnDoDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDoDay();
            }
        });// kết thúc hàm
        // sự kiện click chuyển đổi nhập quy cách
        btnChuyenDoiNhapQuyCach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked==false){
                    layoutQuyCach1.setVisibility(View.GONE);
                    layoutQuyCach2.setVisibility(View.VISIBLE);
                    hinhchuyendoi.setImageResource(R.drawable.imgchuyendoiquycach2);
                    isChecked=true;
                }else {
                    layoutQuyCach1.setVisibility(View.VISIBLE);
                    layoutQuyCach2.setVisibility(View.GONE);
                    hinhchuyendoi.setImageResource(R.drawable.imgchuyendoiquycach);
                    isChecked=false;
                }
            }
        });// kết thúc hàm
        // sự kiện đơn vị giá sỉ
        btnDonViCuaGiaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDonViGiaSi();
            }
        });// kết thúc hàm
        // sự kiện đơn vị giá lẻ
        btnDonViCuaGiaLe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDonViGiaLe();
            }
        });// kết thúc hàm

        // sự kiện click thêm sản phẩm
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckedValidation()==false){
                    return;
                }else {
                    if(btnDonViCuaGiaSi.getText().toString().trim().length()==0){
                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Lỗi")
                                .setContentText("Vui lòng chọn đơn vị bán giá sỉ")
                                .show();
                    }
                    else if(btnDonViCuaGiaLe.getText().toString().trim().length()==0){
                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Lỗi")
                                .setContentText("Vui lòng chọn đơn vị bán giá lẻ")
                                .show();
                    }

                    else {
                        DateFormat dateFormatter = new SimpleDateFormat("MMddhhmmss");
                        dateFormatter.setLenient(false);
                        Date today = new Date();
                        String IDThuocTinh = dateFormatter.format(today);
                        String QuyCach="";
                        if(isChecked==false){
                            QuyCach = edit_QK1.getText().toString().trim();
                        }else {
                            QuyCach = edit_QK3.getText().toString().trim();
                        }
                        String TrongLuong = edit_TrongLuong.getText().toString().trim();
                        String DoDay = edit_DoDay.getText().toString().trim();
                        String Dai = edit_DoDai.getText().toString().trim();
                        String thuoctinhkhac = edit_ThuocTinhKhac.getText().toString().trim();
                        String IDSanPham = "SP-" + dateFormatter.format(today);
                        String tenncc = btnChonNhaCungCap.getText().toString().trim();
                        String tendanhmuc = btnChonDanhMucSanPham.getText().toString().trim();
                        String TenSP = edit_TenSanPham.getText().toString().trim();
                        String SoLuong = "0";
                        String DonViTinh = btnDoDay.getText().toString().trim() + ";" + btnDoDai.getText().toString().trim() + ";" + btnTrongLuong.getText().toString().trim() + ";" +btnDonViCuaGiaLe.getText().toString().trim()+";"+ btnDonViCuaGiaSi.getText().toString().trim();
                        String giale = edit_GiaLe.getText().toString().trim();
                        String giasi = edit_GiaSi.getText().toString().trim();
                        String trangthaisanpham = "1";
                        ThemSanPhamMoi(Api_custom.ThemSanPhamMoi, IDThuocTinh, QuyCach, TrongLuong, DoDay, Dai, thuoctinhkhac, IDSanPham, tenncc, tendanhmuc, TenSP, SoLuong, DonViTinh, giale, giasi, trangthaisanpham);
                    }
                }
            }
        });

    }
    // khởi tạo menu
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

    // hàm kiểm tra
    private boolean isCheckedValidation(){
        // lấy dữ liệu
        String TenSanPham=edit_TenSanPham.getText().toString().trim();
        String QuyCach1=edit_QK1.getText().toString().trim();
        String QuyCach2=edit_QK2.getText().toString().trim();
        String QuyCach3=edit_QK3.getText().toString().trim();
        String TrongLuong=edit_TrongLuong.getText().toString().trim();
        String DoDay=edit_DoDay.getText().toString().trim();
        String DoDai=edit_DoDai.getText().toString().trim();
        String ThuocTinhKhac=edit_ThuocTinhKhac.getText().toString().trim();
        String GiaSi=edit_GiaSi.getText().toString().trim();
        String GiaLe=edit_GiaLe.getText().toString().trim();
        // khai báo định dạng kiểm tra
        String kiemtraquycachkhac="^[a-zA-Z0-9]{1,60}$";
        String kitudacbiet="^[a-zA-Z ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]{1,60}+$";
        // kiểm tra chọn nhà cung cấp
        if(btnChonNhaCungCap.getText().equals("Vui lòng chọn nhà cung cấp")){
            new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Lỗi")
                    .setContentText("Vui lòng chọn nhà cung cấp!")
                    .show();
            return false;
        }else {
            // kiểm tra danh mục sản phẩm
            if(btnChonDanhMucSanPham.getText().equals("Vui lòng chọn danh mục sản phẩm")){
                new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Lỗi")
                        .setContentText("Vui lòng chọn danh mục sản phẩm!")
                        .show();
                return false;
            }else {
                // kiểm tra tên sản phẩm
                if(TenSanPham.length()==0){
                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Lỗi")
                            .setContentText("Vui lòng nhập tên sản phẩm!")
                            .show();
                    return false;
                }else {
                    // kiểm tra xem có kí tự đặc biệt hay không
                    if(!TenSanPham.matches(kitudacbiet)){
                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Lỗi")
                                .setContentText("Không được nhập kí tự đặc biệt!")
                                .show();
                        return false;
                    }else {
                        // xét quy cách
                        if(isChecked==false){
                            if(QuyCach1.length()==0 || QuyCach2.length()==0){
                                new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Lỗi")
                                        .setContentText("Vui lòng nhập quy cách!")
                                        .show();
                                return false;
                            }else {
                                // xét điều kiện trọng lượng
                                if(Integer.parseInt(QuyCach1)==0 || Integer.parseInt(QuyCach2)==0){
                                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Lỗi")
                                            .setContentText("Vui lòng nhập quy cách khác 0!")
                                            .show();
                                    return false;
                                }else {
                                    // set điều kiện trọng lượng
                                    if(TrongLuong.length()==0){
                                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Lỗi")
                                                .setContentText("Vui lòng nhập trọng lượng!")
                                                .show();
                                        return false;
                                    }else {
                                        if(Integer.parseInt(edit_TrongLuong.getText().toString())==0){
                                            new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Lỗi")
                                                    .setContentText("Nhập trọng lượng khác 0 !")
                                                    .show();
                                            return false;
                                        }else {
                                            if(DoDay.length()==0){
                                                new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Lỗi")
                                                        .setContentText("Vui lòng nhập độ dày!")
                                                        .show();
                                                return false;
                                            }else {
                                                if(Integer.parseInt(DoDay)==0){
                                                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                            .setTitleText("Lỗi")
                                                            .setContentText("Nhập độ dày khác 0 !")
                                                            .show();
                                                    return false;
                                                }else {
                                                    if(DoDai.length()==0){
                                                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                .setTitleText("Lỗi")
                                                                .setContentText("Vui lòng nhập độ dài!")
                                                                .show();
                                                        return false;
                                                    }else {
                                                        if(Integer.parseInt(DoDai)==0){
                                                            new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                    .setTitleText("Lỗi")
                                                                    .setContentText("Nhập độ dài khác 0 !")
                                                                    .show();
                                                            return false;
                                                        }else {

                                                            if(ThuocTinhKhac.length()>0){
                                                                if(!ThuocTinhKhac.matches(kiemtraquycachkhac)){
                                                                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                            .setTitleText("Lỗi")
                                                                            .setContentText("Thuộc tính không có kí tự đặc biệt!")
                                                                            .show();
                                                                    return false;
                                                                }

                                                            }else {
                                                                if(GiaSi.length()==0){
                                                                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                            .setTitleText("Lỗi")
                                                                            .setContentText("Vui lòng nhập giá sỉ!")
                                                                            .show();
                                                                    return false;
                                                                }else {
                                                                    if(GiaLe.length()==0){
                                                                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                                .setTitleText("Lỗi")
                                                                                .setContentText("Vui lòng nhập giá lẻ!")
                                                                                .show();
                                                                        return false;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else {
                            if(QuyCach3.length()==0){
                                new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Lỗi")
                                        .setContentText("Vui lòng nhập quy cách!")
                                        .show();
                                return false;
                            }else {
                                // set điều kiện trọng lượng
                                if(TrongLuong.length()==0){
                                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Lỗi")
                                            .setContentText("Vui lòng nhập trọng lượng!")
                                            .show();
                                    return false;
                                }else {
                                    if(Integer.parseInt(edit_TrongLuong.getText().toString())==0){
                                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Lỗi")
                                                .setContentText("Nhập trọng lượng khác 0 !")
                                                .show();
                                        return false;
                                    }else {
                                        if(DoDay.length()==0){
                                            new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Lỗi")
                                                    .setContentText("Vui lòng nhập độ dày!")
                                                    .show();
                                            return false;
                                        }else {
                                            if(Integer.parseInt(DoDay)==0){
                                                new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Lỗi")
                                                        .setContentText("Nhập độ dày khác 0 !")
                                                        .show();
                                                return false;
                                            }else {
                                                if(DoDai.length()==0){
                                                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                            .setTitleText("Lỗi")
                                                            .setContentText("Vui lòng nhập độ dài!")
                                                            .show();
                                                    return false;
                                                }else {
                                                    if(Integer.parseInt(DoDai)==0){
                                                        new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                .setTitleText("Lỗi")
                                                                .setContentText("Nhập độ dài khác 0 !")
                                                                .show();
                                                        return false;
                                                    }else {

                                                        if(ThuocTinhKhac.length()>0){
                                                            if(!ThuocTinhKhac.matches(kiemtraquycachkhac)){
                                                                new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                        .setTitleText("Lỗi")
                                                                        .setContentText("Thuộc tính không có kí tự đặc biệt!")
                                                                        .show();
                                                                return false;
                                                            }

                                                        }else {
                                                            if(GiaSi.length()==0){
                                                                new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                        .setTitleText("Lỗi")
                                                                        .setContentText("Vui lòng nhập giá sỉ!")
                                                                        .show();
                                                                return false;
                                                            }else {
                                                                if(GiaLe.length()==0){
                                                                    new SweetAlertDialog(Seo_ThemSanPhamMoi.this, SweetAlertDialog.ERROR_TYPE)
                                                                            .setTitleText("Lỗi")
                                                                            .setContentText("Vui lòng nhập giá lẻ!")
                                                                            .show();
                                                                    return false;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        return true;
    }

    //chọn nhà cung cấp
    private void bottomNhaCungCap(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn nhà cung cấp");
        // set dữ liệu
        ListView listViewNhaCungCap=view.findViewById(R.id.listViewThuocTinh);
        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThemSanPhamMoi.this,R.layout.item_layoutspiner,mListNhaCungCap);
        listViewNhaCungCap.setAdapter(adapter_thuocTinhSanPham);

        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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
        listViewNhaCungCap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_ListThuocTinhSanPham model_listThuocTinhSanPham=mListNhaCungCap.get(position);
                btnChonNhaCungCap.setText(model_listThuocTinhSanPham.getTenSanPham());
                btnChonNhaCungCap.setTextColor(Color.BLACK);
                mBottomSheetDialog.dismiss();
            }
        });


    }//kết thúc hàm

    // thêm nhà cung cấp
    private void bottomThemNhaCungCap(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomthemnhacungcap, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnThemNhaCungCap=view.findViewById(R.id.btnThemNhaCungCap);
        final EditText edit_TenNhaCungCap=view.findViewById(R.id.edit_TenNhaCungCap);
        final EditText edit_SoDienThoai=view.findViewById(R.id.edit_SoDienThoai);
        final EditText edit_DiaChi=view.findViewById(R.id.edit_DiaChi);
        final EditText edit_Email=view.findViewById(R.id.edit_Email);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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

        btnThemNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenNCC=edit_TenNhaCungCap.getText().toString().trim();
                String SDT=edit_SoDienThoai.getText().toString().trim();
                String DiaChi=edit_DiaChi.getText().toString().trim();
                String Email=edit_Email.getText().toString().trim();
                if(TenNCC.isEmpty()||SDT.isEmpty()||DiaChi.isEmpty()||Email.isEmpty()){
                    Toast.makeText(Seo_ThemSanPhamMoi.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String IDNhaCungCap = "NCC-"+dateFormatter.format(today)+"B";
                    ThemNhaCungCap(Api_custom.ThemNhaCungCap,IDNhaCungCap,TenNCC,SDT,DiaChi,Email);
                    mBottomSheetDialog.dismiss();
                }
            }
        });

    }//kết thúc hàm

    //chọn danh mục sản phẩm
    private void bottomDanhMucSanPham(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn danh mục sản phẩm");
        ListView listViewDanhMucSanPham=view.findViewById(R.id.listViewThuocTinh);
        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThemSanPhamMoi.this,R.layout.item_layoutspiner,mListDanhMucSanPham);
        listViewDanhMucSanPham.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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

        listViewDanhMucSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_ListThuocTinhSanPham model_listThuocTinhSanPham=mListDanhMucSanPham.get(position);
                btnChonDanhMucSanPham.setText(model_listThuocTinhSanPham.getTenSanPham());
                btnChonDanhMucSanPham.setTextColor(Color.BLACK);
                mBottomSheetDialog.dismiss();
            }
        });


    }//kết thúc hàm

    // thêm danh danh mục sản phẩm
    private void bottomThemDanhMucSanPham(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemdanhmuc, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        final EditText edit_TenDanhMuc=view.findViewById(R.id.edit_TenDanhMuc);
        Button btnThemDanhMuc=view.findViewById(R.id.btnThemDanhMuc);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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

        btnThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_TenDanhMuc.getText().toString().trim().isEmpty()){
                    Toast.makeText(Seo_ThemSanPhamMoi.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    String IDDanhMuc = "M-"+dateFormatter.format(today)+"B";
                    ThemDanhMuc(Api_custom.ThemDanhMuc,IDDanhMuc,edit_TenDanhMuc.getText().toString().trim());
                    mBottomSheetDialog.dismiss();
                }
            }
        });

    }//kết thúc hàm
    //chọn trọng lượng
    private void bottomTrongLuong(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn trọng lượng");
        ListView listViewThuocTinh=view.findViewById(R.id.listViewThuocTinh);
        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThemSanPhamMoi.this,R.layout.item_layoutspiner,mListTrongLuong);
        listViewThuocTinh.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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
        listViewThuocTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_ListThuocTinhSanPham model_listThuocTinhSanPham=mListTrongLuong.get(position);
                btnTrongLuong.setText(model_listThuocTinhSanPham.getTenSanPham());
                mBottomSheetDialog.dismiss();
            }
        });
        getAllDonViTinh(Api_custom.GetTaCaDonViTinh);

    }//kết thúc hàm
    //chọn độ dày
    private void bottomDoDay(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn độ dày");
        ListView listViewThuocTinh=view.findViewById(R.id.listViewThuocTinh);

        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThemSanPhamMoi.this,R.layout.item_layoutspiner,mListDoDay);
        listViewThuocTinh.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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
        listViewThuocTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_ListThuocTinhSanPham model_listThuocTinhSanPham=mListDoDay.get(position);
                btnDoDay.setText(model_listThuocTinhSanPham.getTenSanPham());
                mBottomSheetDialog.dismiss();
            }
        });
        getAllDonViTinh(Api_custom.GetTaCaDonViTinh);//

    }//kết thúc hàm
    //chọn độ dài
    private void bottomDoDai(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn độ dài");
        ListView listViewThuocTinh=view.findViewById(R.id.listViewThuocTinh);
        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThemSanPhamMoi.this,R.layout.item_layoutspiner,mListDoDai);
        listViewThuocTinh.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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
        listViewThuocTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_ListThuocTinhSanPham model_listThuocTinhSanPham=mListDoDai.get(position);
                btnDoDai.setText(model_listThuocTinhSanPham.getTenSanPham());
                mBottomSheetDialog.dismiss();
            }
        });
        getAllDonViTinh(Api_custom.GetTaCaDonViTinh);

    }//kết thúc hàm

    //chọn độ dài
    private void bottomDonViGiaSi(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn đơn vị giá sỉ");
        ListView listViewThuocTinh=view.findViewById(R.id.listViewThuocTinh);

        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThemSanPhamMoi.this,R.layout.item_layoutspiner,mListDonViGiaSi);
        listViewThuocTinh.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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
        listViewThuocTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_ListThuocTinhSanPham model_listThuocTinhSanPham=mListDonViGiaSi.get(position);
                btnDonViCuaGiaSi.setText(model_listThuocTinhSanPham.getTenSanPham());
                mBottomSheetDialog.dismiss();
            }
        });
        getAllDonViTinh(Api_custom.GetTaCaDonViTinh);

    }//kết thúc hàm

    private void bottomDonViGiaLe(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn đơn vị giá lẻ");
        ListView listViewThuocTinh=view.findViewById(R.id.listViewThuocTinh);
        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(Seo_ThemSanPhamMoi.this,R.layout.item_layoutspiner,mListDonViGiaLe);
        listViewThuocTinh.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(Seo_ThemSanPhamMoi.this, R.style.MaterialDialogSheet);
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
        listViewThuocTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_ListThuocTinhSanPham model_listThuocTinhSanPham=mListDonViGiaLe.get(position);
                btnDonViCuaGiaLe.setText(model_listThuocTinhSanPham.getTenSanPham());
                mBottomSheetDialog.dismiss();
            }
        });
        getAllDonViTinh(Api_custom.GetTaCaDonViTinh);

    }//kết thúc hàm
    private  void ThemNhaCungCap(String url, final String IDNhaCungCap, final String TenNhaCungCap, final String SDT, final String DiaChi, final String Email){
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
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        getAllNhaCungCap(Api_custom.GetTaCaNhaCungCap);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "error ThemSanPham", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDNhaCungCap",IDNhaCungCap);
                params.put("TenNhaCungCap",TenNhaCungCap);
                params.put("SDT",SDT);
                params.put("DiaChi",DiaChi);
                params.put("Email",Email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//end ThemNhaCungCap
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
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "error getAllNhaCungCap", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end getAllNhaCungCap
    private  void ThemDanhMuc(String url, final String IDDanhMuc, final String TenDanhMuc){
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
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "error ThemDanhMuc", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDDanhMuc",IDDanhMuc);
                params.put("TenDanhMuc",TenDanhMuc);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//end ThemDanhMuc
    public void getAllDanhMuc(String urlService){
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
                            mListDanhMucSanPham.clear();
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    mListDanhMucSanPham.add(new Model_ListThuocTinhSanPham(jsonObject.getString("TenDanhMuc")));
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
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "error getAllNhaCungCap", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end getAllDanhMuc
    public void getAllDonViTinh(String urlService){
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
                            mListTrongLuong.clear();
                            mListDoDai.clear();
                            mListDoDay.clear();
                            mListDonViGiaSi.clear();
                            mListDonViGiaLe.clear();
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    String trongLuong[]=jsonObject.getString("trongluong").split(";");
                                    String dairong[]=jsonObject.getString("dairong").split(";");
                                    String loai[]=jsonObject.getString("loai").split(";");
                                    for (int j=0;j<trongLuong.length;j++){
                                        mListTrongLuong.add(new Model_ListThuocTinhSanPham(trongLuong[j]));
                                    }
                                    for (int j=0;j<dairong.length;j++){
                                        mListDoDai.add(new Model_ListThuocTinhSanPham(dairong[j]));
                                        mListDoDay.add(new Model_ListThuocTinhSanPham(dairong[j]));
                                        mListDonViGiaLe.add(new Model_ListThuocTinhSanPham(dairong[j]));
                                    }
                                    for (int j=0;j<loai.length;j++){
                                        mListDonViGiaSi.add(new Model_ListThuocTinhSanPham(loai[j]));
                                    }
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
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "error getAllDonViTinh", Toast.LENGTH_SHORT).show();
                    }
                }
        );

// Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }//end getAllDanhMuc
    private  void ThemSanPhamMoi(String url, final String IDThuocTinh, final String QuyCach, final String TrongLuong, final String DoDay, final String Dai, final String thuoctinhkhac, final String IDSanPham, final String tenncc, final String tendanhmuc, final String TenSP, final String SoLuong, final String DonViTinh, final String giale, final String giasi, final String trangthaisanpham){
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
                        TastyToast.makeText(Seo_ThemSanPhamMoi.this,"Thêm thành công",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seo_ThemSanPhamMoi.this, "error ThemSanPhamMoi", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDThuocTinh",IDThuocTinh);
                params.put("QuyCach",QuyCach);
                params.put("TrongLuong",TrongLuong);
                params.put("DoDay",DoDay);
                params.put("Dai",Dai);
                params.put("thuoctinhkhac",thuoctinhkhac);
                params.put("IDSanPham",IDSanPham);
                params.put("tenncc",tenncc);
                params.put("tendanhmuc",tendanhmuc);
                params.put("TenSP",TenSP);
                params.put("SoLuong",SoLuong);
                params.put("DonViTinh",DonViTinh);
                params.put("giale",giale);
                params.put("giasi",giasi);
                params.put("trangthaisanpham",trangthaisanpham);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }//end ThemSanPhamMoi
}
