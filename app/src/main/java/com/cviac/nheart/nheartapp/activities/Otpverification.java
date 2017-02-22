package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
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
import com.cviac.nheart.nheartapp.restapi.Invitation;
import com.cviac.nheart.nheartapp.restapi.OTPInfo;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.VerifyOTPResponse;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Otpverification extends AppCompatActivity {

    ProgressDialog progressDialog = null;

    int counter=0;
    Button b, resend;
    EditText pin;
    String str;
    String pass,mobile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);



        b = (Button) findViewById(R.id.verifybutton);
        resend = (Button) findViewById(R.id.resend);
        pin = (EditText) findViewById(R.id.editText1);
        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String aaa = pin.getText().toString();
                boolean error = false;
                if (aaa.length() == 0) {
                    pin.setError("enter the pin");
                    pin.requestFocus();
                    error = true;
                }
                if (error == false) {
                     pass = pin.getText().toString();
                     mobile = Prefs.getString("mobile", "");
                    otpVerify(mobile, pass);

                }
            }

        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = Prefs.getString("mobile", "");

                if (counter < 3) {
                    resendotp(mobile);
                }

                else{
                    Toast.makeText(Otpverification.this, "you exceeded max attempts", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void otpVerify(final String mob, String pin) {

        progressDialog = new ProgressDialog(Otpverification.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Verifying");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);
        OTPInfo info = new OTPInfo();
        info.setMobile(mob);
        info.setOtp(pin);
        Call<VerifyOTPResponse> call = api.verifyOTP(info);
        call.enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Response<VerifyOTPResponse> response, Retrofit retrofit) {
                VerifyOTPResponse rsp = response.body();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if (rsp.getCode() == 0) {
                    Prefs.putString("isregistered", "true");
                    int inviteId = Prefs.getInt("inviteId", -1);
                    String invited = Prefs.getString("to_mobile", "");
                    String mobile = Prefs.getString("mobile", "");
                    if (invited.isEmpty()) {


                        //not invited
                        getInvitation(mobile);
                    } else {
                        progressDialog.dismiss();
                        //already invited
                        checkInvitation(inviteId);
                    }


                } else if (rsp.getCode() == 1005) {
                    progressDialog.dismiss();
                    Toast.makeText(Otpverification.this,
                            "Mobile number / OTP  is wrong" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(Otpverification.this,
                        "Invalid OTP " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

                             if (invits != null && invits.size() > 0) {
                                 Intent logn = new Intent(Otpverification.this, InvitationReceived.class);
                                 logn.putExtra("invite", invits.get(0));
                                 startActivity(logn);
                                 finish();
                             } else {

                                 Intent logn = new Intent(Otpverification.this, SendToInvite.class);
                                 startActivity(logn);
                                 finish();
                             }

                         }

                         @Override
                         public void onFailure(Throwable t) {
                             Toast.makeText(Otpverification.this,
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
                             if (pairstatus.getStatus().equalsIgnoreCase("paired")) {
                                 Intent logn = new Intent(Otpverification.this, MainActivity.class);
                                 startActivity(logn);
                                 finish();
                             } else if (pairstatus.getStatus().equalsIgnoreCase("unpaired")) {
                                 Intent logn = new Intent(Otpverification.this, SendInvitationStatus.class);
                                 startActivity(logn);
                                 finish();
                             } else {
                                 Intent logn = new Intent(Otpverification.this, SendToInvite.class);
                                 startActivity(logn);
                                 finish();
                             }
                         }

                         @Override
                         public void onFailure(Throwable t) {
                             Toast.makeText(Otpverification.this,
                                     "GetInviation Failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }


    public void resendotp(final String mob) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);
        OTPInfo info = new OTPInfo();
        info.setMobile(mob);
        // info.setOtp(pin);
        Call<VerifyOTPResponse> call = api.resendOTP(info);
        call.enqueue(new Callback<VerifyOTPResponse>() {
                         @Override
                         public void onResponse(Response<VerifyOTPResponse> response, Retrofit retrofit) {
                             VerifyOTPResponse rsp = response.body();
                             if (rsp.getCode() == 0) {

                                 otpVerify(mobile,pass);



                             }
                         }

                         @Override
                         public void onFailure(Throwable t) {
                             Toast.makeText(Otpverification.this,
                                     "Invalid OTP " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                         }


                     }
        );
    }
}
