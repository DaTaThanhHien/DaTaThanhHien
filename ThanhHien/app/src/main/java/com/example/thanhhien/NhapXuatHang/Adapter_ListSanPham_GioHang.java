package com.example.thanhhien.NhapXuatHang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Adapter_QuyCachVaTinhChat;
import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;

import java.util.ArrayList;

public class Adapter_ListSanPham_GioHang extends RecyclerView.Adapter<Adapter_ListSanPham_GioHang.ViewHolder> {
    private Context mContext;
    ArrayList<Model_ListSanPhamBan> sanphamArrayList;
    private Adapter_QuyCachVaTinhChat adapter_quyCachVaTinhChat;
    long SoTienNo=0;
    public Adapter_ListSanPham_GioHang(Context mContext, ArrayList<Model_ListSanPhamBan> sanphamArrayList) {
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
        holder.ivGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.txtSoLuongSanPham.getText().toString().trim())>1){
                    holder.txtSoLuongSanPham.setText((Integer.parseInt( holder.txtSoLuongSanPham.getText().toString().trim())-1)+"");
                    Seo_ChonNhaCungCap.gioHang.set(position,new Model_ListSanPhamBan(Seo_ChonNhaCungCap.gioHang.get(position).getMaSanPham(),Seo_ChonNhaCungCap.gioHang.get(position).getTenSanPham(),(Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(position).getSoLuong())-1)+"",Seo_ChonNhaCungCap.gioHang.get(position).getGiaSanPham(),Seo_ChonNhaCungCap.gioHang.get(position).getDonViTinh(),Seo_ChonNhaCungCap.gioHang.get(position).getThuocTinh(),Seo_ChonNhaCungCap.gioHang.get(position).getQuyCach()));
                }
            }
        });
        holder.ivTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtSoLuongSanPham.setText((Integer.parseInt( holder.txtSoLuongSanPham.getText().toString().trim())+1)+"");
                Seo_ChonNhaCungCap.gioHang.set(position,new Model_ListSanPhamBan(Seo_ChonNhaCungCap.gioHang.get(position).getMaSanPham(),Seo_ChonNhaCungCap.gioHang.get(position).getTenSanPham(),(Double.parseDouble(Seo_ChonNhaCungCap.gioHang.get(position).getSoLuong())+1)+"",Seo_ChonNhaCungCap.gioHang.get(position).getGiaSanPham(),Seo_ChonNhaCungCap.gioHang.get(position).getDonViTinh(),Seo_ChonNhaCungCap.gioHang.get(position).getThuocTinh(),Seo_ChonNhaCungCap.gioHang.get(position).getQuyCach()));
            }
        });
        // sự kiện onclick vào item recycal view
        holder.onClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventClickBanHang(sanphamArrayList.get(position).getTenSanPham(),sanphamArrayList.get(position).getQuyCach(),sanphamArrayList.get(position).getThuocTinh(),sanphamArrayList.get(position).getGiaSanPham(),position);

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
        private ImageView ivGiam,ivTang;

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
            ivGiam=itemView.findViewById(R.id.ivGiam);
            ivTang=itemView.findViewById(R.id.ivTang);

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
    private void eventClickBanHang(String TenSP, String QuyCach, String TrongLuong, String GiaNhap, final int position) {
        final LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layoutnhapsanpham, null);
        TextView txtTenSP=view.findViewById(R.id.txtTenSP);
        TextView txtQuyCach=view.findViewById(R.id.txtQuyCach);
        TextView txtTrongLuong=view.findViewById(R.id.txtTrongLuong);
        ImageView ibTang=view.findViewById(R.id.ibTang);
        ImageView ibGiam=view.findViewById(R.id.ibGiam);
        final EditText edit_SoLuong=view.findViewById(R.id.edit_SoLuong);
        txtTenSP.setText(TenSP);
        txtTrongLuong.setText(TrongLuong);
        txtQuyCach.setText(QuyCach);
        ibTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_SoLuong.setText((Integer.parseInt(edit_SoLuong.getText().toString().trim())+1)+"");
            }
        });
        ibGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(edit_SoLuong.getText().toString().trim())>1){
                    edit_SoLuong.setText((Integer.parseInt(edit_SoLuong.getText().toString().trim())+1)+"");
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
        btnclosedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();
                if(Seo_ChonNhaCungCap.gioHang.size()==0){
                    Seo_ChonNhaCungCap.gioHang.add(new Model_ListSanPhamBan(sanphamArrayList.get(position).getMaSanPham(),sanphamArrayList.get(position).getTenSanPham(),(Integer.parseInt(edit_SoLuong.getText().toString()))+"",sanphamArrayList.get(position).getGiaSanPham(),sanphamArrayList.get(position).getDonViTinh(),sanphamArrayList.get(position).getThuocTinh(),sanphamArrayList.get(position).getQuyCach()));
                    Seo_ListSanPhamNhapKho.tongTien=Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(sanphamArrayList.get(position).getGiaSanPham());
                    Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                }else{
                    for (int i=0;i<Seo_ChonNhaCungCap.gioHang.size();i++){
                        if(i==(Seo_ChonNhaCungCap.gioHang.size()-1)){
                            if(sanphamArrayList.get(position).getMaSanPham().equalsIgnoreCase((Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham()))){
                                Seo_ChonNhaCungCap.gioHang.set(i,new Model_ListSanPhamBan(Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getTenSanPham(),(Integer.parseInt(Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong())+Integer.parseInt(edit_SoLuong.getText().toString()))+"",Seo_ChonNhaCungCap.gioHang.get(i).getGiaSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getDonViTinh(),Seo_ChonNhaCungCap.gioHang.get(i).getThuocTinh(),Seo_ChonNhaCungCap.gioHang.get(i).getQuyCach()));
                                Seo_ListSanPhamNhapKho.tongTien=Seo_ListSanPhamNhapKho.tongTien+(Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(sanphamArrayList.get(position).getGiaSanPham()));
                                Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                                i=i+Seo_ChonNhaCungCap.gioHang.size()+10;
                            }else{
                                Seo_ChonNhaCungCap.gioHang.add(new Model_ListSanPhamBan(sanphamArrayList.get(position).getMaSanPham(),sanphamArrayList.get(position).getTenSanPham(),(Integer.parseInt(edit_SoLuong.getText().toString()))+"",sanphamArrayList.get(position).getGiaSanPham(),sanphamArrayList.get(position).getDonViTinh(),sanphamArrayList.get(position).getThuocTinh(),sanphamArrayList.get(position).getQuyCach()));
                                Seo_ListSanPhamNhapKho.tongTien=Seo_ListSanPhamNhapKho.tongTien+(Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(sanphamArrayList.get(position).getGiaSanPham()));
                                Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                                i=i+Seo_ChonNhaCungCap.gioHang.size()+10;
                            }
                        }else{
                            if(sanphamArrayList.get(position).getMaSanPham().equalsIgnoreCase((Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham()))){
                                Seo_ChonNhaCungCap.gioHang.set(i,new Model_ListSanPhamBan(Seo_ChonNhaCungCap.gioHang.get(i).getMaSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getTenSanPham(),(Integer.parseInt(Seo_ChonNhaCungCap.gioHang.get(i).getSoLuong())+Integer.parseInt(edit_SoLuong.getText().toString()))+"",Seo_ChonNhaCungCap.gioHang.get(i).getGiaSanPham(),Seo_ChonNhaCungCap.gioHang.get(i).getDonViTinh(),Seo_ChonNhaCungCap.gioHang.get(i).getThuocTinh(),Seo_ChonNhaCungCap.gioHang.get(i).getQuyCach()));
                                Seo_ListSanPhamNhapKho.tongTien=Seo_ListSanPhamNhapKho.tongTien+(Double.parseDouble(edit_SoLuong.getText().toString())*Double.parseDouble(sanphamArrayList.get(position).getGiaSanPham()));
                                Seo_ListSanPhamNhapKho.btnThanhToan.setText("Thanh Toán: "+ChuyenDoiTongTien.priceWithoutDecimal(Seo_ListSanPhamNhapKho.tongTien)+" VNĐ");
                                i=i+Seo_ChonNhaCungCap.gioHang.size()+10;
                            }
                        }
                    }
                }
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Seo_ThanhToanNhapHang.class);
                mContext.startActivity(intent);
            }
        });

    }
}

