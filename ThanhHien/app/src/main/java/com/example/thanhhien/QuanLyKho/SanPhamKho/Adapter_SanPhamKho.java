package com.example.thanhhien.QuanLyKho.SanPhamKho;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;

import java.util.ArrayList;


public class Adapter_SanPhamKho extends RecyclerView.Adapter<Adapter_SanPhamKho.ViewHolder> implements Filterable {
    private Context mContext;
    ArrayList<Model_SanPhamKho> sanphamArrayList;
    ArrayList<Model_SanPhamKho> sanphamArrayList1;
    ArrayList<Model_SanPhamKho> filters;
    customFilter filter;
    Activity overridePendingTransition;
    public Adapter_SanPhamKho(Context mContext, ArrayList<Model_SanPhamKho> sanphamArrayList) {
        this.mContext = mContext;
        this.sanphamArrayList = sanphamArrayList;
        this.sanphamArrayList1 = sanphamArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.item_layoutlistsanpham,parent,false);
        ViewHolder viewHolder=new Adapter_SanPhamKho.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model_SanPhamKho model_kho=sanphamArrayList.get(position);
        // sét giá trị truyền vào
        holder.txtTenSanPham.setText(model_kho.getTenSanPham());
        holder.txtSoLuongSanPham.setText(model_kho.getSoLuongSanPham());
        holder.txtDonViTinh.setText(model_kho.getDonViTinh());
        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(model_kho.getGiaSanPham()+""));
        holder.txtGiaSanPham.setText(TongTienChuyenDoi+" VNĐ");
        holder.txtNhaCungCap.setText(model_kho.getNhaCungCap());
        holder.txtThuocTinh.setText(model_kho.getThuocTinh());
        // sự kiện onclick vào item recycal view
        holder.onClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Seo_ChiTietSanPham.class);
                intent.putExtra("MaSanPham",model_kho.getMaSanPham());
                intent.putExtra("TenSanPham",model_kho.getTenSanPham());
                intent.putExtra("SoLuongTon",model_kho.getSoLuongSanPham());
                intent.putExtra("DonViTinh",model_kho.getDonViTinh());
                mContext.startActivity(intent);


            }
        });
        Double SoLuong= Double.parseDouble(model_kho.getSoLuongSanPham());
        if(SoLuong>10){
            holder.txtTinhTrangSanPham.setText("Còn hàng");
            holder.txtTinhTrangSanPham.setTextColor(Color.GREEN);
        }else if(SoLuong<10){
                if(SoLuong<1){
                    holder.txtTinhTrangSanPham.setText("Hết hàng");
                    holder.txtTinhTrangSanPham.setTextColor(Color.RED);
                }else {
                    holder.txtTinhTrangSanPham.setText("Sắp hết");
                    holder.txtTinhTrangSanPham.setTextColor(Color.parseColor("#FFC107"));
                }
        }
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new customFilter();
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTenSanPham,txtSoLuongSanPham,txtDonViTinh,txtGiaSanPham,txtNhaCungCap,txtTinhTrangSanPham,txtThuocTinh;
        private LinearLayout onClickItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham=itemView.findViewById(R.id.txtTenSanPham);
            txtSoLuongSanPham=itemView.findViewById(R.id.txtSoLuongSanPham);
            txtDonViTinh=itemView.findViewById(R.id.txtDonViTinh);
            txtGiaSanPham=itemView.findViewById(R.id.txtGiaSanPham);
            txtNhaCungCap=itemView.findViewById(R.id.txtNhaCungCap);
            txtTinhTrangSanPham=itemView.findViewById(R.id.txtTinhTrangSanPham);
            txtThuocTinh=itemView.findViewById(R.id.txtThuocTinh);
            onClickItem=itemView.findViewById(R.id.onClickItem);
        }

    }

    class customFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null&&constraint.length()>0){
                constraint=constraint.toString().toUpperCase();
                filters=new ArrayList<>();
                for (int i=0;i<sanphamArrayList1.size();i++){
                    if((sanphamArrayList1.get(i).getTenSanPham().toUpperCase()).contains((String.valueOf(constraint)))){
                        Model_SanPhamKho p=sanphamArrayList1.get(i);
                        filters.add(p);
                    }
                }

                results.count=filters.size();
                results.values=filters;
            }else {
                results.count=sanphamArrayList1.size();
                results.values=sanphamArrayList1;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            sanphamArrayList= (ArrayList<Model_SanPhamKho>) results.values;
            notifyDataSetChanged();
        }
    }
}