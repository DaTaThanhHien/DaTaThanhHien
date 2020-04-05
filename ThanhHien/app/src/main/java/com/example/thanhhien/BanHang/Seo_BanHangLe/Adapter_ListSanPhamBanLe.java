package com.example.thanhhien.BanHang.Seo_BanHangLe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe.Seo_ListSanPhamBanLe;
import com.example.thanhhien.R;

import java.util.ArrayList;

public class Adapter_ListSanPhamBanLe extends BaseAdapter {
    private Context mContext;
    private int mLayout;
    private ArrayList<Model_ListSanPhamBanLe> mListSanPhamBanLe;

    public Adapter_ListSanPhamBanLe(Context mContext, int mLayout, ArrayList<Model_ListSanPhamBanLe> mListSanPhamBanLe) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListSanPhamBanLe = mListSanPhamBanLe;
    }

    @Override
    public int getCount() {
        return mListSanPhamBanLe.size();
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
        TextView btnTenSanPham;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mLayout,null);
            viewHolder.btnTenSanPham=convertView.findViewById(R.id.btnTenSanPham);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Model_ListSanPhamBanLe model_listSanPhamBanLe=mListSanPhamBanLe.get(position);
        viewHolder.btnTenSanPham.setText(model_listSanPhamBanLe.getTenSanPham());

        return convertView;
    }
}
