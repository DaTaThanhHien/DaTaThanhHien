package com.example.thanhhien.QuanLyKho.ThemSuaXoaSanPham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thanhhien.R;

import java.util.ArrayList;
public class Adapter_ThuocTinhSanPham extends BaseAdapter {
    private Context mContext;
    private int mLayout;
    private ArrayList<Model_ListThuocTinhSanPham> mListThuocTinh;

    public Adapter_ThuocTinhSanPham(Context mContext, int mLayout, ArrayList<Model_ListThuocTinhSanPham> mListThuocTinh) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListThuocTinh = mListThuocTinh;
    }

    @Override
    public int getCount() {
        return mListThuocTinh.size();
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
        TextView txtTen;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mLayout,null);
            viewHolder.txtTen=convertView.findViewById(R.id.txtTen);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Model_ListThuocTinhSanPham model_listThuocTinh=mListThuocTinh.get(position);
        viewHolder.txtTen.setText(model_listThuocTinh.getTenSanPham());

        return convertView;
    }
}