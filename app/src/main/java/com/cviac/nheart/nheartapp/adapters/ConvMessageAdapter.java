package com.cviac.nheart.nheartapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.activities.ChatMessage;
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
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1, MY_IMAGE = 2, OTHER_IMAGE = 3;

    Context mContext;

    public ConvMessageAdapter(List<ConvMessage> objects, Context context) {
        super(context, R.layout.chat_mine, objects);
        chats = objects;
        mContext = context;
    }

    public static class ViewHolder {
        public TextView textView;
        public TextView textViewother, textviewduration, textviewdurationmine;
        public  ImageView imagetick;

    }
    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 4;
    }
    @Override
    public int getItemViewType(int position) {
        ConvMessage item = getItem(position);
        if (item.isMine()) return MY_MESSAGE;
        else if (!item.isMine() ) return OTHER_MESSAGE;
        else if (item.isMine()) return MY_IMAGE;
        else return OTHER_IMAGE;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vw=convertView;
        int viewType = getItemViewType(position);
        ViewHolder holder;
        ConvMessage chat=getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            if (viewType == MY_MESSAGE) {

                vw = LayoutInflater.from(getContext()).inflate(R.layout.chat_mine, parent, false);
                holder.textView = (TextView) vw.findViewById(R.id.text);
                holder.textviewdurationmine = (TextView) vw.findViewById(R.id.durationtext);
                holder.imagetick = (ImageView) vw.findViewById(R.id.imageView2);


            } else if(viewType == OTHER_MESSAGE)
            {
                vw = LayoutInflater.from(getContext()).inflate(R.layout.chat_other, parent, false);
                holder.textViewother = (TextView) vw.findViewById(R.id.text);
                holder.textviewduration = (TextView) vw.findViewById(R.id.text1);

            }
            vw.setTag(holder);
        }else {
            holder = (ViewHolder) vw.getTag();
        }

        if(chat.isMine()){
            holder.textView.setText(chat.getMsg());
            holder.textviewdurationmine.setText(getformatteddate(chat.getCtime()));
            if (chat.getStatus() == 0) {
                holder.imagetick.setBackgroundResource(R.drawable.schedule);
            }
            if (chat.getStatus() == 1) {
                holder.imagetick.setBackgroundResource(R.drawable.done);
            } else if (chat.getStatus() == 2) {
                holder.imagetick.setBackgroundResource(R.drawable.done_all);
            } else if (chat.getStatus() == 3) {
                holder.imagetick.setBackgroundResource(R.drawable.done_all_colo);
            }
        }else{
            holder.textViewother.setText(chat.getMsg());
            holder.textviewduration.setText(getformatteddate(chat.getCtime()));
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
