package com.example.thanhhien.NhapXuatHang;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Adapter_QuyCachVaTinhChat;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Adapter_ThuocTinhSanPham;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Model_ListThuocTinhSanPham;
import com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham.Seo_ThemSanPhamMoi;
import com.example.thanhhien.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class Adapter_ListSanPhamNhap extends RecyclerView.Adapter<Adapter_ListSanPhamNhap.ViewHolder> {
        private Context mContext;
        double chia=1;
        ArrayList<Model_ListSanPhamBan> sanphamArrayList;
        private Adapter_QuyCachVaTinhChat adapter_quyCachVaTinhChat;
        long SoTienNo=0;
        public Adapter_ListSanPhamNhap(Context mContext, ArrayList<Model_ListSanPhamBan> sanphamArrayList) {
            this.mContext = mContext;
            this.sanphamArrayList = sanphamArrayList;
        }
        private ArrayList<Model_ListThuocTinhSanPham> mListDonViNhap=new ArrayList<>();
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(mContext);
            View view= layoutInflater.inflate(R.layout.item_layoutlistsanphamnhap,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            final Model_ListSanPhamBan model_kho=sanphamArrayList.get(position);
            // sét giá trị truyền vào
            holder.txtTenSanPham.setText(model_kho.getTenSanPham());
            String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(model_kho.getGiaSanPham()+""));
            holder.txtGiaSanPham.setText(TongTienChuyenDoi+" VNĐ");
            holder.txtNhaCungCap.setText(model_kho.getNhaCungCap());
            holder.txtThuocTinh.setText(model_kho.getThuocTinh());
            holder.txtSoLuongSanPham.setText(model_kho.getSoLuong());
            holder.txtDonViTinh.setText(model_kho.getDonViTinh());
            final double SoLuong=Double.parseDouble(model_kho.getSoLuong()+"");
            if(SoLuong>10){
                holder.txtTinhTrangSanPham.setText("Còn hàng");
                holder.txtTinhTrangSanPham.setTextColor(Color.GREEN);
            }else  if(SoLuong<10){
                if(SoLuong <=0){
                    holder.txtTinhTrangSanPham.setText("Hết hàng");
                    holder.txtTinhTrangSanPham.setTextColor(Color.parseColor("#F44336"));
                    holder.onClickItem.setBackgroundColor(Color.parseColor("#C1C5C5C5"));
                }else {
                    holder.txtTinhTrangSanPham.setText("Sắp hết");
                    holder.txtTinhTrangSanPham.setTextColor(Color.parseColor("#FFC107"));
                }
            }
            // sự kiện onclick vào item recycal view
            holder.onClickItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventClickBanHang(sanphamArrayList.get(position).getTenSanPham(),sanphamArrayList.get(position).getThuocTinh(),sanphamArrayList.get(position).getDonViTinh(),sanphamArrayList.get(position).getDonViNhap(),position,Double.parseDouble(sanphamArrayList.get(position).getDai()),Double.parseDouble(sanphamArrayList.get(position).getNang()));
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
            void onItemClick(String MaSanPham, String TenSanPham, String GiaSanPham, String ThuocTinh);
        }

        private OnItemClickedListener onItemClickedListener;

        public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
            this.onItemClickedListener = onItemClickedListener;
        }
    // chọn sản phẩm thanh toán
    private void eventClickBanHang(String TenSP,  String TrongLuong, final String DonViTinh, final String DonViNhap, final int position, final double Dai, final double Nang) {
        final LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layoutnhapsanpham, null);
        TextView txtTenSP=view.findViewById(R.id.txtTenSP);
        final TextView txtSLQuyDoi2=view.findViewById(R.id.txtSLQuyDoi2);
        final TextView txtSLQuyDoi1=view.findViewById(R.id.txtSLQuyDoi1);
        final TextView TongTienNhap=view.findViewById(R.id.TongTien);
        TextView txtTrongLuong=view.findViewById(R.id.txtTrongLuong);
        final TextView txtDonViNhap=view.findViewById(R.id.txtDonViNhap);
        final EditText edit_GiaNhap=view.findViewById(R.id.edit_GiaNhap);
        ImageView ibTang=view.findViewById(R.id.ibTang);
        ImageView ibGiam=view.findViewById(R.id.ibGiam);
        final EditText edit_SoLuong=view.findViewById(R.id.edit_SoLuong);
        txtTenSP.setText(TenSP);
        txtTrongLuong.setText(TrongLuong);
        txtDonViNhap.setText(DonViTinh);
        double  kq1=Math.round((Double.parseDouble(edit_SoLuong.getText().toString())/chia)*10);
        double  kq=kq1/10;
        txtSLQuyDoi2.setText(kq+DonViTinh);
        txtSLQuyDoi1.setText(((edit_SoLuong.getText().toString()))+txtDonViNhap.getText().toString().trim());
        ibTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    edit_SoLuong.setText((Integer.parseInt(edit_SoLuong.getText().toString().trim())+1)+"");
                    double  kq1=Math.round((Double.parseDouble(edit_SoLuong.getText().toString())/chia)*10);
                    double  kq=kq1/10;
                    txtSLQuyDoi2.setText(kq+DonViTinh);
                    txtSLQuyDoi1.setText(((edit_SoLuong.getText().toString()))+txtDonViNhap.getText().toString().trim());
            }
        });
        ibGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(edit_SoLuong.getText().toString().trim())>1){
                    edit_SoLuong.setText((Integer.parseInt(edit_SoLuong.getText().toString().trim())-1)+"");
                    double  kq1=Math.round((Double.parseDouble(edit_SoLuong.getText().toString())/chia)*10);
                    double  kq=kq1/10;
                    txtSLQuyDoi2.setText(kq+DonViTinh);
                    txtSLQuyDoi1.setText(((edit_SoLuong.getText().toString()))+txtDonViNhap.getText().toString().trim());
                }
            }
        });
        edit_SoLuong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(edit_SoLuong.getText().toString().trim().length()==0){
                    edit_SoLuong.setText("1");
                }else if(Integer.parseInt(edit_SoLuong.getText().toString().trim())<1){
                    edit_SoLuong.setText("1");
                }
                double  kq1=Math.round((Double.parseDouble(edit_SoLuong.getText().toString())/chia)*10);
                double  kq=kq1/10;
                txtSLQuyDoi2.setText(kq+DonViTinh);
                txtSLQuyDoi1.setText(((edit_SoLuong.getText().toString()))+txtDonViNhap.getText().toString().trim());

            }
        });
        edit_GiaNhap.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(edit_GiaNhap.getText().toString().trim().length()==0){
                    edit_GiaNhap.setText("0");
                    TongTienNhap.setText("0 VNĐ");
                }else {
                    TongTienNhap.setText(Long.parseLong(edit_GiaNhap.getText().toString().trim())*Long.parseLong(edit_SoLuong.getText().toString())+" VNĐ");
                }
                return false;
            }
        });
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnTiepTuc=view.findViewById(R.id.btnTiepTuc);
        final Button btnThanhToan=view.findViewById(R.id.btnThanhToan);
        btnThanhToan.setText("Nhập hàng: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
        final Dialog mBottomSheetDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        // sự kiện đóng bottomm sheet
        btnclosedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();
            }
        });
        // sự kiện chọn đơn vị nhập
        txtDonViNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSLQuyDoi1.setText(((edit_SoLuong.getText().toString()))+txtDonViNhap.getText().toString().trim());
                bottomChonDonViNhap(DonViNhap,txtDonViNhap,Dai,Nang,txtSLQuyDoi2,edit_SoLuong.getText().toString().trim(),DonViTinh,txtSLQuyDoi1,edit_SoLuong.getText().toString().trim());
            }
        });
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Seo_GiaoDienDanhMuc.IDNhaCungCap.equalsIgnoreCase("null")){
                    Seo_GiaoDienDanhMuc.IDNhaCungCap=sanphamArrayList.get(position).getNhaCungCap();
                }else {
                    if(!Seo_GiaoDienDanhMuc.IDNhaCungCap.equalsIgnoreCase(sanphamArrayList.get(position).getNhaCungCap())){
                        Seo_GiaoDienDanhMuc.IDNhaCungCap="khac";
                    }
                }
                if(edit_GiaNhap.getText().length()==0 || Integer.parseInt(edit_GiaNhap.getText().toString().trim())==0){
                    TastyToast.makeText(mContext,"Vui lòng nhập giá nhập",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                }else {
                    mBottomSheetDialog.dismiss();
                if(Seo_GiaoDienDanhMuc.gioHang.size()==0){
                    Seo_GiaoDienDanhMuc.gioHang.add(new Model_ListSanPhamBan(sanphamArrayList.get(position).getMaSanPham(),sanphamArrayList.get(position).getTenSanPham(),(Integer.parseInt(edit_SoLuong.getText().toString()))+"",edit_GiaNhap.getText().toString().trim(),txtDonViNhap.getText().toString(),sanphamArrayList.get(position).getThuocTinh(),sanphamArrayList.get(position).getQuyCach(),(Double.parseDouble(edit_SoLuong.getText().toString())/chia)+"","","","","",""));
                    Seo_ListSanPhamNhapKho.tongTien=Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(edit_GiaNhap.getText().toString().trim());
                    Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                }else{
                    for (int i=0;i<Seo_GiaoDienDanhMuc.gioHang.size();i++){
                        if(i==(Seo_GiaoDienDanhMuc.gioHang.size()-1)){
                            if(sanphamArrayList.get(position).getMaSanPham().equalsIgnoreCase((Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham()))){
                                Seo_GiaoDienDanhMuc.gioHang.set(i,new Model_ListSanPhamBan(Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham(),Seo_GiaoDienDanhMuc.gioHang.get(i).getTenSanPham(),(Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong())+Integer.parseInt(edit_SoLuong.getText().toString()))+"",Seo_GiaoDienDanhMuc.gioHang.get(i).getGiaSanPham(),Seo_GiaoDienDanhMuc.gioHang.get(i).getDonViTinh(),Seo_GiaoDienDanhMuc.gioHang.get(i).getThuocTinh(),Seo_GiaoDienDanhMuc.gioHang.get(i).getQuyCach(),(Double.parseDouble(""+(Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong())+Integer.parseInt(edit_SoLuong.getText().toString())))/chia)+"","","","","",""));
                                Seo_ListSanPhamNhapKho.tongTien=Seo_ListSanPhamNhapKho.tongTien+(Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(edit_GiaNhap.getText().toString().trim()));
                                Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                                i=i+Seo_GiaoDienDanhMuc.gioHang.size()+10;
                            }else{
                                Seo_GiaoDienDanhMuc.gioHang.add(new Model_ListSanPhamBan(sanphamArrayList.get(position).getMaSanPham(),sanphamArrayList.get(position).getTenSanPham(),(Integer.parseInt(edit_SoLuong.getText().toString()))+"",edit_GiaNhap.getText().toString().trim(),txtDonViNhap.getText().toString(),sanphamArrayList.get(position).getThuocTinh(),sanphamArrayList.get(position).getQuyCach(),(Double.parseDouble(edit_SoLuong.getText().toString())/chia)+"","","","","",""));
                                Seo_ListSanPhamNhapKho.tongTien=Seo_ListSanPhamNhapKho.tongTien+(Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(edit_GiaNhap.getText().toString().trim()));
                                Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                                i=i+Seo_GiaoDienDanhMuc.gioHang.size()+10;
                            }
                        }else{
                            if(sanphamArrayList.get(position).getMaSanPham().equalsIgnoreCase((Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham()))){
                                Seo_GiaoDienDanhMuc.gioHang.set(i,new Model_ListSanPhamBan(Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham(),Seo_GiaoDienDanhMuc.gioHang.get(i).getTenSanPham(),(Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong())+Integer.parseInt(edit_SoLuong.getText().toString()))+"",Seo_GiaoDienDanhMuc.gioHang.get(i).getGiaSanPham(),Seo_GiaoDienDanhMuc.gioHang.get(i).getDonViTinh(),Seo_GiaoDienDanhMuc.gioHang.get(i).getThuocTinh(),Seo_GiaoDienDanhMuc.gioHang.get(i).getQuyCach(),(Double.parseDouble(""+(Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong())+Integer.parseInt(edit_SoLuong.getText().toString())))/chia)+"","","","","",""));
                                Seo_ListSanPhamNhapKho.tongTien=Seo_ListSanPhamNhapKho.tongTien+(Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(edit_GiaNhap.getText().toString().trim()));
                                Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                                i=i+Seo_GiaoDienDanhMuc.gioHang.size()+10;
                            }
                        }
                    }
                }
                }
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Seo_GiaoDienDanhMuc.IDNhaCungCap.equalsIgnoreCase("null")){
                    Seo_GiaoDienDanhMuc.IDNhaCungCap=sanphamArrayList.get(position).getNhaCungCap();
                }else {
                    if(!Seo_GiaoDienDanhMuc.IDNhaCungCap.equalsIgnoreCase(sanphamArrayList.get(position).getNhaCungCap())){
                        Seo_GiaoDienDanhMuc.IDNhaCungCap="khac";
                    }
                }

                if(edit_GiaNhap.getText().length()==0 || Integer.parseInt(edit_GiaNhap.getText().toString().trim())==0){
                    TastyToast.makeText(mContext,"Vui lòng nhập giá nhập",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                }else {
                    if (Seo_GiaoDienDanhMuc.gioHang.size() == 0) {
                        Seo_GiaoDienDanhMuc.gioHang.add(new Model_ListSanPhamBan(sanphamArrayList.get(position).getMaSanPham(), sanphamArrayList.get(position).getTenSanPham(), (Integer.parseInt(edit_SoLuong.getText().toString())) + "", edit_GiaNhap.getText().toString().trim(), txtDonViNhap.getText().toString(), sanphamArrayList.get(position).getThuocTinh(), sanphamArrayList.get(position).getQuyCach(), (Double.parseDouble(edit_SoLuong.getText().toString()) / chia) + "", "", "", "", "", ""));
                        Seo_ListSanPhamNhapKho.tongTien = Double.parseDouble(edit_SoLuong.getText().toString()) * Double.parseDouble(edit_GiaNhap.getText().toString().trim());
                        Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: " + ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien) + " VNĐ");
                    } else {
                        for (int i = 0; i < Seo_GiaoDienDanhMuc.gioHang.size(); i++) {
                            if (i == (Seo_GiaoDienDanhMuc.gioHang.size() - 1)) {
                                if (sanphamArrayList.get(position).getMaSanPham().equalsIgnoreCase((Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham()))) {
                                    Seo_GiaoDienDanhMuc.gioHang.set(i, new Model_ListSanPhamBan(Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham(), Seo_GiaoDienDanhMuc.gioHang.get(i).getTenSanPham(), (Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong()) + Integer.parseInt(edit_SoLuong.getText().toString())) + "", Seo_GiaoDienDanhMuc.gioHang.get(i).getGiaSanPham(), Seo_GiaoDienDanhMuc.gioHang.get(i).getDonViTinh(), Seo_GiaoDienDanhMuc.gioHang.get(i).getThuocTinh(), Seo_GiaoDienDanhMuc.gioHang.get(i).getQuyCach(), (Double.parseDouble("" + (Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong()) + Integer.parseInt(edit_SoLuong.getText().toString()))) / chia) + "", "", "", "", "", ""));
                                    Seo_ListSanPhamNhapKho.tongTien = Seo_ListSanPhamNhapKho.tongTien + (Double.parseDouble(edit_SoLuong.getText().toString()) * Double.parseDouble(edit_GiaNhap.getText().toString().trim()));
                                    Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: " + ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien) + " VNĐ");
                                    i = i + Seo_GiaoDienDanhMuc.gioHang.size() + 10;
                                } else {
                                    Seo_GiaoDienDanhMuc.gioHang.add(new Model_ListSanPhamBan(sanphamArrayList.get(position).getMaSanPham(), sanphamArrayList.get(position).getTenSanPham(), (Integer.parseInt(edit_SoLuong.getText().toString())) + "", edit_GiaNhap.getText().toString().trim(), txtDonViNhap.getText().toString(), sanphamArrayList.get(position).getThuocTinh(), sanphamArrayList.get(position).getQuyCach(), (Double.parseDouble(edit_SoLuong.getText().toString()) / chia) + "", "", "", "", "", ""));
                                    Seo_ListSanPhamNhapKho.tongTien = Seo_ListSanPhamNhapKho.tongTien + (Double.parseDouble(edit_SoLuong.getText().toString()) * Double.parseDouble(edit_GiaNhap.getText().toString().trim()));
                                    Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: " + ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien) + " VNĐ");
                                    i = i + Seo_GiaoDienDanhMuc.gioHang.size() + 10;
                                }
                            } else {
                                if (sanphamArrayList.get(position).getMaSanPham().equalsIgnoreCase((Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham()))) {
                                    Seo_GiaoDienDanhMuc.gioHang.set(i, new Model_ListSanPhamBan(Seo_GiaoDienDanhMuc.gioHang.get(i).getMaSanPham(), Seo_GiaoDienDanhMuc.gioHang.get(i).getTenSanPham(), (Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong()) + Integer.parseInt(edit_SoLuong.getText().toString())) + "", Seo_GiaoDienDanhMuc.gioHang.get(i).getGiaSanPham(), Seo_GiaoDienDanhMuc.gioHang.get(i).getDonViTinh(), Seo_GiaoDienDanhMuc.gioHang.get(i).getThuocTinh(), Seo_GiaoDienDanhMuc.gioHang.get(i).getQuyCach(), (Double.parseDouble("" + (Integer.parseInt(Seo_GiaoDienDanhMuc.gioHang.get(i).getSoLuong()) + Integer.parseInt(edit_SoLuong.getText().toString()))) / chia) + "", "", "", "", "", ""));
                                    Seo_ListSanPhamNhapKho.tongTien = Seo_ListSanPhamNhapKho.tongTien + (Double.parseDouble(edit_SoLuong.getText().toString()) * Double.parseDouble(edit_GiaNhap.getText().toString().trim()));
                                    Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: " + ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien) + " VNĐ");
                                    i = i + Seo_GiaoDienDanhMuc.gioHang.size() + 10;
                                }
                            }
                        }
                    }
                    Intent intent = new Intent(mContext, Seo_ThanhToanNhapHang.class);
                    mContext.startActivity(intent);
                    mBottomSheetDialog.dismiss();
                }
            }
        });

    }
    private void bottomChonDonViNhap(String DonViNhap, final TextView txtDonViNhap,final double Dai,final double Nang,final TextView txtSLQuyDoi2,final String soLuong2,final String DonViTinh,final TextView txtSLQuyDoi1,final String soLuong1){
        mListDonViNhap.clear();
        final LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_bottomlayoutthemsanpham, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        TextView txtTitle=view.findViewById(R.id.txtTitle);
        txtTitle.setText("Chọn đơn vị nhập");
        ListView listViewThuocTinh=view.findViewById(R.id.listViewThuocTinh);
        String mangDonVi[]=DonViNhap.split(";");
        mListDonViNhap.add(new Model_ListThuocTinhSanPham("1",mangDonVi[1]));
        mListDonViNhap.add(new Model_ListThuocTinhSanPham("2",mangDonVi[2]));
        mListDonViNhap.add(new Model_ListThuocTinhSanPham("3",mangDonVi[3]));
        Adapter_ThuocTinhSanPham adapter_thuocTinhSanPham=new Adapter_ThuocTinhSanPham(mContext,R.layout.item_layoutspiner,mListDonViNhap);
        listViewThuocTinh.setAdapter(adapter_thuocTinhSanPham);
        final Dialog mBottomSheetDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        btnclosedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListDonViNhap.clear();
                mBottomSheetDialog.dismiss();
            }
        });
        listViewThuocTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    chia=Dai;
                }else if(position==1){
                    chia=Nang;
                }else{
                    chia=1;
                }
                txtDonViNhap.setText(mListDonViNhap.get(position).getTenSanPham());
                mBottomSheetDialog.dismiss();
                double  kq1=Math.round((Double.parseDouble(soLuong2)/chia)*10);
                double  kq=kq1/10;
                txtSLQuyDoi2.setText(kq+" "+DonViTinh);
                txtSLQuyDoi1.setText(((soLuong1))+txtDonViNhap.getText().toString().trim());
            }
        });


    }//kết thúc hàm
}

