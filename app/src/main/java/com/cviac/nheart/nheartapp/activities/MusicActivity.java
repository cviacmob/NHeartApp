package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.checked;

public class MusicActivity extends AppCompatActivity {
    ListView lv;
    CheckBox cb;
    String[] items;
    MediaPlayer mp;
    //Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        lv=(ListView)findViewById(R.id.list1);

      cb=(CheckBox)findViewById(R.id.checkBox1);
        //b=(Button)findViewById(R.id.add);
        final ArrayList<File> mysongs=findsongs(Environment.getExternalStorageDirectory());

        items=new String[ mysongs.size() ];
        for(int i=0;i<mysongs.size();i++){
            //toast(mysongs.get(i).getName().toString());
            items[i]=mysongs.get(i).getName().toString().replace(".mp3"," ").replace(".wav"," ");
        }

        final ArrayAdapter<String>adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.textView,items);

        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {



                //startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("songlist",mysongs));
            }


        });
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
        public void toast(String text)
    {
        //Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }


    }



