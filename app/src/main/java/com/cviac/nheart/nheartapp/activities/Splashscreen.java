package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cviac.nheart.nheartapp.R;


public class Splashscreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */


               /* final String MyPREFERENCES = "MyPrefs" ;
                SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                String isRegistered = prefs.getString("isRegistered", "false");*/
              /*  if (isRegistered.equals("false")) {*/
                Intent mainIntent = new Intent(Splashscreen.this,Registration.class);
                Splashscreen.this.startActivity(mainIntent);
                Splashscreen.this.finish();
            }
            //else {
                    /*Intent mainIntent = new Intent(Splashscreen.this,Registration
                            .class);
                    Splashscreen.this.startActivity(mainIntent);
                    Splashscreen.this.finish();
                }*/


            //}
        }, SPLASH_DISPLAY_LENGTH);
    }
}