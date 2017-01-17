package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.Addressinfo;
import com.cviac.nheart.nheartapp.datamodel.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
     Context ctx;
    List<Addressinfo> addressList;
    LayoutInflater inflater;

    public

    AddressAdapter(Context ctx, List<Addressinfo> addressList) {
        this.ctx = ctx;
        this.addressList = addressList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return addressList.size();
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
        public TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
        ImageView iv;
        ImageView right;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View vw = convertView;
        AddressAdapter.ViewHolder holder;
        Addressinfo ct = addressList.get(position);
        if(convertView==null)
        {
            inflater = LayoutInflater.from(ctx);
            convertView = inflater.inflate(R.layout.activity_addressadapter, null);
            holder=new AddressAdapter.ViewHolder();
            vw = convertView;
            holder.tv=(TextView)vw.findViewById(R.id.fname);
            holder.tv1=(TextView)vw.findViewById(R.id.shippingno);
            holder.tv2=(TextView)vw.findViewById(R.id.acity);
            holder.tv3=(TextView)vw.findViewById(R.id.district);
            holder.tv4=(TextView)vw.findViewById(R.id.astae);
            holder.tv5=(TextView)vw.findViewById(R.id.apincode);
            holder.tv6=(TextView)vw.findViewById(R.id.amobileno);
            holder.tv7=(TextView)vw.findViewById(R.id.almobileno);
            holder.tv8=(TextView)vw.findViewById(R.id.alandmark);
           // vw = convertView;
            vw.setTag(holder);

        }
        else {
            holder = (AddressAdapter.ViewHolder) vw.getTag();
        }
        holder.tv.setText(ct.getFulname());
        holder.tv1.setText(ct.getShipping());
        holder.tv2.setText(ct.getCity());
        holder.tv3.setText(ct.getDistrict());
        holder.tv4.setText(ct.getState());
        holder.tv5.setText(ct.getPincode());
        holder.tv6.setText(ct.getMobile());
        holder.tv7.setText(ct.getAlternatemobile());
        holder.tv8.setText(ct.getLandmark());

        return vw;
    }
}



