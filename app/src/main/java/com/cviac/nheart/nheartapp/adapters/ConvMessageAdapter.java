package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.ConvMessage;

import java.util.List;

/**
 * Created by Administrator on 2/7/2017.
 */

public class ConvMessageAdapter extends ArrayAdapter<ConvMessage> {
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;
    public ConvMessageAdapter(List<ConvMessage> objects, Context context) {
        super(context, R.layout.chat_mine, objects);
    }
    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 2;
    }
  /*  @Override
    public int getItemViewType(int position) {
        ConvMessage item = getItem(position);
        if (item.ismine() && !item.ismine()) return MY_MESSAGE;
        else  return OTHER_MESSAGE;

    }*/
    public static class ViewHolder {
        public TextView msgview,txt;
        public ImageView statusview;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        View vww = convertView;
        ViewHolder holder = null;
        if (viewType == MY_MESSAGE) {
            vww = LayoutInflater.from(getContext()).inflate(R.layout.chat_mine, parent, false);
            holder.msgview = (TextView) convertView.findViewById(R.id.textchatmsg);
            holder.txt = (TextView) convertView.findViewById(R.id.duration);
            holder.statusview = (ImageView) convertView.findViewById(R.id.list_image);
            holder.msgview.setText(getItem(position).getMsg());
        } else if (viewType == OTHER_MESSAGE) {
            vww   = LayoutInflater.from(getContext()).inflate(R.layout.chat_others, parent, false);
            holder.msgview= (TextView) convertView.findViewById(R.id.text);
            holder.msgview.setText(getItem(position).getMsg());
        }
        convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

}
