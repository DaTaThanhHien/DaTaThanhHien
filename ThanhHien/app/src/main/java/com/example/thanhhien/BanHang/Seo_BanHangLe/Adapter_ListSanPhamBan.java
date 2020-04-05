package com.example.thanhhien.BanHang.Seo_BanHangLe;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.BanHang.Seo_SanPhamDaChon.Model_SanPhamDaChon;
import com.example.thanhhien.BanHang.Seo_SanPhamDaChon.Seo_BanHang;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Adapter_ListSanPhamBan extends RecyclerView.Adapter<Adapter_ListSanPhamBan.ViewHolder> {
        private Context mContext;
        ArrayList<Model_ListSanPhamBan> sanphamArrayList;
        private  TextView txtTenSanPham,txtSoLuongSanPham,txtGiaBan,txtTongTien;
        private EditText edit_SoLuong,edit_TongTienBanLe,edit_SoLuongNhoLe;
        private ImageButton btn_TruSoLuong,btn_CongSoLuong;
        private CheckBox rad_CoSan,rad_LayMoi;
        long TongTien=0;
        long SoTienNo=0;
        long TienBanLe;
        int SoLuongBanNhoLe;
        private Adapter_GoiYKhachHang adapter_goiYKhachHang;
        public Adapter_ListSanPhamBan(Context mContext, ArrayList<Model_ListSanPhamBan> sanphamArrayList) {
            this.mContext = mContext;
            this.sanphamArrayList = sanphamArrayList;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(mContext);
            View view= layoutInflater.inflate(R.layout.item_layoutlistsanphamban,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            final Model_ListSanPhamBan model_kho=sanphamArrayList.get(position);
            // sét giá trị truyền vào
            holder.txtTenSanPham.setText(model_kho.getTenSanPham());
            String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(model_kho.getGiaSanPham()+""));
            holder.txtGiaSanPham.setText(TongTienChuyenDoi+" VNĐ");
            holder.txtNhaCungCap.setText(model_kho.getNhaCungCap());
            holder.txtThuocTinh.setText(model_kho.getThuocTinh());
            holder.txtSoLuongSanPham.setText(model_kho.getSoLuong());
            holder.txtDonViTinh.setText(model_kho.getDonViTinh());
            final int SoLuong=Integer.parseInt(model_kho.getSoLuong());
            if(SoLuong>10){
                holder.txtTinhTrangSanPham.setText("Còn hàng");
                holder.txtTinhTrangSanPham.setTextColor(Color.GREEN);
            }else  if(SoLuong<10){
                if(SoLuong <1){
                    holder.txtTinhTrangSanPham.setText("Hết hàng");
                    holder.txtTinhTrangSanPham.setTextColor(Color.parseColor("#F44336"));
                    holder.onClickItem.setBackgroundColor(Color.parseColor("#C1C5C5C5"));
                }else {
                    holder.txtTinhTrangSanPham.setText("Gần hết hàng");
                    holder.txtTinhTrangSanPham.setTextColor(Color.parseColor("#FFC107"));
                }
            }
            // sự kiện onclick vào item recycal view
            holder.onClickItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventClickBanHang(model_kho);
                }
            });

            holder.setIsRecyclable(false);
        }

        @Override
        public int getItemCount() {
            return sanphamArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtTenSanPham,txtThuocTinh,txtGiaSanPham,txtNhaCungCap,txtSoLuongSanPham,txtDonViTinh,txtTinhTrangSanPham;
            private LinearLayout onClickItem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTenSanPham=itemView.findViewById(R.id.txtTenSanPham);
                txtGiaSanPham=itemView.findViewById(R.id.txtGiaSanPham);
                txtNhaCungCap=itemView.findViewById(R.id.txtNhaCungCap);
                txtThuocTinh=itemView.findViewById(R.id.txtThuocTinh);
                txtSoLuongSanPham=itemView.findViewById(R.id.txtSoLuongSanPham);
                txtDonViTinh=itemView.findViewById(R.id.txtDonViTinh);
                txtTinhTrangSanPham=itemView.findViewById(R.id.txtTinhTrangSanPham);
                onClickItem=itemView.findViewById(R.id.onClickItem);
            }
        }
        public interface OnItemClickedListener {
            void onItemClick(String MaSanPham,String TenSanPham,String GiaSanPham,String ThuocTinh);
        }

        private OnItemClickedListener onItemClickedListener;

        public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
            this.onItemClickedListener = onItemClickedListener;
        }
    // chọn sản phẩm thanh toán
    private void eventClickBanHang(final Model_ListSanPhamBan model_listSanPhamBan) {
        final LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_bottomlayoutnguyenlieu, null);
        // ánh xạ giao diện
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnTiepTuc=view.findViewById(R.id.btnTiepTuc);
        Button btnGioHang=view.findViewById(R.id.btnGioHang);
        final Button btnThanhToan=view.findViewById(R.id.btnThanhToan);
        txtTenSanPham=view.findViewById(R.id.txtTenSanPham);
        txtSoLuongSanPham=view.findViewById(R.id.txtSoLuongSanPham);
        txtGiaBan=view.findViewById(R.id.txtGiaBan);
        edit_SoLuong=view.findViewById(R.id.edit_SoLuong);
        btn_CongSoLuong=view.findViewById(R.id.btn_CongSoLuong);
        btn_TruSoLuong=view.findViewById(R.id.btn_TruSoLuong);
        edit_TongTienBanLe=view.findViewById(R.id.edit_TongTienBanLe);
        rad_CoSan=view.findViewById(R.id.rad_CoSan);
        rad_LayMoi=view.findViewById(R.id.rad_LayMoi);
        edit_SoLuongNhoLe=view.findViewById(R.id.edit_SoLuongNhoLe);

        final Dialog mBottomSheetDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        // set dữ liệu lên
        txtTenSanPham.setText(model_listSanPhamBan.getTenSanPham());
        txtSoLuongSanPham.setText(model_listSanPhamBan.getSoLuong()+" "+model_listSanPhamBan.getDonViTinh());
        final String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(model_listSanPhamBan.getGiaSanPham()+""));
        txtGiaBan.setText(TongTienChuyenDoi+" VNĐ");
        TongTien =(Long.parseLong(edit_SoLuong.getText().toString().trim())*(Long.parseLong(model_listSanPhamBan.getGiaSanPham())));
        btnThanhToan.setText("Thanh toán: "+Seo_BanHangLe.TongTien+" VNĐ");
        //<=================================sự kiện=================================================>
        // sự kiện đóng trang dialog
        btnclosedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;
                mBottomSheetDialog.dismiss();
            }
        });
        // sự kiện tiếp tục và thêm vào mục đã mua
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SoLuongNhoLe=edit_SoLuongNhoLe.getText().toString();
                String TongTienNhoLe=edit_TongTienBanLe.getText().toString();

                // xét sự kiện tiếp tục

                if(SoLuongNhoLe.length()>0 || TongTienNhoLe.length()>0 ){
                    if(rad_CoSan.isChecked()==false && rad_LayMoi.isChecked()==false){
                        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Lỗi!!!")
                                .setContentText("Vui lòng chọn có sẵn hoặc lấy mới")
                                .show();
                    }else {
                        if(SoLuongNhoLe.length()==0 || TongTienNhoLe.length()==0){
                            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Lỗi!!!")
                                    .setContentText("Vui lòng nhập đầy đủ thông tin ")
                                    .show();
                        }else {

                            TongTien=0;
                            mBottomSheetDialog.dismiss();
                        }
                    }
                }else {
                    if(rad_CoSan.isChecked()==true || rad_LayMoi.isChecked()==true){
                        if(SoLuongNhoLe.length()==0 || TongTienNhoLe.length()==0){
                            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Lỗi!!!")
                                    .setContentText("Vui lòng nhập đầy đủ thông tin")
                                    .show();
                        }
                    }else {
                        TongTien=0;
                        mBottomSheetDialog.dismiss();
                    }
                }
                if(edit_TongTienBanLe.length()==0 || edit_SoLuongNhoLe.length()==0){
                    TienBanLe=0;
                    SoLuongBanNhoLe=0;
                }else {
                    TienBanLe= Long.parseLong(edit_TongTienBanLe.getText().toString().trim());
                    SoLuongBanNhoLe=Integer.parseInt(edit_SoLuongNhoLe.getText().toString().trim());
                }
                // thêm vào giỏ
                if(Seo_BanHangLe.mListSanPhamDaChon.size()>0){
                    boolean isChecked=false;
                    for(int i=0;i<Seo_BanHangLe.mListSanPhamDaChon.size();i++){
                        Seo_BanHangLe.TongTien=0;
                        if(model_listSanPhamBan.getMaSanPham().equalsIgnoreCase(Seo_BanHangLe.mListSanPhamDaChon.get(i).getMaSanPham())){
                            Seo_BanHangLe.mListSanPhamDaChon.get(i).setSoLuong(Integer.parseInt(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuong())+Integer.parseInt(edit_SoLuong.getText().toString())+"");
                            if(rad_CoSan.isChecked()==true || rad_LayMoi.isChecked()==true){
                                Seo_BanHangLe.mListSanPhamDaChon.get(i).setSoLuongNhoLe(Integer.parseInt(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuongNhoLe())+Integer.parseInt(edit_SoLuongNhoLe.getText().toString())+"");
                                Seo_BanHangLe.mListSanPhamDaChon.get(i).setSoTienNhoLe(Integer.parseInt(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoTienNhoLe())+Integer.parseInt(edit_TongTienBanLe.getText().toString())+"");
                            }

                            isChecked=true;
                            for(int j=0;j<Seo_BanHangLe.mListSanPhamDaChon.size();j++){
                                Seo_BanHangLe.TongTien= Seo_BanHangLe.TongTien+Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(j).getSoTienNhoLe())+Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(j).getDonGia())*Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(j).getSoLuong());
                                String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble( Seo_BanHangLe.TongTien+""));
                                Seo_ListSanPhamBanLe.btnThanhToan.setText("Thanh toán: "+TongTienChuyenDoi+ " VNĐ");
                            }
                        }
                    }
                    if(isChecked==false){
                        Seo_BanHangLe.TongTien=0;
                        Seo_BanHangLe.mListSanPhamDaChon.add(new Model_SanPhamDaChon(model_listSanPhamBan.getMaSanPham(),model_listSanPhamBan.getTenSanPham(),edit_SoLuong.getText().toString(),model_listSanPhamBan.getDonViTinh(),model_listSanPhamBan.getGiaSanPham()+"",model_listSanPhamBan.getThuocTinh(),model_listSanPhamBan.getNhaCungCap(),SoLuongBanNhoLe+"",TienBanLe+""));
                        String TongTienBanLe=edit_TongTienBanLe.getText().toString();
                        if(TongTienBanLe.length()==0){
                            TongTienBanLe="0";
                        }

                        for(int i=0;i<Seo_BanHangLe.mListSanPhamDaChon.size();i++){
                            Seo_BanHangLe.TongTien= Seo_BanHangLe.TongTien+Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoTienNhoLe())+(Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getDonGia())*Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuong()));
                            String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble( Seo_BanHangLe.TongTien+""));
                            Seo_ListSanPhamBanLe.btnThanhToan.setText("Thanh toán: "+TongTienChuyenDoi+ " VNĐ");
                        }
                    }
                }else {
                    Seo_BanHangLe.TongTien=0;
                    Seo_BanHangLe.mListSanPhamDaChon.add(new Model_SanPhamDaChon(model_listSanPhamBan.getMaSanPham(),model_listSanPhamBan.getTenSanPham(),
                            edit_SoLuong.getText().toString(),model_listSanPhamBan.getDonViTinh(),
                            model_listSanPhamBan.getGiaSanPham()+"",
                            model_listSanPhamBan.getThuocTinh(),model_listSanPhamBan.getNhaCungCap(),
                            SoLuongBanNhoLe+"",TienBanLe+""));
                    String TongTienBanLe=edit_TongTienBanLe.getText().toString();
                    if(TongTienBanLe.length()==0){
                        TongTienBanLe="0";
                    }
                    for(int i=0;i<Seo_BanHangLe.mListSanPhamDaChon.size();i++){
                        Seo_BanHangLe.TongTien= Seo_BanHangLe.TongTien+Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoTienNhoLe())+Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getDonGia())*Long.parseLong(Seo_BanHangLe.mListSanPhamDaChon.get(i).getSoLuong());
                        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble( Seo_BanHangLe.TongTien+""));
                        Seo_ListSanPhamBanLe.btnThanhToan.setText("Thanh toán: "+TongTienChuyenDoi+ " VNĐ");

                    }
                }

            }
        });
        //sự kiện lúc thanh toán
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SoLuongNhoLe=edit_SoLuongNhoLe.getText().toString();
                String TongTienNhoLe=edit_TongTienBanLe.getText().toString();
                if(SoLuongNhoLe.length()>0 || TongTienNhoLe.length()>0 ){
                    if(rad_CoSan.isChecked()==false && rad_LayMoi.isChecked()==false){
                        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Lỗi!!!")
                                .setContentText("Vui lòng chọn có sẵn hoặc lấy mới")
                                .show();
                    }else {
                       if(SoLuongNhoLe.length()==0 || TongTienNhoLe.length()==0){
                           new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                   .setTitleText("Lỗi!!!")
                                   .setContentText("Vui lòng nhập đầy đủ thông tin")
                                   .show();
                       }else {
                           eventClickThanhToan();
                       }
                    }
                }else {
                    if(rad_CoSan.isChecked()==true || rad_LayMoi.isChecked()==true){
                        if(SoLuongNhoLe.length()==0 || TongTienNhoLe.length()==0){
                            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Lỗi!!!")
                                    .setContentText("Vui lòng nhập đầy đủ thông tin")
                                    .show();
                        }
                    }else {
                        eventClickThanhToan();
                    }
                }

            }
        });
        //sự kiện qua trang gio hàng
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Seo_BanHang.class);
                mContext.startActivity(intent);
            }
        });
        // sự kiện tăng sô lượng
        btn_CongSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;
               if(edit_TongTienBanLe.length()==0){
                   TienBanLe=0;
               }else {
                   TienBanLe= Long.parseLong(edit_TongTienBanLe.getText().toString().trim());
               }
                edit_SoLuong.setText((Integer.parseInt(edit_SoLuong.getText().toString().trim())+1)+"");
                //TongTien=TongTien+(Integer.parseInt(edit_SoLuong.getText().toString()))*(Long.parseLong(model_listSanPhamBan.getGiaSanPham()))+TienBanLe;

            }
        });
        // sự kiện trừ số luọng
        btn_TruSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongTien=0;
                edit_SoLuong.setText((Integer.parseInt(edit_SoLuong.getText().toString().trim())-1)+"");
                if(Integer.parseInt(edit_SoLuong.getText().toString().trim())<0){
                    new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Lỗi!!!")
                            .setContentText("Số lượng không thể nhỏ hơn 0")
                            .show();
                    edit_SoLuong.setText(0+"");
                }
                if(edit_TongTienBanLe.length()==0){
                    TienBanLe=0;
                }else {
                    TienBanLe= Long.parseLong(edit_TongTienBanLe.getText().toString().trim());
                }
               // TongTien=TongTien+(Integer.parseInt(edit_SoLuong.getText().toString()))*(Long.parseLong(model_listSanPhamBan.getGiaSanPham()))+TienBanLe;
            }
        });
        // sự kiện chuyển đổi kiểu tiền


    }

    private void eventClickThanhToan() {
        final LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layuotthanhtoanhoadon, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnNhapHang=view.findViewById(R.id.btnNhapHang);
        final TextView txtTienThua=view.findViewById(R.id.txtTienThua);
        final TextView editDaTra=view.findViewById(R.id.editDaTra);
        TextView txtTongTien=view.findViewById(R.id.txtTongTien);
        btnNhapHang.setText("Thanh toán");
        final EditText edit_TenKhachHnag=view.findViewById(R.id.edit_TenKhachHnag);
        AutoCompleteTextView autoCompleteTextView=view.findViewById(R.id.autoCompleteTextView);
        adapter_goiYKhachHang = new Adapter_GoiYKhachHang(mContext, R.layout.item_goiykhachhang, Seo_BanHangLe.mListKhachHang);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_goiYKhachHang);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model_GoiYKhachHang model_goiYKhachHang=Seo_BanHangLe.mListKhachHang.get(position);
                edit_TenKhachHnag.setText(model_goiYKhachHang.getTenKhachHang());
            }
        });
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
                    long TongTienTra=Seo_BanHangLe.TongTien-Long.parseLong(editDaTra.getText().toString());
                    String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(TongTienTra+""));
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
        final Dialog mBottomSheetDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
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

                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
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

}
