package com.cviac.nheart.nheartapp.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.activities.Player;
import com.cviac.nheart.nheartapp.activities.ProductdetailsActivity;
import com.cviac.nheart.nheartapp.adapters.MusicInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;
import com.cviac.nheart.nheartapp.datamodel.Product;
import com.cviac.nheart.nheartapp.datamodel.Songs;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.cviac.nheart.nheartapp.R.id.list1;

public class MusicFragment extends Fragment {
    private ImageButton btnPlay;
    static MediaPlayer mp;
    private TextView title, artist, bduration;
    private ListView lv;
    private String[] items;
    private Integer[] imageId = {R.mipmap.musicimg};
    private ArrayList<File> mysongs;
    private List<MusicInfo> songlist;
    private ImageView songimg;


     static long duration2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_music, container, false);
        btnPlay = (ImageButton) view.findViewById(R.id.btnimg);
        lv = (ListView) view.findViewById(list1);
        mp = new MediaPlayer();
        title = (TextView) view.findViewById(R.id.playtitle);
        artist = (TextView) view.findViewById(R.id.artist);
        bduration = (TextView) view.findViewById(R.id.texdura);
        songimg = (ImageView) view.findViewById(R.id.musicimg);

        songlist = listOfSongs(getActivity());

        MusicInfoAdapter adapter = new MusicInfoAdapter(getActivity(), songlist);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MusicInfo info = songlist.get(position);
                title.setText(info.getTitle());
                artist.setText(info.getSingers());
                bduration.setText(info.getDuration());
                if (info.getImgUrl() != null && info.getImgUrl().length() > 0) {
                    Picasso.with(getContext()).load(Uri.parse("file://" + info.getImgUrl())).resize(50, 50).into(songimg);
                }
                else {
                    Picasso.with(getContext()).load(R.mipmap.musicimg).resize(50, 50).into(songimg);
                }


            }
        });
        if (songlist.size() > 0) {
            title.setText(songlist.get(0).getTitle());
            artist.setText(songlist.get(0).getSingers());
            bduration.setText(songlist.get(0).getDuration());
            Picasso.with(getContext()).load(Uri.parse("file://" + songlist.get(0).getImgUrl())).resize(50, 50).into(songimg);
        } else {
            title.setText("");
            artist.setText("");
            bduration.setText("");
        }
        return view;
    }


    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }


    public String toast(String text) {
        //Toast.makeText(getActivity().getApplication(),text,Toast.LENGTH_SHORT).show();
        return text;
    }




    public ArrayList<MusicInfo> listOfSongs(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // Filter only mp3s, only those marked by the MediaStore to be music and longer than 1 minute
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        // +" AND " + MediaStore.Audio.Media.MIME_TYPE + "= 'audio/mpeg'"
        // `+ " AND " + MediaStore.Audio.Media.DURATION + " > 60000";


        String sortOrder = MediaStore.Audio.AudioColumns.TITLE
                + " COLLATE LOCALIZED ASC";
        Cursor c = context.getContentResolver().query(uri, null, selection, null, sortOrder);
        ArrayList<MusicInfo> listOfSongs = new ArrayList<MusicInfo>();
        try {
            c.moveToFirst();
            while (c.moveToNext()) {
                MusicInfo songData = new MusicInfo();

                String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                long duration = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long albumId = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String data = getAlbumArt(albumId);
                String composer = c.getString(c.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                long minutes = (duration / 1000)  / 60;
                long seconds = (duration / 1000) % 60;
                songData.setTitle(title);
                songData.setImgUrl(data);
                songData.setAlbumName(album);
                songData.setSingers(artist);
                songData.setDuration(minutes+":"+seconds);
                songData.setAlbumId(albumId);
                songData.setComposer(composer);
                listOfSongs.add(songData);
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfSongs;
    }

    private String getAlbumArt(long albumId) {
        String path = "";
        Cursor cursor = getActivity().managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID+ "=?",
                new String[] {String.valueOf(albumId)},
                null);

        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            // do whatever you need to do
        }
        return path;
    }

}