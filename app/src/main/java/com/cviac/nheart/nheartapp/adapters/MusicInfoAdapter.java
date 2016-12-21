package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.Category;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Cviac on 21/12/2016.
 */

public class MusicInfoAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater inflter;
    List<MusicInfo> list;

    public MusicInfoAdapter(Context ctx, List<MusicInfo> list) {
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
        TextView title;
        ImageView img;
        TextView duration;
        String imgUrl;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vw = convertView;
        ViewHolder holder;
        MusicInfo ct = list.get(position);
        if (convertView == null) {
            inflter = LayoutInflater.from(ctx);
            convertView = inflter.inflate(R.layout.list_single, null);
            holder = new ViewHolder();
            vw = convertView;
            holder.title = (TextView) vw.findViewById(R.id.titlehead);
            holder.duration = (TextView) vw.findViewById(R.id.texduration);
            holder.img = (ImageView) vw.findViewById(R.id.img);
            vw.setTag(holder);

        } else {
            holder = (ViewHolder) vw.getTag();
        }
        holder.imgUrl = ct.getImgUrl();
        holder.title.setText(ct.getTitle());
        holder.duration.setText(ct.getDuration());
        new LoadImageAsyncTask().execute(holder);
        //Picasso.with(vw.getContext()).load(ct.getImgUrl()).resize(50, 50).into(holder.img);


        return vw;
    }

    private class LoadImageAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {
        ViewHolder holder;
        Bitmap bitmap;
        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            holder = params[0];
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(holder.imgUrl);
            byte[] imgbytes = mmr.getEmbeddedPicture();
            if (imgbytes != null) {
                bitmap = BitmapFactory.decodeByteArray(imgbytes, 0, imgbytes.length);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ViewHolder viewHolder) {
            if (bitmap != null) {
                holder.img.setImageBitmap(bitmap);
            }
            else {
                holder.img.setImageResource(R.mipmap.musicimg);
            }
        }
    }


}