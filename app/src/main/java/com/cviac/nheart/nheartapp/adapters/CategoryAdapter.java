package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.activities.ProductdetailsActivity;
import com.cviac.nheart.nheartapp.datamodel.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin1 on 11/24/2016.
 */

public class CategoryAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater inflter;
    List<Category> list;

    public CategoryAdapter(Context ctx, List<Category> list) {
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
        ImageView iv;
        ImageView right;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vw = convertView;
        ViewHolder holder;
        Category ct = list.get(position);
        if(convertView==null)
        {
            inflter = LayoutInflater.from(ctx);
            convertView = inflter.inflate(R.layout.categoryadapter, null);
            holder=new ViewHolder();
            vw = convertView;
            holder.tv=(TextView)vw.findViewById(R.id.textView);
            holder.iv=(ImageView)vw.findViewById(R.id.catimg);
            holder.right=(ImageView)vw.findViewById(R.id.right);
            vw.setTag(holder);

//            String url="http://nheart.cviac.com//image//cache//catalog//ring22-500x500.jpg";
//            Picasso.with(vw.getContext()).load(url).resize(50,50).into( holder.iv);
        }
        else {
            holder = (ViewHolder) vw.getTag();
        }
        holder.tv.setText(ct.getName());
        String url = ct.getImage();
        url = url.replace("localhost","192.168.1.133");
        try {
            Picasso.with(vw.getContext()).load(url).resize(50,50).into( holder.iv);
//            holder.imgView.setImageURI(Uri.parse(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vw;
    }
}
