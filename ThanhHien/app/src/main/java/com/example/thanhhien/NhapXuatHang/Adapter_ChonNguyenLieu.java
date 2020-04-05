package com.example.thanhhien.NhapXuatHang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.thanhhien.MainActivity;

import java.util.ArrayList;
public class Adapter_ChonNguyenLieu extends BaseAdapter implements Filterable {
    private Context mContext;
    private int mLayout;
    private ArrayList<Model_ChonNguyenLieu> listNguyenLieuArrayList;
    private ArrayList<Model_ChonNguyenLieu> listNguyenLieuArrayList1;
    public static ArrayList<Model_ChonNguyenLieu> filters;
    customFilter filter;
    public Adapter_ChonNguyenLieu(Context mContext, int mLayout, ArrayList<Model_ChonNguyenLieu> listNguyenLieuArrayList) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.listNguyenLieuArrayList = listNguyenLieuArrayList;
        this.listNguyenLieuArrayList1 = listNguyenLieuArrayList;
    }

    @Override
    public int getCount() {
        return listNguyenLieuArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new customFilter();
        }
        return filter;
    }

    public class ViewHolder{
        private CheckedTextView txtTenNguyenLieu;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mLayout,null);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Model_ChonNguyenLieu Model_ChonNguyenLieu=listNguyenLieuArrayList.get(position);
        viewHolder.txtTenNguyenLieu.setText(Model_ChonNguyenLieu.getTenSanPham()+"");

        int i=0;
        viewHolder.txtTenNguyenLieu.setChecked(false);
        while (i< MainActivity.listSanPhamDaChon.size()){
            if(MainActivity.listSanPhamDaChon.get(i).getTenSanPham().equalsIgnoreCase(listNguyenLieuArrayList1.get(position).getTenSanPham())){
                viewHolder.txtTenNguyenLieu.setChecked(true);
                break;
            }
            i++;
        }
        viewHolder.txtTenNguyenLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// sự kiên khi click vào sản phẩm sẽ thêm vào list giống giở hàng
                if (viewHolder.txtTenNguyenLieu.isChecked()) {
                    viewHolder.txtTenNguyenLieu.setChecked(false);
                    int i=0;
                    while (i<MainActivity.listSanPhamDaChon.size()){
                        if(MainActivity.listSanPhamDaChon.get(i).getTenSanPham().equalsIgnoreCase(listNguyenLieuArrayList.get(position).getTenSanPham())){
                            MainActivity.listSanPhamDaChon.remove(i);
                            break;
                        }
                        i++;
                    }

                } else {// sự kiện khi bỏ list
                    viewHolder.txtTenNguyenLieu.setChecked(true);
                    MainActivity.listSanPhamDaChon.add(listNguyenLieuArrayList.get(position));
                }
            }
        });
        return convertView;
    }
    class customFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null&&constraint.length()>0){
                constraint=constraint.toString().toUpperCase();
                filters=new ArrayList<>();
                for (int i=0;i<listNguyenLieuArrayList1.size();i++){
                    if((listNguyenLieuArrayList1.get(i).getTenSanPham().toUpperCase()).contains((String.valueOf(constraint)))){
                        Model_ChonNguyenLieu p=listNguyenLieuArrayList1.get(i);
                        filters.add(p);
                    }
                }

                results.count=filters.size();
                results.values=filters;
            }else {
                results.count=listNguyenLieuArrayList1.size();
                results.values=listNguyenLieuArrayList1;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            listNguyenLieuArrayList= (ArrayList<Model_ChonNguyenLieu>) results.values;
            notifyDataSetChanged();
        }
    }
}
