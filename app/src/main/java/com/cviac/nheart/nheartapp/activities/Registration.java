package com.cviac.nheart.nheartapp.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    protected static final Context context = null;
    private EditText email, phone, name;
    private Button submit;
    String value;
    ImageView iw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Register");

        name = (EditText) findViewById(R.id.namebox);

        email = (EditText) findViewById(R.id.mailbox);

        phone = (EditText) findViewById(R.id.phonebox);
        //iw=(ImageView) findViewById(R.id.imageView3);
        submit = (Button) findViewById(R.id.Registerbutton);
        value = email.getText().toString();

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String nam1e = name.getText().toString();
                String emi = email.getText().toString();
                String mbno=phone.getText().toString();

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

                    Intent btn = new Intent(Registration.this,
                            Otpverification.class);

                    startActivity(btn);
                    finish();
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



}
