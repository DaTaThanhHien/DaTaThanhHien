package com.example.thanhhien.BanHang.Seo_SanPhamDaChon;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Model_ListSanPhamBan;
import com.example.thanhhien.BanHang.Seo_BanHangLe.Seo_BanHangLe;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
/*
 * Người viết: Nguyễn Hữu Hai
 * Date: 2019/12/20
 * Cái này seo viết đó nha
 *
 * */
public class Adapter_SanPhamDaChon extends RecyclerView.Adapter<Adapter_SanPhamDaChon.ViewHolder> {
    private Context mContext;
    ArrayList<Model_SanPhamDaChon> sanphamArrayList;
    long SoTienNo=0;
    public Adapter_SanPhamDaChon(Context mContext, ArrayList<Model_SanPhamDaChon> sanphamArrayList) {
        this.mContext = mContext;
        this.sanphamArrayList = sanphamArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.item_layoutlistsanphamdachon,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Model_SanPhamDaChon model_kho=sanphamArrayList.get(position);
        // sét giá trị truyền vào
        holder.txtTenSanPham.setText(model_kho.getTenSanPham());
        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(model_kho.getDonGia()+""));
        holder.txtGiaSanPham.setText(TongTienChuyenDoi+" VNĐ");
        holder.txtSoLuongSanPham.setText(model_kho.getSoLuong());
        holder.txtDonViTinh.setText(model_kho.getDonViTinh());
        holder.txtNhaCungCap.setText(model_kho.getNhaCungCap());
        holder.txtThuocTinh.setText(model_kho.getThuocTinh());
        holder.txtSoLuongNhoLe.setText(model_kho.getSoLuongNhoLe());
        String TongTienNhoLeChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(model_kho.getSoTienNhoLe()+""));
        holder.txtGiaSanPhamNhoLe.setText(TongTienNhoLeChuyenDoi+" VNĐ");

        //<================================ sự kiện click tăng giảm số lượng sản phẩm ==========================================>
        // sự kiện xóa sản phẩm
        holder.imgdeletesanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận ?")
                        .setContentText("Bạn có muốn xóa sản phẩm!")
                        .setConfirmText("Xóa!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                sanphamArrayList.remove(model_kho);
                                notifyDataSetChanged();
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
        // sự kiện nhấn sửa đổi số lượng của bán lẻ

        holder.onClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventClickBanHang();
            }
        });
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTenSanPham,txtGiaSanPham,txtSoLuongSanPham,txtDonViTinh,txtThuocTinh,txtNhaCungCap,txtSoLuongNhoLe,txtGiaSanPhamNhoLe;
        private ImageView imgdeletesanpham;
        private LinearLayout onClickItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham=itemView.findViewById(R.id.txtTenSanPham);
            txtGiaSanPham=itemView.findViewById(R.id.txtGiaSanPham);
            txtSoLuongSanPham=itemView.findViewById(R.id.txtSoLuongSanPham);
            txtDonViTinh=itemView.findViewById(R.id.txtDonViTinh);
            txtThuocTinh=itemView.findViewById(R.id.txtThuocTinh);
            txtNhaCungCap=itemView.findViewById(R.id.txtNhaCungCap);
            imgdeletesanpham=itemView.findViewById(R.id.imgdeletesanpham);
            txtSoLuongNhoLe=itemView.findViewById(R.id.txtSoLuongNhoLe);
            txtGiaSanPhamNhoLe=itemView.findViewById(R.id.txtGiaSanPhamNhoLe);
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
    private void eventClickBanHang() {
        final LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_bottomlayoutsanphamdachon, null);
        ImageView btnclosedialog=view.findViewById(R.id.btnclosedialog);
        Button btnThanhToan=view.findViewById(R.id.btnThanhToan);
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

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventClickThanhToan();

            }
        });

    }

    private void eventClickThanhToan() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_layuotthanhtoanhoadon, null);
        ImageView btnclosedialog = view.findViewById(R.id.btnclosedialog);
        Button btnNhapHang = view.findViewById(R.id.btnNhapHang);
        final TextView txtTienThua = view.findViewById(R.id.txtTienThua);
        final TextView editDaTra = view.findViewById(R.id.editDaTra);
        TextView txtTongTien = view.findViewById(R.id.txtTongTien);
        btnNhapHang.setText("Thanh toán");


        editDaTra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editDaTra.getText().toString().trim().length() <= 0) {
                    editDaTra.setText("0");
                } else {
                    SoTienNo = 0;
                    String TongTienChuyenDoi = ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(SoTienNo + ""));
                    txtTienThua.setText(TongTienChuyenDoi);
                    if (SoTienNo <= 0) {
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
        mBottomSheetDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                Date today2 = new Date();
                final String NgayTao = dateFormatter2.format(today2);

                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Xác nhận thanh toán")
                        .setConfirmText("Xác nhận")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                mBottomSheetDialog.dismiss();
                                SoTienNo = 0;
                                if (editDaTra.getText().toString().equals("0")) {
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
