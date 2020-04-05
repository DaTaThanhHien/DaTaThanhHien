package com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
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

import com.example.thanhhien.Api_custom;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Adapter_QuyCachVaTinhChat;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_QuyCachVaTinhChat;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.R;
import com.example.thanhhien.Seo_GiaoDienLogin;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.EventListener;

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
        onClick();
        // tạo mảng
        mListNhaCungCap=new ArrayList<>();
        mListNhaCungCap.add(new Model_ListThuocTinhSanPham("Tôn hoa sen"));
        mListDanhMucSanPham=new ArrayList<>();
        mListDanhMucSanPham.add(new Model_ListThuocTinhSanPham("Tôn hoa sen"));
        mListTrongLuong=new ArrayList<>();
        mListTrongLuong.add(new Model_ListThuocTinhSanPham("Tôn hoa sen"));
        mListDoDai=new ArrayList<>();
        mListDoDai.add(new Model_ListThuocTinhSanPham("Tôn hoa sen"));
        mListDoDay=new ArrayList<>();
        mListDoDay.add(new Model_ListThuocTinhSanPham("Tôn hoa sen"));
        mListDonViGiaLe=new ArrayList<>();
        mListDonViGiaLe.add(new Model_ListThuocTinhSanPham("Tôn hoa sen"));
        mListDonViGiaSi=new ArrayList<>();
        mListDonViGiaSi.add(new Model_ListThuocTinhSanPham("Tôn hoa sen"));
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
                    TastyToast.makeText(Seo_ThemSanPhamMoi.this,"Thêm thành công",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
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
                mBottomSheetDialog.dismiss();
            }
        });


    }//kết thúc hàm

    // thêm nhà cung cấp
    private void bottomThemNhaCungCap(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomthemnhacungcap, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
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
                mBottomSheetDialog.dismiss();
            }
        });


    }//kết thúc hàm

    // thêm danh danh mục sản phẩm
    private void bottomThemDanhMucSanPham(){
        View view = getLayoutInflater().inflate(R.layout.item_bottomlayoutthemdanhmuc, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
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

    }//kết thúc hàm

}
