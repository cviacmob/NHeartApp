package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.PairStatus;
import com.cviac.nheart.nheartapp.restapi.Invitation;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class Splashscreen extends Activity {

    public String registered;
    public int inviteId;
    public String invited;
    public String mobile;
    public String paired;

    Animation animMove,mv1,fade;
    ImageView img,img2,img3,iv5,iv9;
    TextView ttex1,ttex2,ttex3,ttex4,ttex5;
    ImageView imageView;
    private GifImageView gifImageView;

    public String invitetest;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_splash);
        //ivGif = (ImageView) findViewById(R.id.ivGif);
        // Display the GIF (from raw resource) into the ImageView
        //Glide.with(this).load(R.raw.android).asGif().into(imageView);
        /*gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.android);*/


    /*    WebView web = (WebView)findViewById(R.id.web1);

        web.loadUrl("file:///android_asset/accepted.gif");
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
      ;*/


        img=(ImageView) findViewById(R.id.img1) ;
        img2=(ImageView) findViewById(R.id.img2) ;
        img3=(ImageView)findViewById(R.id.imageView4);
        iv9=(ImageView)findViewById(R.id.imageView9);







        registered = Prefs.getString("isregistered", "false");
        inviteId = Prefs.getInt("inviteId", -1);
        invited = Prefs.getString("to_mobile", "");
        mobile = Prefs.getString("mobile", "");
        paired = Prefs.getString("paired", "false");

       // iv5  = (ImageView) findViewById(R.id.ivGif);
        // Display the GIF (from raw resource) into the ImageView


        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        // Move


        img3.startAnimation(animMove);

        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        // Move


        img.startAnimation(animMove);

        mv1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move1);

        img2.startAnimation(mv1);






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


                if (paired.equalsIgnoreCase("true")) {



                    Intent logn = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(logn);
                    finish();
                } else if (registered.equalsIgnoreCase("true")) {
                    if (invited.isEmpty()) {
                        //not invited
                        getInvitation(mobile);
                    } else {
                        //already invited
                        checkInvitation(inviteId);
                    }
                } else {
                    Intent logn = new Intent(Splashscreen.this, Registration.class);
                    startActivity(logn);
                    finish();
                    //show register screen activity
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void getInvitation(final String mobile) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<List<Invitation>> call = api.getInvitation(mobile);

        call.enqueue(new Callback<List<Invitation>>() {
                         @Override
                         public void onResponse(Response<List<Invitation>> response, Retrofit retrofit) {
                             List<Invitation> invits = response.body();

                             if (invits != null && invits.size() > 0) {
                                 Invitation invt = invits.get(0);
                                 if (invt.getStatus().equalsIgnoreCase("paired")) {

                                     Prefs.putString("paired", "true");
                                     Prefs.putInt("inviteId", -1);


                                     if (invt.getMobile().equalsIgnoreCase(mobile)) {
                                         String ss = invt.getTo_mobile();
                                         Prefs.putString("to_mobile", ss);

                                         


                                     } else {
                                         String mob = invt.getMobile();
                                         Prefs.putString("to_mobile", mob);

                                     }

                                     Intent logn1 = new Intent(Splashscreen.this, MainActivity.class);
                                     startActivity(logn1);
                                     finish();

                                 } else {
                                     Intent logn = new Intent(Splashscreen.this, InvitationReceived.class);
                                     logn.putExtra("invite", invits.get(0));
                                     startActivity(logn);
                                     finish();
                                 }
                             } else {
                                 Intent logn = new Intent(Splashscreen.this, SendToInvite.class);

                                 startActivity(logn);
                                 finish();
                             }

                         }

                         @Override
                         public void onFailure(Throwable t) {
                             Toast.makeText(Splashscreen.this,
                                     "GetInviation Failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }


    private void checkInvitation(int invtId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<PairStatus> call = api.checkInvitation(invtId);

        call.enqueue(new Callback<PairStatus>() {
                         @Override
                         public void onResponse(Response<PairStatus> response, Retrofit retrofit) {
                             PairStatus pairstatus = response.body();

                             if (pairstatus.getCode() == 0) {
                                 if (pairstatus != null && pairstatus.getStatus().equalsIgnoreCase("paired")) {
                                     Intent logn = new Intent(Splashscreen.this, MainActivity.class);
                                     startActivity(logn);
                                     finish();
                                 } else if (pairstatus != null && pairstatus.getStatus().equalsIgnoreCase("unpaired")) {
                                     Intent logn = new Intent(Splashscreen.this, SendInvitationStatus.class);
                                     startActivity(logn);
                                     finish();
                                 } else {
                                     Intent logn = new Intent(Splashscreen.this, SendToInvite.class);
                                     startActivity(logn);
                                     finish();
                                 }
                             } else {
                                 Toast.makeText(Splashscreen.this,
                                         "GetInviation Failed: " + pairstatus.getCode(), Toast.LENGTH_LONG).show();
                             }
                         }

                         @Override
                         public void onFailure(Throwable t) {
                             Toast.makeText(Splashscreen.this,
                                     "GetInviation Failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}