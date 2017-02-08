package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.PairStatus;
import com.cviac.nheart.nheartapp.datamodel.ReginfoResponse;
import com.cviac.nheart.nheartapp.restapi.Invitation;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.UpdateInvitation;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class InvitationReceived extends AppCompatActivity {

    ProgressDialog progressDialog = null;


    Button accept, reject;
    TextView name, email, mobile,textvv;
    String mob;
    ImageButton callbtn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        setTitle("Received Invitation ");
        accept = (Button) findViewById(R.id.accept);
        name = (TextView) findViewById(R.id.name1);
        //email = (TextView) findViewById(R.id.email1);
        mobile = (TextView) findViewById(R.id.mob2);
        textvv = (TextView) findViewById(R.id.text11);
        callbtn=(ImageButton)findViewById(R.id.call_button);
        Intent i = getIntent();
        Invitation invite = (Invitation) i.getSerializableExtra("invite");
        // email.setText(invite.getEmail_id());
        //final String em=email.getText().toString();
        mobile.setText(invite.getMobile());
        mob=mobile.getText().toString();
        name.setText(invite.getName());
        final String nm=name.getText().toString();
        Prefs.putString("mobile1",mob);
        final String my_mobile= Prefs.getString("Phone","");


        accept.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                updateInvite(mob,my_mobile,"accepted");


            }
        });
        reject = (Button) findViewById(R.id.reject);

        reject.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                updateInvite(mob,my_mobile,"rejected");
            }
        });





    }

    public void updateInvite(String mobile, String to_mobile, final String status) {


        progressDialog = new ProgressDialog(InvitationReceived.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        UpdateInvitation up=new UpdateInvitation();

        up.setMobile(mobile);
        up.setTo_mobile(to_mobile);
        up.setStatus(status);
        Call<PairStatus> call = api.updateInvite(up);
        call.enqueue(new Callback<PairStatus>() {


            @Override
            public void onResponse(Response<PairStatus> response, Retrofit retrofit) {
                PairStatus rsp = response.body();

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if(rsp.getCode()==0){
                    if(status=="accepted"){
                        Prefs.putString("from_mobile",mob);
                        Prefs.putString("paired","true");
                        Intent mainIntent = new Intent(InvitationReceived.this, MainActivity
                                .class);
                        startActivity(mainIntent);
                        finish();
                    }
                    else {
                        Intent mainIntent = new Intent(InvitationReceived.this, SendToInvite
                                .class);
                        startActivity(mainIntent);
                        finish();
                    }


                }
                else{
                    Toast.makeText(InvitationReceived.this,
                            "Already Paired With Someone " + rsp.getCode(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(InvitationReceived.this,
                        "Pair Request failed" , Toast.LENGTH_LONG).show();


            }
        });
    }
}