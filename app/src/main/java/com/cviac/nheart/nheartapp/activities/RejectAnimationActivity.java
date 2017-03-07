package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;

/**
 * Created by admin1 on 3/6/2017.
 */

public class RejectAnimationActivity  extends AppCompatActivity {

    WebView web;
    TextView tv,tv2;
    TranslateAnimation animMove,mv2;
    private static int SPLASH_TIME_OUT = 4000;
    Button b1;
    String name,toname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature((Window.FEATURE_NO_TITLE));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rejectscreen);



        web = (WebView)findViewById(R.id.web1);
        tv=(TextView) findViewById(R.id.textView) ;
        tv2=(TextView) findViewById(R.id.textView2) ;
        name= Prefs.getString("name","");
        toname= Prefs.getString("to_name","");

        animMove=new TranslateAnimation(0.0f, -150.0f, 850.0f, 0.0f);
        animMove.setDuration(1500);
        animMove.setFillAfter(true);
        tv.setText(name);
        tv.startAnimation(animMove);


        mv2=new TranslateAnimation(0.0f, 150.0f, -850.0f, 0.0f);
        mv2.setDuration(1500);
        mv2.setFillAfter(true);
        tv2.setText(toname);
        tv2.startAnimation(mv2);

//        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.move1);
//        // Move
//
//
//        tv.startAnimation(animMove);
//
//        mv1 = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.move);
//
//        tv2.startAnimation(mv1);
        // web2 = (WebView)findViewById(R.id.web2);
        web.loadUrl("file:///android_asset/new_broke.gif");
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setSupportZoom(false);
        web.getSettings().setUseWideViewPort(true);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.setScrollbarFadingEnabled(false);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(RejectAnimationActivity.this, SendToInvite.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
