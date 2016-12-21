package com.cviac.nheart.nheartapp.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.activities.Player;
import com.cviac.nheart.nheartapp.activities.ProductdetailsActivity;
import com.cviac.nheart.nheartapp.datamodel.Product;
import com.cviac.nheart.nheartapp.datamodel.Songs;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.cviac.nheart.nheartapp.R.id.list1;

public class MusicFragment extends Fragment {
    private ImageButton btnPlay;
    static MediaPlayer mp;
    private AudioManager audioManager = null;
    TextView title,bduration;
    private ListView lv1;
    List<Songs> emps;
    ListView lv;
    //private double finalTime = 0;
    String text;
    Context thiscontext;
    String newString;
    String[] items;
    Integer[] imageId = {
            R.mipmap.musicimg};

    final ArrayList<File> mysongs=findsongs(Environment.getExternalStorageDirectory());

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_music, container, false);
        btnPlay = (ImageButton) view.findViewById(R.id.btnimg);
        lv=(ListView)view.findViewById(list1);
        mp = new MediaPlayer();
         title=(TextView) view.findViewById(R.id.playtitle);
        bduration=(TextView) view.findViewById(R.id.texdura);
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);


        items=new String[ mysongs.size() ];
        for(int i=0;i<mysongs.size();i++){
           // toast(mysongs.get(i).getName().toString());
            items[i]=mysongs.get(i).getName().toString().replace(".mp3"," ").replace(".wav"," ");
        }
        CustomList adapter = new
                CustomList(getActivity(), items, imageId);
        lv.setAdapter(adapter);

        /*StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            strBuilder.append(items);
        }
        newString= strBuilder.toString();*/


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //playSong(position);

                mp.start();

                long finalTime = mp.getDuration();


                //bduration.setText(""+utils.milliSecondsToTimer(totalDuration));

                bduration.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                String SelectedItem =lv.getItemAtPosition(position).toString();

                Prefs.edit();
                Prefs.putString("titl",SelectedItem);
                //title.setText(position);

            }
        });

        String tit=Prefs.getString("titl"," ");
        title.setText(String.valueOf(tit));
        return view;
    }



    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }





    public String toast(String text)
    {
        //Toast.makeText(getActivity().getApplication(),text,Toast.LENGTH_SHORT).show();
        return text;
    }








    public ArrayList<File> findsongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(findsongs(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                    al.add(singleFile);
                }
            }
        }
        return al;
    }

}