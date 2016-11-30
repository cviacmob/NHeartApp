package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.datamodel.Product;
import com.squareup.picasso.Picasso;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
        public ImageView imgView;
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
            holder.tv=(TextView)vw.findViewById(R.id.tex1);
            holder.imgView = (ImageView) vw.findViewById(R.id.imageView);
            vw.setTag(holder);
        }
        else {
            holder = (Productsadapter.ViewHolder) vw.getTag();
        }
        holder.tv.setText(ct.getName());
        String url = ct.getThumb();
        url = url.replace("localhost","192.168.1.133");
        try {
            Picasso.with(vw.getContext()).load(url).resize(100,100).into( holder.imgView);
//            holder.imgView.setImageURI(Uri.parse(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vw;
    }
}