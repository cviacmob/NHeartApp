package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.ConvMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConvMessageAdapter extends ArrayAdapter<ConvMessage> {

    private List<ConvMessage> chats;
    public ArrayAdapter adapter;
    private String myempId;
    private int lastPostion = -1;

    Context mContext;

    public ConvMessageAdapter(List<ConvMessage> objects, Context context) {
        super(context, R.layout.fragment_chat, objects);
        chats = objects;
        mContext = context;
    }

    public static class ViewHolder {
        public TextView msgview,txt;
        public ImageView statusview;
        public RelativeLayout rLayout;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vw = convertView;
        ViewHolder holder;
        ConvMessage chat = getItem(position);
        if (convertView == null) {
            LayoutInflater inf = LayoutInflater.from(getContext());
            vw = inf.inflate(R.layout.fragment_chat, parent, false);
            holder = new ViewHolder();
            holder.msgview = (TextView) vw.findViewById(R.id.textchatmsg);
            holder.txt = (TextView) vw.findViewById(R.id.duration);
            holder.statusview= (ImageView)vw.findViewById(R.id.list_image) ;
            holder.rLayout = (RelativeLayout) vw.findViewById(R.id.textchat);
            vw.setTag(holder);
        } else {
            holder = (ViewHolder) vw.getTag();
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        if (chat.isMine() == true) {
            holder.statusview.setVisibility(View.VISIBLE);
            Resources res = mContext.getResources();
            Drawable drawable = res.getDrawable(R.drawable.msg_out);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.rLayout.setBackgroundDrawable(drawable);
            holder.msgview.setLayoutParams(layoutParams);
            //msgview.setBackgroundResource(R.drawable.bubble2);
            holder.msgview.setText(chat.getMsg());
            // msgview.setText(s.getMsg());
            holder.txt.setText(getformatteddate(chat.getCtime()));
            if (chat.getStatus() == 0) {
                holder.statusview.setBackgroundResource(R.drawable.schedule);
            }
            if (chat.getStatus() == 1) {
                holder.statusview.setBackgroundResource(R.drawable.done);
            } else if (chat.getStatus() == 2) {
                holder.statusview.setBackgroundResource(R.drawable.done_all);
            } else if (chat.getStatus() == 3) {
                holder.statusview.setBackgroundResource(R.drawable.done_all_colo);
            }
           // holder.statusview.setBackgroundResource(R.drawable.done);

        } else {
            holder.statusview.setVisibility(View.INVISIBLE);
            Resources res = mContext.getResources();
            Drawable drawable = res.getDrawable(R.drawable.msg_in);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.msgview.setLayoutParams(layoutParams);
            holder.rLayout.setBackgroundDrawable(drawable);
            // msgview.setBackgroundResource(R.drawable.bubble1);
            holder.msgview.setText(chat.getMsg());
            holder.txt.setText(getformatteddate(chat.getCtime()));

        }
        return vw;

    }

    private String getformatteddate(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);

        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "today at  " + timeFormatter.format(dateTime);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "yesterday at " + timeFormatter.format(dateTime);
        } else {
            DateFormat dateform = new SimpleDateFormat("dd-MMM-yy");
            return dateform.format(dateTime) + " " + timeFormatter.format(dateTime);
        }

    }

}
