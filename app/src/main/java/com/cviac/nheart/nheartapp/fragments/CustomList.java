package com.cviac.nheart.nheartapp.fragments;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;

/**
 * Created by user on 12/19/2016.
 */
public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] items;


    public CustomList(Activity context,
                      String[] items, Integer[] imageId) {
        super(context, R.layout.list_single, items);
        this.context = context;
        this.items = items;


    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.titlehead);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.texduration);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(items[position]);

        //TextView title=(TextView) view.findViewById(R.id.playtitle);
        //title.setText(items[position]);
        return rowView;
    }
}
