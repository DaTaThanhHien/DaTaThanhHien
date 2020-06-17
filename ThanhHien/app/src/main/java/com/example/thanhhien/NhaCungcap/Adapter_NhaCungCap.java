package com.example.thanhhien.NhaCungcap;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Adapter_NhaCungCap extends RecyclerView.Adapter<Adapter_NhaCungCap.ViewHolder> {
        private Context mContext;
        ArrayList<Model_NhaCungCap> listNhaCungCap;

        public Adapter_NhaCungCap(Context mContext, ArrayList<Model_NhaCungCap> listNhaCungCap) {
            this.mContext = mContext;
            this.listNhaCungCap = listNhaCungCap;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(mContext);
            View view= layoutInflater.inflate(R.layout.item_layoutlistnhacungcap,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final Model_NhaCungCap model_nhaCungCap=listNhaCungCap.get(position);
            holder.txtNhaCungCap.setText(model_nhaCungCap.getTenNhaCungCap());
            holder.txtSoDienThoai.setText(model_nhaCungCap.getSoDienThoai());
            holder.txtDiaChiNCC.setText(model_nhaCungCap.getDiaChi());
            String TongTienNoChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(model_nhaCungCap.getNo()+""));
            holder.txtNoNhaCunCap.setText(TongTienNoChuyenDoi+" VNĐ");
        }

        @Override
        public int getItemCount() {
            return listNhaCungCap.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtNhaCungCap,txtDiaChiNCC,txtSoDienThoai,txtNoNhaCunCap;
            private LinearLayout btnSuaNhaCungCap,btnXoaNhaCungCap,btnChiTiet;
            public ViewHolder(@NonNull View itemView) {

                super(itemView);
                txtSoDienThoai=itemView.findViewById(R.id.txtSoDienThoai);
                txtDiaChiNCC=itemView.findViewById(R.id.txtDiaChiNCC);
                txtNhaCungCap=itemView.findViewById(R.id.txtNhaCungCap);
                btnSuaNhaCungCap=itemView.findViewById(R.id.btnSuaNhaCungCap);
                txtNoNhaCunCap=itemView.findViewById(R.id.txtNoNhaCunCap);
            }

        }

    }

