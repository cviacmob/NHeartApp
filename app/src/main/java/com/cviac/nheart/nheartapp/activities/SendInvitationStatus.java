package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.PairStatus;
import com.cviac.nheart.nheartapp.datamodel.ReginfoResponse;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by user on 1/25/2017.
 */

public class SendInvitationStatus extends AppCompatActivity {


    ProgressDialog progressDialog = null;

    Animation animMove;
ImageView iv1,iv2;
    TextView im,tm,tm2,tm3,tm4,tm5;
    Button ok;
    int id=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendstatus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  tv=(TextView)findViewById(R.id.status);*/
        final ImageView im=(ImageView)findViewById(R.id.imgmm);
         tm=(TextView)findViewById(R.id.text1);
         tm2=(TextView)findViewById(R.id.text2);
         tm3=(TextView)findViewById(R.id.text3);
         tm4=(TextView)findViewById(R.id.textnum);
         tm5=(TextView)findViewById(R.id.textView3);
        iv1=(ImageView) findViewById(R.id.imageView10);
        iv2=(ImageView) findViewById(R.id.imageView11);


        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate1);
        // Move


        iv1.startAnimation(animMove);

        animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        // Move


        iv2.startAnimation(animMove);




        String sent_mobile=Prefs.getString("display_mob_number","");
        tm4.setText(sent_mobile);
       /* String  text = "Test";
        Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
        spanText.setSpan(new BackgroundColorSpan(0xFFFFFF00), 14, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spanText);*/

        tm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animMove = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_out);
                // Move


                tm5.startAnimation(animMove);

              int invitation_id=  Prefs.getInt("inviteId",-1);
              resendInvitation(invitation_id);
            }
        });


        ok=(Button)findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);

            }
        });

    }



    public void invitation(final int id,final String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);
        Call<PairStatus> call = api.checkInvitation(id);
        call.enqueue(new Callback<PairStatus>() {
            @Override
            public void onResponse(Response<PairStatus> response, Retrofit retrofit) {
                PairStatus rsp = response.body();
                if (rsp.getStatus() == "paired") {

                    Prefs.putString("status","Paired");
                    //Prefs.putString("Customer_ID",rsp.getCustomer().getCustomer_id());
                    Intent logn = new Intent(SendInvitationStatus.this, MainActivity.class);
                    startActivity(logn);
                    finish();
                } else if(rsp.getStatus() == "rejected") {
                    Intent logn = new Intent(SendInvitationStatus.this, SendToInvite.class);
                    startActivity(logn);
                    finish();
                }
                else
                    Toast.makeText(SendInvitationStatus.this,
                            "Your invitation is Pending  ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(SendInvitationStatus.this,
                        "Failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }





        });
    }




    public void resendInvitation(final int id) {

        progressDialog = new ProgressDialog(SendInvitationStatus.this,
                R.style.AppCompatAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);
        Call<PairStatus> call = api.verifyInvitation(id);
        call.enqueue(new Callback<PairStatus>() {
            @Override
            public void onResponse(Response<PairStatus> response, Retrofit retrofit) {
                PairStatus rsp = response.body();
                if (rsp.getCode() == 0) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }

                    //Prefs.putInt("inviteId", -1);
                    Toast.makeText(SendInvitationStatus.this,
                            "Your invitation has sent sucessfully", Toast.LENGTH_LONG).show();

                    /*Intent logn = new Intent(SendInvitationStatus.this, MainActivity.class);
                    startActivity(logn);
                    finish();*/
            }
                else

                    progressDialog.dismiss();
                    Toast.makeText(SendInvitationStatus.this,
                            "Your invitation could not sent ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SendInvitationStatus.this,
                        " Sent Failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }





        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;

    }
}
