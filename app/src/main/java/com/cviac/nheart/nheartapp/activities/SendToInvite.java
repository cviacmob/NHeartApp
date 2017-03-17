package com.cviac.nheart.nheartapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.PairStatus;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class SendToInvite extends AppCompatActivity {
    Animation anim;
    ImageView img4,img5;
    ProgressDialog progressDialog = null;
    String display_mobile;
    Button b;
    EditText ed;
    String str,fromname,frommobile,tomobile,fromemail;
    int cus_id;
    int my_id=Prefs.getInt("customer_id",-1);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contt);
        ed=(EditText)findViewById(R.id.num);


        //str= Prefs.getString("Phone","");
        fromname= Prefs.getString("name","");
        fromemail=  Prefs.getString("email","");
        frommobile = Prefs.getString("mobile","");

        b = (Button) findViewById(R.id.getin);
        img4=(ImageView)findViewById(R.id.imageView5);
        img5=(ImageView)findViewById(R.id.imageView7);

        //tomobile=ed.getText().toString();

        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                tomobile=ed.getText().toString();

                Prefs.edit();
                Prefs.putString("display_mob_number",tomobile);
                Prefs.putString("Phone2",tomobile);

                if(tomobile.equals(frommobile)){
                    Toast.makeText(SendToInvite.this,"You cannot invite yourself",Toast.LENGTH_LONG).show();
                }
                else{



                    invitation(fromname,fromemail,frommobile,tomobile);
                    anim = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move2);
                    // Move


                    img4.startAnimation(anim);
                }
                // finish();

            }
        });

    }
    public void invitation(String name, String email, String mobile, final String to_mobile) {

        progressDialog = new ProgressDialog(SendToInvite.this,
                R.style.AppCompatAlertDialogStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);
        String pushid = Prefs.getString("pushId","");
        Call<PairStatus> call = api.sendInvitation(name, email, mobile,  to_mobile,pushid);
        call.enqueue(new Callback<PairStatus>() {
            @Override
            public void onResponse(Response<PairStatus> response, Retrofit retrofit) {
                PairStatus rsp = response.body();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if (rsp.getCode() == 0) {

                    //String resh=Prefs.getString("unpaired","");

//                    cus_id=Prefs.getInt("customer_id",-1);
                    Prefs.putString("to_mobile",to_mobile);
                    Prefs.putInt("inviteId", rsp.getId());


                    try {

                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();


                    }

                    //Prefs.putString("Customer_ID",rsp.getCustomer().getCustomer_id());
                    Intent logn = new Intent(SendToInvite.this, SendInvitationStatus.class);

                    startActivity(logn);
                    finish();
                }
                else if(rsp.getCode()==1004){
                    progressDialog.dismiss();
                    Toast.makeText(SendToInvite.this,
                            "Already Paired with Someone" , Toast.LENGTH_LONG).show();

                }

                else if(rsp.getCode()==1003){
                    progressDialog.dismiss();
                    Toast.makeText(SendToInvite.this,
                            "Your Invitation is Pending", Toast.LENGTH_LONG).show();

                }

                else if(rsp.getCode()==1007){
                    progressDialog.dismiss();
                    Toast.makeText(SendToInvite.this,
                            "Your Invitation is Rejected", Toast.LENGTH_LONG).show();

                }


                else {
                    progressDialog.dismiss();
                    Toast.makeText(SendToInvite.this,
                            "SendInvitation Failed: " + rsp.getCode(), Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SendToInvite.this,
                        "SendInvitation Failed: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}
