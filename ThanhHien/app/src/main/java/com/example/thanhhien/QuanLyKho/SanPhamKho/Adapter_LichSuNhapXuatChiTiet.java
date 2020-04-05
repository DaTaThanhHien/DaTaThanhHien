package com.example.thanhhien.QuanLyKho.SanPhamKho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thanhhien.R;

import java.util.ArrayList;

public class Adapter_LichSuNhapXuatChiTiet extends BaseAdapter {
    private Context mContext;
    private int mLayout;
    private ArrayList<Model_LichSuNhapXuatChiTiet> chiTietNhapXuatArrayList;


    public Adapter_LichSuNhapXuatChiTiet(Context mContext, int mLayout, ArrayList<Model_LichSuNhapXuatChiTiet> chiTietNhapXuatArrayList) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.chiTietNhapXuatArrayList = chiTietNhapXuatArrayList;
    }

    @Override
    public int getCount() {
        return chiTietNhapXuatArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        private TextView txtTenSanPham,txtSoLuong,txtDonViTinh,txtNgayNhapXuat;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mLayout,null);


            viewHolder.txtTenSanPham=convertView.findViewById(R.id.txtTenSanPham);
            viewHolder.txtDonViTinh=convertView.findViewById(R.id.txtDonViTinh);
            viewHolder.txtSoLuong=convertView.findViewById(R.id.txtSoLuong);
            viewHolder.txtNgayNhapXuat=convertView.findViewById(R.id.txtNgayNhapXuat);



            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Model_LichSuNhapXuatChiTiet model_lichSuNhapXuatChiTiet=chiTietNhapXuatArrayList.get(position);
            viewHolder.txtTenSanPham.setText(model_lichSuNhapXuatChiTiet.getTenSanPham());
            viewHolder.txtSoLuong.setText(model_lichSuNhapXuatChiTiet.getSoLuong());
            viewHolder.txtDonViTinh.setText(model_lichSuNhapXuatChiTiet.getDonViTinh());
            viewHolder.txtNgayNhapXuat.setText(model_lichSuNhapXuatChiTiet.getNgayNhapXuat());
        return convertView;
    }
}
