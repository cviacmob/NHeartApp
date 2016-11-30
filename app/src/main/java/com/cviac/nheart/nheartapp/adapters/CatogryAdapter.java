package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.CatogryInfo;

import java.util.ArrayList;
import java.util.List;

public class CatogryAdapter extends BaseAdapter {

    private Context mContext;

    private List<CatogryInfo> list=new ArrayList<>();

    private int layoutid;

    public CatogryAdapter(Context c, int layoutid, List<CatogryInfo> list) {
        mContext = c;
        this.list = list;
        this.layoutid = layoutid;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView tv, tv2, price, price1;
        public ImageView iv, iv2;
        public RatingBar rating;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View ins = convertView;
        ViewHolder holder;
        CatogryInfo sinfo = list.get(position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ins = inflater.inflate(layoutid, null);
            holder = new ViewHolder();

            holder.tv = (TextView) ins.findViewById(R.id.tex1);
            holder.iv = (ImageView) ins.findViewById(R.id.ima1);
            holder.iv2 = (ImageView) ins.findViewById(R.id.ima4);

            ins.setTag(holder);
        } else {
            holder = (ViewHolder) ins.getTag();
        }
        holder.tv.setText(sinfo.getCatogryname());
        holder.iv.setImageResource(sinfo.getCatogryimg());
        holder.iv2.setImageResource(sinfo.getCatogryimg1());
        return ins;
    }
}