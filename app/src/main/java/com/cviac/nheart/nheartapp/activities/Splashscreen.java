package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
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
    public String invitetest;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        registered = Prefs.getString("isregistered", "false");
        inviteId = Prefs.getInt("inviteId", -1);
        invited = Prefs.getString("to_mobile", "");
        mobile = Prefs.getString("mobile", "");
        paired = Prefs.getString("paired", "false");
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
                }
                else if (registered.equalsIgnoreCase("true")) {
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

    private void getInvitation(String mobile) {
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

                             if (invits !=null && invits.size() > 0) {
                                 Intent logn = new Intent(Splashscreen.this, InvitationReceived.class);
                                 logn.putExtra("invite", invits.get(0));
                                 startActivity(logn);
                                 finish();
                             }
                             else {
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
                               if (pairstatus.getStatus().equalsIgnoreCase("paired") && pairstatus!=null) {
                                 Intent logn = new Intent(Splashscreen.this, MainActivity.class);
                                 startActivity(logn);
                                 finish();
                             }
                             else if (pairstatus.getStatus().equalsIgnoreCase("unpaired")) {
                                 Intent logn = new Intent(Splashscreen.this, SendInvitationStatus.class);
                                 startActivity(logn);
                                 finish();
                             }
                             else {
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
}