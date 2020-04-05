package com.example.thanhhien.NhapXuatHang;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.thanhhien.NhaCungcap.Model_NhaCungCap;
import com.example.thanhhien.R;

import java.util.ArrayList;

public class Adapter_NhaCungCap extends BaseAdapter {
    private Context context;
    private ArrayList<Model_NhaCungCap> listNhaCungCap;

    public Adapter_NhaCungCap(Context context, ArrayList<Model_NhaCungCap> listNhaCungCap) {
        this.context = context;
        this.listNhaCungCap = listNhaCungCap;
    }

    @Override
    public int getCount() {
        return listNhaCungCap.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHoder{
        TextView txtNhaCungCap;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHoder holder;
        if(view==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.item_layoutnhacungcap,null);
            holder =new ViewHoder();
            holder.txtNhaCungCap=view.findViewById(R.id.btnTenSanPham);
            view.setTag(holder);

        }else {
            holder= (ViewHoder) view.getTag();
        }

            holder.txtNhaCungCap.setText(listNhaCungCap.get(position).getTenNhaCungCap());

        return view;
    }
}

