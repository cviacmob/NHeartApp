package com.cviac.nheart.nheartapp.fragments;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;


import com.cviac.nheart.nheartapp.activities.MainActivity;
import com.cviac.nheart.nheartapp.activities.ProductdetailsActivity;
import com.cviac.nheart.nheartapp.adapters.MusicInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;
import com.cviac.nheart.nheartapp.datamodel.Product;
import com.cviac.nheart.nheartapp.datamodel.Songs;
import com.squareup.picasso.Picasso;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.cviac.nheart.nheartapp.R.id.list1;
import static com.cviac.nheart.nheartapp.R.id.media_actions;


public class MusicFragment extends Fragment {
    private ImageButton btnPlay;

    private TextView title, artist, bduration;
    private ListView lv;
    private List<MusicInfo> songlist;
    private ImageView songimg;
    private int musicCounter = 0;
    private Thread updateseekbar;
    private SeekBar sb;

    public MediaPlayer mp;

    private MusicInfoAdapter adapter;


    private ProgressBar pb;

    private View rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_music, container, false);
        pb = (ProgressBar) view.findViewById(R.id.progressbarmusic);

        rv = view.findViewById(R.id.rdid);

        //btnPlay = (ImageButton) view.findViewById(R.id.btnimg);


        lv = (ListView) view.findViewById(list1);


        btnPlay = (ImageButton) view.findViewById(R.id.btnimg);


        title = (TextView) view.findViewById(R.id.playtitle);
        title.setSelected(true);
        artist = (TextView) view.findViewById(R.id.artist);
        bduration = (TextView) view.findViewById(R.id.texdura);
        songimg = (ImageView) view.findViewById(R.id.musicimg);

        songlist = ((MainActivity) getActivity()).getSonglist();
        if (songlist.size() == 0 ) {
            new LoadMediaTask().execute();
        }
        else {
            adapter = new MusicInfoAdapter(getActivity(), songlist);
            lv.setAdapter(adapter);
        }

        mp = ((MainActivity) getActivity()).getMediaPlayer();

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
                    btnPlay.setBackgroundResource(R.drawable.pau);
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
                    v.setBackgroundResource(R.drawable.play143);
                    mp.pause();
                    sb.setMax(mp.getDuration());
                    songlist.get(musicCounter).setPlaying(false);
                    adapter.notifyDataSetChanged();

                } else {

                    mp.start();
                }
                if (mp.isPlaying()) {
                    sb.setMax(mp.getDuration());
                    v.setBackgroundResource(R.drawable.pau);
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

    public void loadMedias() {
        new LoadMediaTask().execute();
    }

    private class LoadMediaTask extends AsyncTask<String, Integer, Long> {

        ArrayList<MusicInfo> list;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(ProgressBar.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.INVISIBLE);

        }

        @Override
        protected Long doInBackground(String... params) {
//            try {
//                Thread.sleep(1000*3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            list = listOfSongs(getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            pb.setVisibility(ProgressBar.INVISIBLE);

            ((MainActivity) getActivity()).setSonglist(list);
            songlist = list;
            if (songlist.size() > 0) {
                lv.setVisibility(View.VISIBLE);
                rv.setVisibility(View.VISIBLE);
                adapter = new MusicInfoAdapter(getActivity(), songlist);
                lv.setAdapter(adapter);


                MusicInfo info = songlist.get(0);
                title.setText(info.getTitle());
                artist.setText(info.getSingers());
                bduration.setText(info.getDuration());
                Picasso.with(getContext()).load(Uri.parse("file://" + info.getImgUrl())).resize(50, 50).into(songimg);
                try {
                    mp.setDataSource(info.getPath());
                    mp.prepare();
                    sb.setMax(mp.getDuration());
                }
                catch (Exception ex) {
                }
            } else {
                title.setText("");
                artist.setText("");
                bduration.setText("");
            }

        }
    }



    public ArrayList<MusicInfo> listOfSongs(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // Filter only mp3s, only those marked by the MediaStore to be music and longer than 1 minute
        final String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        // +" AND " + MediaStore.Audio.Media.MIME_TYPE + "= 'audio/mpeg'"
        // `+ " AND " + MediaStore.Audio.Media.DURATION + " > 60000";


        String sortOrder = MediaStore.Audio.AudioColumns.TITLE
                + " COLLATE LOCALIZED ASC";

        ArrayList<MusicInfo> listOfSongs = new ArrayList<MusicInfo>();
        if (ContextCompat.checkSelfPermission(getActivity(), (Manifest.permission.READ_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED)  {
           // ActivityCompat.requestPermissions(getActivity(), new String[]{
           //         android.Manifest.permission.READ_EXTERNAL_STORAGE}, MainActivity.MY_PERMISSION_MEDIA);
            return listOfSongs;
        }

        Cursor c = context.getContentResolver().query(uri, null, selection, null, sortOrder);

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
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.loc).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
