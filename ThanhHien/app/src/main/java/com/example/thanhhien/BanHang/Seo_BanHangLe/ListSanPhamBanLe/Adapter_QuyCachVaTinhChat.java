package com.example.thanhhien.BanHang.Seo_BanHangLe.ListSanPhamBanLe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thanhhien.R;

import java.util.ArrayList;

public class Adapter_QuyCachVaTinhChat  extends BaseAdapter {
    private Context mContext;
    private int mLayout;
    private ArrayList<Model_QuyCachVaTinhChat> mListQuyCachVaTinhChat;
    private ArrayList<Model_QuyCachVaTinhChat>mListQuyCach;
    private Adapter_QuyCachVaTinhChat adapter_quyCachVaTinhChat;

    public Adapter_QuyCachVaTinhChat(Context mContext, int mLayout, ArrayList<Model_QuyCachVaTinhChat> mListQuyCachVaTinhChat) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListQuyCachVaTinhChat = mListQuyCachVaTinhChat;
    }

    @Override
    public int getCount() {
        return mListQuyCachVaTinhChat.size();
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
        private TextView txtTenQuyCachVaTinhChat;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mLayout,null);
            viewHolder.txtTenQuyCachVaTinhChat=convertView.findViewById(R.id.txtTenQuyCachVaTinhChat);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        final Model_QuyCachVaTinhChat model_quyCachVaTinhChat=mListQuyCachVaTinhChat.get(position);
        viewHolder.txtTenQuyCachVaTinhChat.setText(model_quyCachVaTinhChat.getTenQuyCachVaTT());


        return convertView;
    }

}
