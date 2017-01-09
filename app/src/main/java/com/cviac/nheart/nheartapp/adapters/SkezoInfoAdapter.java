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

import com.cviac.nheart.nheartapp.datamodel.SkezoInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 1/3/2017.
 */

public class SkezoInfoAdapter  extends BaseAdapter {

    Context ctx;
    LayoutInflater inflter;
    List<SkezoInfo> list;

    public SkezoInfoAdapter(Context ctx, List<SkezoInfo> list) {
        this.ctx = ctx;
        this.list = list;
    }




    public static class ViewHolder {
        TextView title22;
        ImageView img;
        TextView description;
        TextView date;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vw = convertView;
            ViewHolder holder;
        SkezoInfo ct = list.get(position);
        if (convertView == null) {
            inflter = LayoutInflater.from(ctx);
            convertView = inflter.inflate(R.layout.skezo_list, null);
            holder = new ViewHolder();
            vw = convertView;
            holder.title22 = (TextView) vw.findViewById(R.id.title_skezo);

            holder.description = (TextView) vw.findViewById(R.id.description);
            holder.date=(TextView)vw.findViewById(R.id.date);
            holder.img = (ImageView) vw.findViewById(R.id.imgcircle);
            vw.setTag(holder);

        } else {
            holder = (ViewHolder) vw.getTag();
        }

        holder.description.setText(ct.getDescription());
        holder.title22.setText(ct.getTitle());
        holder.date.setText(ct.getDate());
     int uri=ct.getImgUrl();
        Picasso.with(vw.getContext()).load(uri).resize(50, 50).transform(new CircleTransform())
                .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.img);
        //Picasso.with(vw.getContext()).load(Uri.parse("file://" + ct.getImgUrl())).resize(50, 50).into(holder.img);


        return vw;
    }



}
