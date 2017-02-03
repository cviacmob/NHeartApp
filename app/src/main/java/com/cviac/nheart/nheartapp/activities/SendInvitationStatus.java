package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    TextView tv;
    Button ok;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendstatus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv=(TextView)findViewById(R.id.status);
       /* String  text = "Test";
        Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
        spanText.setSpan(new BackgroundColorSpan(0xFFFFFF00), 14, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spanText);*/
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
                            "Your invitation is Pending  " + rsp.getCode(), Toast.LENGTH_LONG).show();
                }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(SendInvitationStatus.this,
                        "Failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }





        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;

    }
}
