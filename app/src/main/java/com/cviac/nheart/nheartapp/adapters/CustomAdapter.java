package com.cviac.nheart.nheartapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;

public class CustomAdapter extends PagerAdapter{

    Context context;
    int[] imageId = {R.drawable.one, R.drawable.twoo, R.drawable.three, R.mipmap.ffour, R.mipmap.ffive};

    public CustomAdapter(Context context){
        this.context = context;

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.activity_fullscreen, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.i1);
        imageView.setImageResource(imageId[position]);
        //TextView textView1 = (TextView) viewItem.findViewById(R.id.textView1);
        //textView1.setText("hi");
        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub

        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }

}