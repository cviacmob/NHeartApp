package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;

/**
 * Created by admin1 on 3/6/2017.
 */

public class
AcceptAnimationAcitvity  extends AppCompatActivity {
    WebView web;
    //Button b1;
    TextView tv,tv2;

    String name,to_name;
    TranslateAnimation animation,animation2;
    private static int SPLASH_TIME_OUT = 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature((Window.FEATURE_NO_TITLE));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_acceptscree);

        web = (WebView)findViewById(R.id.web3);
        tv=(TextView) findViewById(R.id.textView3) ;
        tv2=(TextView) findViewById(R.id.textView4) ;
    final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.songone);
// web2 = (WebView)findViewById(R.id.web2);
        web.loadUrl("file:///android_asset/accpt.gif");
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setSupportZoom(false);
        web.getSettings().setUseWideViewPort(true);
    web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.setScrollbarFadingEnabled(false);

        name = Prefs.getString("name", "");
        to_name= Prefs.getString("to_name","");


        int startFrom = 0000;
        int endAt = SPLASH_TIME_OUT;



        Runnable stopPlayerTask = new Runnable(){
            @Override
            public void run() {
                mp2.pause();
            }};



          // final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.songone);
            mp2.seekTo(startFrom);
            mp2.start();

            Handler handler = new Handler();
            handler.postDelayed(stopPlayerTask, endAt);




//        if(mp2.isPlaying() == false) {
//            mp2.start();
//
//        }
//      if(mp2.isPlaying() == true)
//            mp2.stop();


        Intent i = getIntent();
//-----to get name from another class-------//
//        name = (DataModel) i.getSerializableExtra("mob");
//
//        mob = hug.getMob();
//         ss1 = hug.getTitle();
//         setTitle(ss1);


        animation = new TranslateAnimation(0.0f, 320.0f,0.0f, 400.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
        animation.setDuration(1000); // animation duration
        // animation repeat count
        animation.setRepeatMode(2); // repeat animation (left to right, right to left)
        animation.setFillAfter(true);
        tv.setText(name);
        tv .startAnimation(animation);//your_view for mine is imageView


        animation2 = new TranslateAnimation(0.0f, -320.0f,0.0f, -400.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
        animation2.setDuration(1000);// animation duration

        animation2.setFillBefore(true);// animation repeat count


        animation2.setRepeatMode(2); // repeat animation (left to right, right to left)

        animation2.setFillAfter(true);
        tv2.setText(to_name);
        tv2 .startAnimation(animation2);//your_view for mine is imageView





        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                Intent i = new Intent(AcceptAnimationAcitvity.this, MainActivity.class);
                startActivity(i);

                finish();

            }
        }, SPLASH_TIME_OUT);
    }


}

