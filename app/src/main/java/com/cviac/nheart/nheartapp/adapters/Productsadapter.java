package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.datamodel.Product;


import java.util.List;

public class Productsadapter extends BaseAdapter {

    Context ctx;
    LayoutInflater inflter;
    List<Product> list;

    public Productsadapter(Context ctx, List<Product> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder {
        public TextView tv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vw = convertView;
        Productsadapter.ViewHolder holder;
        Product ct = list.get(position);
        if(convertView==null)
        {
            inflter = LayoutInflater.from(ctx);
            convertView = inflter.inflate(R.layout.activity_productsadapter, null);
            holder=new Productsadapter.ViewHolder();
            vw = convertView;
            holder.tv=(TextView)vw.findViewById(R.id.textView2);
            vw.setTag(holder);
        }
        else {
            holder = (Productsadapter.ViewHolder) vw.getTag();
        }
        holder.tv.setText(ct.getName());
        return vw;
    }
}