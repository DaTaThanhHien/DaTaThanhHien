package com.example.thanhhien.LichSuNhapXuat;

import android.content.Context;
import android.graphics.Color;
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

public class Adapter_LichSuNhapXuat extends RecyclerView.Adapter<Adapter_LichSuNhapXuat.ViewHolder> {
        private Context mContext;
        ArrayList<Model_LichSuNhapXuat> listLichSuNhapXuat;

        public Adapter_LichSuNhapXuat(Context mContext, ArrayList<Model_LichSuNhapXuat> listLichSuNhapXuat) {
            this.mContext = mContext;
            this.listLichSuNhapXuat = listLichSuNhapXuat;
        }

        @NonNull
        @Override
        public Adapter_LichSuNhapXuat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(mContext);
            View view= layoutInflater.inflate(R.layout.item_layoutlistnhapxuat,parent,false);
            Adapter_LichSuNhapXuat.ViewHolder viewHolder=new Adapter_LichSuNhapXuat.ViewHolder(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final Model_LichSuNhapXuat model_lichSuNhapXuat=listLichSuNhapXuat.get(position);
            holder.txtMaPhieuNhap.setText(model_lichSuNhapXuat.getIDPhieuNhap());
            holder.txtNhaCungCap.setText(model_lichSuNhapXuat.getTenNhaCungCap());
            final  String NgayTaoDon= ChuyenDoiTongTien.formatDateTime(model_lichSuNhapXuat.getNgayTao());
            holder.txtNgayTaoPhieu.setText(NgayTaoDon);
            final  String NgayNhap= ChuyenDoiTongTien.formatDateTime(model_lichSuNhapXuat.getNgayNhap());
            holder.txtNgayNhap.setText(NgayNhap);
            final String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(model_lichSuNhapXuat.getTongTien()+""));
            holder.txtTongTien.setText(TongTienChuyenDoi+ " VNĐ");
            final String TongTienDaTraChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Double.parseDouble(model_lichSuNhapXuat.getTongTienDaTra()+""));
            holder.txtTongTienDaTra.setText(TongTienDaTraChuyenDoi+ " VNĐ");
            if(model_lichSuNhapXuat.getTinhTrang().equals("1")){
                holder.txtTinhTrangPhieuNhap.setText("Đang sử lý");
            }else if(model_lichSuNhapXuat.getTinhTrang().equals("2")) {
                holder.txtTinhTrangPhieuNhap.setText("Hoàn thành");
                holder.txtTinhTrangPhieuNhap.setBackgroundColor(Color.parseColor("#4CAF50"));
            }
            holder.onClickItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClick(model_lichSuNhapXuat.getIDPhieuNhap(),model_lichSuNhapXuat.getTenNhaCungCap(),NgayNhap,NgayTaoDon,TongTienChuyenDoi,TongTienDaTraChuyenDoi,model_lichSuNhapXuat.getTinhTrang());
                    }
                }
            });

            holder.setIsRecyclable(false);
        }

        @Override
        public int getItemCount() {
            return listLichSuNhapXuat.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtMaPhieuNhap,txtNhaCungCap,txtNgayTaoPhieu,txtNgayNhap,txtTongTien,txtTongTienDaTra,txtTinhTrangPhieuNhap;
            private LinearLayout onClickItem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtMaPhieuNhap=itemView.findViewById(R.id.txtMaPhieuNhap);
                txtNhaCungCap=itemView.findViewById(R.id.txtNhaCungCap);
                txtNgayTaoPhieu=itemView.findViewById(R.id.txtNgayTaoPhieu);
                txtNgayNhap=itemView.findViewById(R.id.txtNgayNhap);
                txtTongTien=itemView.findViewById(R.id.txtTongTien);
                txtTongTienDaTra=itemView.findViewById(R.id.txtTongTienDaTra);
                txtTinhTrangPhieuNhap=itemView.findViewById(R.id.txtTinhTrangPhieuNhap);
                onClickItem=itemView.findViewById(R.id.onClickItem);

            }

        }


    public interface OnItemClickedListener {
        void onItemClick(String MaPhieuNhap,String NhaCungCap,String NgayTaoPhieu,String NgayNhap,String TongTien,String TongTienDaTra,String TinhTrang);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

}
