package com.example.thanhhien.HoaDon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;

import java.util.ArrayList;
public class Adapter_ChiTietHoaDon extends RecyclerView.Adapter<Adapter_ChiTietHoaDon.ViewHolder> {
    private Context mContext;
    ArrayList<Model_ChiTietHoaDon> listHoaDon;
    public Adapter_ChiTietHoaDon(Context mContext, ArrayList<Model_ChiTietHoaDon> listHoaDon) {
        this.mContext = mContext;
        this.listHoaDon = listHoaDon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.item_thanhtoannhaphang,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Model_ChiTietHoaDon model_chiTietHoaDon=listHoaDon.get(position);
        holder.txtTenHang.setText(model_chiTietHoaDon.getTenSanPham());
        holder.txtSoLuong.setText(model_chiTietHoaDon.getSoLuong());
        holder.txtDonGia.setText(model_chiTietHoaDon.getDonGia());
        holder.txtThanhTien.setText(model_chiTietHoaDon.getThanhTien());
        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTenHang,txtSoLuong,txtDonGia,txtThanhTien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHang=itemView.findViewById(R.id.txtTenHang);
            txtSoLuong=itemView.findViewById(R.id.txtSoLuong);
            txtDonGia=itemView.findViewById(R.id.txtDonGia);
            txtThanhTien=itemView.findViewById(R.id.txtThanhTien);
        }

    }
}

