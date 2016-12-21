package com.cviac.nheart.nheartapp.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


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
    TextView title;
    private ListView lv1;
    List<Songs> emps;
    ListView lv;
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

        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);


        items=new String[ mysongs.size() ];
        for(int i=0;i<mysongs.size();i++){
           // toast(mysongs.get(i).getName().toString());
            items[i]=mysongs.get(i).getName().toString().replace(".mp3"," ").replace(".wav"," ");
        }
        CustomList adapter = new
                CustomList(getActivity(), items, imageId);
        lv.setAdapter(adapter);

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            strBuilder.append(items);
        }
        newString= strBuilder.toString();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //playSong(position);

                String SelectedItem = (String) lv.getItemAtPosition(position);

                //title.setText(position);

            }
        });



        return view;



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