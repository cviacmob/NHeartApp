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
import com.cviac.nheart.nheartapp.datamodel.ServiceInfo;

import java.util.List;

public class ServiceinfoAdapter extends BaseAdapter {

    private Context mContext;

    private List<ServiceInfo> list;

    private int layoutid;

    public ServiceinfoAdapter(Context c, int layoutid, List<ServiceInfo> list) {
        mContext = c;
        this.list=list;
        this.layoutid=layoutid;

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
        public TextView tv,tv2,price,price1;
        public ImageView iv;
    public RatingBar rating;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View ins = convertView;
        ViewHolder holder;
        ServiceInfo sinfo=list.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ins = new View(mContext);
            ins = inflater.inflate(layoutid, null);
            holder = new ViewHolder();
            holder.tv = (TextView) ins.findViewById(R.id.name);
            holder.tv2 = (TextView) ins.findViewById(R.id.t2);
            holder.price = (TextView) ins.findViewById(R.id.price);
            holder.price1 = (TextView) ins.findViewById(R.id.price1);
           holder.price1.setPaintFlags(holder.price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.iv = (ImageView) ins.findViewById(R.id.image1);
            holder.rating=(RatingBar)ins.findViewById(R.id.rating);
            holder.tv.setText(sinfo.getServiceNAME());
            holder.iv.setImageResource(sinfo.getImgID());
            holder.tv2.setText(sinfo.getDesc());
            holder.price.setText(sinfo.getPrice());
            holder.price1.setText(sinfo.getPrice1());



        } else {
            holder=(ViewHolder)ins.getTag();
        }
        return ins;
    }
}