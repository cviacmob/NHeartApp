package com.cviac.nheart.nheartapp.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.NheartApp;
import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.ReginfoResponse;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Registration extends AppCompatActivity {

    protected static final Context context = null;
    private EditText email, phone, name,password,confirm;
    private Button submit;
    String value;
    ImageView iw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Register");

        name = (EditText) findViewById(R.id.namebox);
        name.setInputType(InputType.TYPE_CLASS_TEXT);

        email = (EditText) findViewById(R.id.mailbox);
        email.setInputType(InputType.TYPE_CLASS_TEXT);

        phone = (EditText) findViewById(R.id.phonebox);
        phone.setInputType(InputType.TYPE_CLASS_PHONE);

        password = (EditText) findViewById(R.id.password);


        confirm = (EditText) findViewById(R.id.confirm);

        //iw=(ImageView) findViewById(R.id.imageView3);
        submit = (Button) findViewById(R.id.Registerbutton);
        submit.setInputType(InputType.TYPE_CLASS_TEXT);

        value = email.getText().toString();

        submit.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v) {

                String nam1e = name.getText().toString();
                String emi = email.getText().toString();
                String mbno=phone.getText().toString();
                String pass = password.getText().toString();
                String conf=confirm.getText().toString();


                boolean error = false;
                if (nam1e.length() == 0) {
                    name.setError("name not entered");
                    name.requestFocus();
                    error = true;
                }


                if (phone.length() != 10) {
                    phone.setError("invalid mobile number");
                    phone.requestFocus();
                    error = true;
                }
                if (isValidEmail(emi) == false) {
                    email.setError("Enter valid email");
                    email.requestFocus();
                    error = true;
                }

                if (error == false) {

                    Prefs.edit();
                    Prefs.putString("Name",nam1e);
                    Prefs.putString("Email",emi);
                    Prefs.putString("Phone",mbno);

                    /*Intent btn = new Intent(Registration.this,
                            Otpverification.class);

                    startActivity(btn);
                    finish();*/
                }

                if(!pass.equals(conf)){
                    password.setError("Password does not match");
                    confirm.requestFocus();
                    return;
                }

                NheartApp nh=(NheartApp)Registration.this.getApplication();

                if(nh.isNetworkstatus()){


                    register(nam1e,nam1e,emi,mbno,pass,conf);
                }

                else{
                    Toast.makeText(getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();
                }



            }
            });




    }

    protected boolean isValidEmail(String email) {
        // TODO Auto-generated method stub
        // String emi=email.getText().toString();

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void register(String firstname, String lastname, String email1, String mob, String pswd, String cpswd) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);
        Call<ReginfoResponse> call = api.register(firstname, lastname, email1, mob, pswd, cpswd);
        call.enqueue(new Callback<ReginfoResponse>() {
            @Override
            public void onResponse(Response<ReginfoResponse> response, Retrofit retrofit) {
                ReginfoResponse rsp = response.body();
                if (rsp.getCode() == 0) {
                    //Prefs.putString("Customer_ID",rsp.getCustomer().getCustomer_id());
                    Intent logn = new Intent(Registration.this, MainActivity.class);
                    startActivity(logn);
                    finish();
                } else {
                    Toast.makeText(Registration.this,
                            "Registration Failed: " + rsp.getCode(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(Registration.this,
                        "Registration Failed: "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
