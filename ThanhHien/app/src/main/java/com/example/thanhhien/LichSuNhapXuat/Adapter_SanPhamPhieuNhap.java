package com.example.thanhhien.LichSuNhapXuat;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.NhapXuatHang.Seo_BanHang_GioHang;
import com.example.thanhhien.NhapXuatHang.Seo_ChonNhaCungCap;
import com.example.thanhhien.NhapXuatHang.Seo_ListSanPhamNhapKho;
import com.example.thanhhien.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Adapter_SanPhamPhieuNhap extends RecyclerView.Adapter<Adapter_SanPhamPhieuNhap.ViewHolder> {
    private Context mContext;
    ArrayList<Model_ListSanPhamBan> sanphamArrayList;
    long SoTienNo=0;
    public Adapter_SanPhamPhieuNhap(Context mContext, ArrayList<Model_ListSanPhamBan> sanphamArrayList) {
        this.mContext = mContext;
        this.sanphamArrayList = sanphamArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.item_nhaphang_giohang,parent,false);
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
        holder.txtSoLuongSanPham.setText(model_kho.getSoLuong());
        holder.txtNhaCungCap.setVisibility(View.GONE);
        holder.txtThuocTinh.setVisibility(View.GONE);
        holder.ivGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.ivTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTenSanPham,txtThuocTinh,txtGiaSanPham,txtNhaCungCap,txtSoLuongSanPham,txtDonViTinh;
        private ImageView ivGiam,ivTang,txtXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham=itemView.findViewById(R.id.txtTenSanPham);
            txtGiaSanPham=itemView.findViewById(R.id.txtGiaSanPham);
            txtNhaCungCap=itemView.findViewById(R.id.txtNhaCungCap);
            txtThuocTinh=itemView.findViewById(R.id.txtThuocTinh);
            txtSoLuongSanPham=itemView.findViewById(R.id.txtSoLuongSanPham);
            txtDonViTinh=itemView.findViewById(R.id.txtDonViTinh);
            ivGiam=itemView.findViewById(R.id.ivGiam);
            ivTang=itemView.findViewById(R.id.ivTang);
            txtXoa=itemView.findViewById(R.id.txtXoa);
        }
    }
}
