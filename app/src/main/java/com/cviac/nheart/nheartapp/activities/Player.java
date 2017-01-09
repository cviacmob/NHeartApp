package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;

import java.io.File;
import java.util.ArrayList;

import static android.R.attr.duration;

public class Player extends AppCompatActivity implements View.OnClickListener {

    static MediaPlayer mp;
    ArrayList<File> mysongs;
    private double timeElapsed = 0, finalTime = 0;

    int position;
    SeekBar sb;
    Thread updateseekbar;
    Button btplay,btPv,btNxt,btFF,btFB;
    Uri u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btplay=(Button)findViewById(R.id.btplay);
        btFF=(Button)findViewById(R.id.btFF);
        btFB=(Button)findViewById(R.id.btFB);
        btNxt=(Button)findViewById(R.id.btNxt);
        btPv=(Button)findViewById(R.id.btPv);
        //TextView duration1 = (TextView) findViewById(R.id.dur);


        btplay.setOnClickListener(this);
        btFF.setOnClickListener(this);
        btFB.setOnClickListener(this);
        btNxt.setOnClickListener(this);
        btPv.setOnClickListener(this);


        sb=(SeekBar)findViewById(R.id.seekBar);

        updateseekbar=new Thread(){
            @Override
              public void run() {
                int totalduration=mp.getDuration();
                int currentposition=0;
                sb.setMax(totalduration);

                timeElapsed = mp.getCurrentPosition();

                while(currentposition<totalduration){
                    try {
                        sleep(500);
                        currentposition=mp.getCurrentPosition();
                        sb.setProgress(currentposition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //super.run();
            }

        };



        if(mp!=null)
        {
            mp.stop();
            mp.release();
        }



        Intent i=getIntent();
        Bundle b=i.getExtras();
        ArrayList<File> mysongs=(ArrayList) b.getParcelableArrayList("songlist");
       position=b.getInt("pos",0);

        u=Uri.parse( mysongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        sb.setMax(mp.getDuration());
    updateseekbar.start();
    sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mp.seekTo(seekBar.getProgress());
        }
    });
}

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.btplay:

                if(mp.isPlaying()){
                   // btplay.setText("playing");
                    mp.pause();
                    //sb.setMax(mp.getDuration());
                }

                else
                    btplay.setText("||");
                    mp.start();
                //sb.setMax(mp.getDuration());
                    break;
            case R.id.btNxt:
                mp.stop();
                mp.release();

                 u=Uri.parse( mysongs.get(position+1).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                break;
            case R.id.btPv:
                mp.stop();
                mp.release();
                position=(position-1<0)? mysongs.size()-1: position-1;
               /* if (position-1 < 0) {
                    position=mysongs.size()-1;
                }
                else{
                    position=position-1;
                }*/
                u=Uri.parse( mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                break;

        }
    }
}
