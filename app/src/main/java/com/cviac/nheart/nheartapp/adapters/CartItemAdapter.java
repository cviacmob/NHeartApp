package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.CartItemInfo;
import com.cviac.nheart.nheartapp.datamodel.ProductCartInfo;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends BaseAdapter {
    int count;
    private static int counter = 0;
    private String stringVal;
    private Context mContext;

    private List<ProductCartInfo> list=new ArrayList<>();

    private int layoutid;
    private static Integer deletecounter= 0;

    public CartItemAdapter(Context c, int layoutid, List<ProductCartInfo> list) {
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

        //public RatingBar rating;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View ins = convertView;
        ViewHolder holder;
        ProductCartInfo sinfo = list.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ins = inflater.inflate(layoutid, null);
            holder = new ViewHolder();
            holder.tv = (TextView) ins.findViewById(R.id.name);
            holder.iv = (ImageView) ins.findViewById(R.id.ima1);
            final TextView tv2 = (TextView)ins.findViewById(R.id.items);
            holder.price = (TextView) ins.findViewById(R.id.price);
            ImageButton txtplus  = (ImageButton) ins.findViewById(R.id.add);
            txtplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("src", "Increasing value...");
                    counter++;
                    stringVal = Integer.toString(counter);
                    tv2.setText(stringVal);
                }
            });
            ImageButton txtminus = (ImageButton) ins.findViewById(R.id.remove);
            txtminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("src", "Decreasing value...");
                    counter--;
                    stringVal = Integer.toString(counter);
                    tv2.setText(stringVal);
                    if (counter < 0)
                    {
                        tv2.setText("0");
                    }
                }
            });

            ins.setTag(holder);
        } else {
            holder = (ViewHolder) ins.getTag();
        }
        holder.price.setText(sinfo.getPrice());
        holder.tv.setText(sinfo.getName());
       // holder.iv.setImageResource(sinfo.g);


        return ins;
    }
}