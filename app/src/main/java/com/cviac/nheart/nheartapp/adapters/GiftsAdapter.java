package com.cviac.nheart.nheartapp.adapters;/*
package cviac.nheart.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cviac.nheart.R;
import cviac.nheart.datamodel.Gift;


*/
/**
 * Created by user on 11/23/2016.
 *//*


public class GiftsAdapter extends ArrayAdapter<Gift> {

    private List<Gift> gifts;
    Context mcontext;
    public GiftsAdapter(List<Gift> objects, Context context, List<Gift> gifts)
    {
        super(context, R.layout.activity_gifts, objects);

        this.gifts = gifts;
        mcontext=context;




    }

    public static class ViewHolder{

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vw=convertView;
  ViewHolder holder;
        Gift git=getItem(position);
        if(convertView==null)
        {
            LayoutInflater inf = LayoutInflater.from(getContext());
            vw = inf.inflate(R.layout.collegues_item, parent, false);
            holder = new ViewHolder();
            holder.nameView = (TextView) vw.findViewById(R.id.colleguesname);
            holder.mobile = (TextView) vw.findViewById(R.id.textemail);
            holder.empimage = (ImageView) vw.findViewById(R.id.empimage);
            // String
            // imageurl="http://www.gantrypark.com/Portals/12/Users/066/14/53314/adam-parker-large.jpg";
           */
/* Picasso.with(mContext).load(R.drawable.ic_launcher).resize(130, 130).transform(new CircleTransform())
                    .into(holder.empimage);
*//*

            holder.nameView.setText(git.getName());
            holder.mobile.setText(emp.getEmailID());
        }else
        {
            holder=(ViewHolder)vw.getTag();
        }
        return vw;



    }
}
*/
