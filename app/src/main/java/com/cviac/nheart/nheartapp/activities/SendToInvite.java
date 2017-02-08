package com.cviac.nheart.nheartapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class SendToInvite extends AppCompatActivity {

    ProgressDialog progressDialog = null;

    Button b;
    EditText ed;
    String str,fromname,frommobile,tomobile,fromemail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contt);
        ed=(EditText)findViewById(R.id.num);
        setTitle("Invite");
        //str= Prefs.getString("Phone","");
        fromname=Prefs.getString("Name","");
        fromemail=  Prefs.getString("Email","");
        frommobile =Prefs.getString("Phone","");
        b = (Button) findViewById(R.id.getin);
        //tomobile=ed.getText().toString();

        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                tomobile=ed.getText().toString();
                Prefs.putString("Phone2",tomobile);

                if(tomobile.equals(frommobile)){
                    Toast.makeText(SendToInvite.this,"send valid number",Toast.LENGTH_LONG).show();
                }
                else{
                    invitation(fromname,fromemail,frommobile,tomobile);
                }
                // finish();

            }
        });

    }
    public void invitation(String name, String email, String mobile, final String to_mobile) {

        progressDialog = new ProgressDialog(SendToInvite.this,
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
        Call<PairStatus> call = api.sendInvitation(name, email, mobile,  to_mobile);
        call.enqueue(new Callback<PairStatus>() {
            @Override
            public void onResponse(Response<PairStatus> response, Retrofit retrofit) {
                PairStatus rsp = response.body();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if (rsp.getCode() == 0) {

                    //String resh=Prefs.getString("unpaired","");


                    Prefs.putString("to_mobile",to_mobile);
                    Prefs.putInt("inviteId", rsp.getId());
                    //Prefs.putString("Customer_ID",rsp.getCustomer().getCustomer_id());
                    Toast.makeText(SendToInvite.this,
                            "Your Invitation Sent" + rsp.getCode(), Toast.LENGTH_LONG).show();

                    Intent logn = new Intent(SendToInvite.this, SendInvitationStatus.class);

                    startActivity(logn);
                    finish();
                }
                else if(rsp.getCode()==1004){

                    Toast.makeText(SendToInvite.this,
                            "Already Paired with Someone" + rsp.getCode(), Toast.LENGTH_LONG).show();

                }

                else if(rsp.getCode()==1003){

                    Toast.makeText(SendToInvite.this,
                            "Your Invitation is Pending" + rsp.getCode(), Toast.LENGTH_LONG).show();

                }

                else if(rsp.getCode()==1007){

                    Toast.makeText(SendToInvite.this,
                            "Your Invitation is Rejected" + rsp.getCode(), Toast.LENGTH_LONG).show();

                }


                else {
                    Toast.makeText(SendToInvite.this,
                            "SendInvitation Failed: " + rsp.getCode(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(SendToInvite.this,
                        "SendInvitation Failed: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}
