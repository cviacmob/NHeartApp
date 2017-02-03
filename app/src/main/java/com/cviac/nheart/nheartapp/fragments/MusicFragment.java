package com.cviac.nheart.nheartapp.fragments;

import java.io.File;
import java.io.IOException;
import java.net.URI;
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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.cviac.nheart.nheartapp.R.id.list1;
import static com.cviac.nheart.nheartapp.R.id.media_actions;
import static com.cviac.nheart.nheartapp.R.id.seekBar;

public class MusicFragment extends Fragment {
    private ImageButton btnPlay;
    static public MediaPlayer mp;
    private TextView title, artist, bduration;
    private ListView lv;
    private List<MusicInfo> songlist;
    private ImageView songimg;
    private int musicCounter = 0;
    private Thread updateseekbar;
    private SeekBar sb;

    private ProgressDialog progressDialog=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_music, container, false);
        //btnPlay = (ImageButton) view.findViewById(R.id.btnimg);


        lv = (ListView) view.findViewById(list1);
        progressDialog=new ProgressDialog(getContext(),R.style.AppTheme_AppBarOverlay);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (progressDialog!=null){
            progressDialog.dismiss();
        }

        mp = new MediaPlayer();
        btnPlay = (ImageButton) view.findViewById(R.id.btnimg);


        title = (TextView) view.findViewById(R.id.playtitle);
        title.setSelected(true);
        artist = (TextView) view.findViewById(R.id.artist);
        bduration = (TextView) view.findViewById(R.id.texdura);
        songimg = (ImageView) view.findViewById(R.id.musicimg);

        songlist = listOfSongs(getActivity());


        final MusicInfoAdapter adapter = new MusicInfoAdapter(getActivity(), songlist);
        lv.setAdapter(adapter);

        sb = (SeekBar) view.findViewById(R.id.seekBar3);
        updateseekbar = new Thread() {
            @Override
            public void run() {
                int totalduration = mp.getDuration();
                int currentposition = 0;
                sb.setMax(totalduration);
                while (currentposition < totalduration) {
                    try {
                        sleep(200);
                        currentposition = mp.getCurrentPosition();
                        sb.setProgress(currentposition);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.run();
            }

        };

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                songlist.get(musicCounter).setPlaying(false);
                musicCounter = position;
                Uri u = Uri.parse(songlist.get(position).toString());
                MusicInfo info = songlist.get(position);
                title.setText(info.getTitle());
                artist.setText(info.getSingers());
                bduration.setText(info.getDuration());
                if (info.getImgUrl() != null && info.getImgUrl().length() > 0) {
                    Picasso.with(getContext()).load(Uri.parse("file://" + info.getImgUrl())).resize(50, 50).into(songimg);
                } else {
                    Picasso.with(getContext()).load(R.mipmap.musicimg).resize(50, 50).into(songimg);
                }
                mp.reset();
                try {
                    mp.setDataSource(info.getPath());
                    mp.prepare();
                    mp.start();
                    info.setPlaying(true);
                    adapter.notifyDataSetChanged();
                    sb.setMax(mp.getDuration());
                    btnPlay.setBackgroundResource(R.drawable.pause);
                    try
                    {
                       updateseekbar.start();
                    }
                    catch(Exception e)
                    {
                        return;
                    }
                    sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });


                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mparg) {
                            songlist.get(musicCounter).setPlaying(false);
                            musicCounter++;
                            MusicInfo info = null;
                            if (musicCounter == songlist.size()) {
                                musicCounter = 0;
                                info = songlist.get(0);
                            } else {
                                info = songlist.get(musicCounter);
                            }
                            try {
                                mp.reset();
                                mp.setDataSource(info.getPath());
                                mp.prepare();
                                mp.start();
                                songlist.get(musicCounter).setPlaying(true);
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    v.setBackgroundResource(R.drawable.play);
                    mp.pause();
                    sb.setMax(mp.getDuration());
                    songlist.get(musicCounter).setPlaying(false);
                    adapter.notifyDataSetChanged();

                } else {
                    mp.start();
                }
                if (mp.isPlaying()) {
                    sb.setMax(mp.getDuration());
                    v.setBackgroundResource(R.drawable.pause);
                    songlist.get(musicCounter).setPlaying(true);
                    adapter.notifyDataSetChanged();
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



    public ArrayList<MusicInfo> listOfSongs(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // Filter only mp3s, only those marked by the MediaStore to be music and longer than 1 minute
        final String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
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
                String path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
                long duration = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long albumId = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String data = getAlbumArt(albumId);
                String composer = c.getString(c.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                int hours = (int) (duration / (1000 * 60 * 60));
                int minutes = (int) (duration % (1000 * 60 * 60)) / (1000 * 60);
                int seconds = (int) ((duration % (1000 * 60 * 60)) % (1000 * 60) / 1000);

               /* MediaPlayer mp = new MediaPlayer();
                mp.setDataSource(id);
                mp.prepare();
                mp.start();*/

                String finalTimerString = "";
                String secondsString = "";
                String minuteString = "";
                if (hours > 0) {
                    finalTimerString = hours + ":";
                }

                // Prepending 0 to seconds if it is one digit
                if (seconds < 10) {
                    secondsString = "0" + seconds;
                } else {
                    secondsString = "" + seconds;
                }

                if (minutes < 10) {
                    minuteString = "0" + minutes;
                } else {
                    minuteString = "" + minutes;
                }


                finalTimerString = finalTimerString + minuteString + ":" + secondsString;


                songData.setTitle(title);
                songData.setImgUrl(data);
                songData.setAlbumName(album);
                songData.setSingers(artist);
                songData.setDuration(finalTimerString);
                songData.setAlbumId(albumId);
                songData.setComposer(composer);
                songData.setPath(path);
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
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{String.valueOf(albumId)},
                null);

        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            // do whatever you need to do
        }
        return path;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_category).setVisible(false);
        menu.findItem(R.id.action_cart).setVisible(false);
        menu.findItem(R.id.action_call).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
