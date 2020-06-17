package com.example.thanhhien.HoaDon;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.thanhhien.Api_custom;
import com.example.thanhhien.ChuyenDoiTongTien;
import com.example.thanhhien.R;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Adapter_HoaDon extends RecyclerView.Adapter<Adapter_HoaDon.ViewHolder> {
    private Context mContext;
    ArrayList<Model_HoaDon> listHoaDon;
    public Adapter_HoaDon(Context mContext, ArrayList<Model_HoaDon> listHoaDon) {
        this.mContext = mContext;
        this.listHoaDon = listHoaDon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.item_layoutchitiethoadon,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Model_HoaDon model_hoaDon=listHoaDon.get(position);
        holder.txtMaHoaDon.setText(model_hoaDon.MaHoaDon);
        holder.txtTenKhachHang.setText(model_hoaDon.TenKhachHang+"");
        holder.txtDiaChiKH.setText(model_hoaDon.getDiaChi());
        final  String NgayNhap= ChuyenDoiTongTien.formatDateTime(model_hoaDon.getNgayTao());
        holder.txtNgayTao.setText(NgayNhap);
        String TongTienChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(model_hoaDon.TongTien+""));
        holder.txtTongTien.setText(TongTienChuyenDoi+" VNĐ");
        holder.txtSoDienThoai.setText(model_hoaDon.getSoDienThoai());
        // sét tình trạng của hóa đơn
        if(model_hoaDon.getTinhTrang().equalsIgnoreCase("1")){
            holder.txtTinhTrangHoaDon.setText("Đang sử lý");
        }else {
            if(model_hoaDon.getTinhTrang().equalsIgnoreCase("0")){
                holder.txtTinhTrangHoaDon.setText("Đã lữu");
                holder.txtTinhTrangHoaDon.setBackgroundColor(Color.parseColor("#03A9F4"));
            }else {
                holder.txtTinhTrangHoaDon.setText("Hoàn thành");
                holder.txtTinhTrangHoaDon.setBackgroundColor(Color.GREEN);
            }
        }
        String TongTienNoChuyenDoi= ChuyenDoiTongTien.priceWithoutDecimal(Long.parseLong(model_hoaDon.getNo()+""));
        holder.txtTongTienConLai.setText(TongTienNoChuyenDoi+" VNĐ");
        holder.btnChiTietDonBanLe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Seo_ChiTietHoaDon.class);
                intent.putExtra("MaHoaDon",model_hoaDon.getMaHoaDon());
                intent.putExtra("TenKhachHang",model_hoaDon.getTenKhachHang());
                intent.putExtra("NgayTao",model_hoaDon.getNgayTao());
                intent.putExtra("NgayThanhToan",model_hoaDon.getNgayTao());
                intent.putExtra("TongTien",model_hoaDon.getTongTien());
                intent.putExtra("DaTra",model_hoaDon.getDaTra());
                intent.putExtra("TinhTrang",model_hoaDon.getTinhTrang());
                mContext.startActivity(intent);
                TastyToast.makeText(mContext,""+model_hoaDon.getTinhTrang(),TastyToast.LENGTH_SHORT,TastyToast.CONFUSING);
            }
        });
        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTenKhachHang,txtNgayTao,txtTongTien,txtMaHoaDon,txtDiaChiKH,txtSoDienThoai,txtTinhTrangHoaDon,txtTongTienConLai;
        private LinearLayout btnChiTietDonBanLe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKhachHang=itemView.findViewById(R.id.txtTenKhachHang);
            txtNgayTao=itemView.findViewById(R.id.txtNgayTao);
            txtTongTien=itemView.findViewById(R.id.txtTongTien);
            btnChiTietDonBanLe=itemView.findViewById(R.id.btnChiTietDonBanLe);
            txtMaHoaDon=itemView.findViewById(R.id.txtMaHoaDon);
            txtDiaChiKH=itemView.findViewById(R.id.txtDiaChiKH);
            txtSoDienThoai=itemView.findViewById(R.id.txtSoDienThoai);
            txtTinhTrangHoaDon=itemView.findViewById(R.id.txtTinhTrangHoaDon);
            txtTongTienConLai=itemView.findViewById(R.id.txtTongTienConLai);


        }

    }


}
