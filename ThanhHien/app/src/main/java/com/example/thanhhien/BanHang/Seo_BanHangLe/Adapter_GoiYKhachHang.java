package com.example.thanhhien.BanHang.Seo_BanHangLe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thanhhien.R;

import java.util.ArrayList;
import java.util.List;
/*
 * Người viết: Nguyễn Hữu Hai,Nguyễn Văn Thành
 * Date: 2019/12/20
 * Cái này seo viết đó nha
 *
 * */
public class Adapter_GoiYKhachHang extends ArrayAdapter<Model_GoiYKhachHang> {
    private Context context;
    private int resourceId;
    private List<Model_GoiYKhachHang> items, tempItems, suggestions;
    public Adapter_GoiYKhachHang(@NonNull Context context, int resourceId, ArrayList<Model_GoiYKhachHang> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            Model_GoiYKhachHang fruit = getItem(position);
            TextView TenKh = (TextView) view.findViewById(R.id.txtTenKhachHang);
            TextView SoDienThoaiKh = (TextView) view.findViewById(R.id.txtSoDienThoai);
            TenKh.setText(fruit.getTenKhachHang());
            SoDienThoaiKh.setText(fruit.getSoDienThoai());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public Model_GoiYKhachHang getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return fruitFilter;
    }
    private Filter fruitFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Model_GoiYKhachHang fruit = (Model_GoiYKhachHang) resultValue;
            return fruit.getSoDienThoai();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Model_GoiYKhachHang fruit: tempItems) {
                    if (fruit.getSoDienThoai().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(fruit);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Model_GoiYKhachHang> tempValues = (ArrayList<Model_GoiYKhachHang>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Model_GoiYKhachHang fruitObj : tempValues) {
                    add(fruitObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
