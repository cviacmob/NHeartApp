package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;


public class Splashscreen extends Activity {

    public static String str_reg_test;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        str_reg_test = Prefs.getString("isregistered", null);


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*
                 * if user reg test is true on oncreate then redirect the user
				 * to home page
				 */

                if (str_reg_test != null
                        && !str_reg_test.toString().trim().equals("")) {
                    Intent send = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(send);
                    finish();
                }
				/*
				 * if user reg test is false on oncreate then redirect the
				 * user to Registration page
				 */
                else {
                    Intent send = new Intent(getApplicationContext(),
                            Registration.class);
                    startActivity(send);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}