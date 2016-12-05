package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.R;

public class Otpverification extends Activity {

    Button b;
    EditText pin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        b = (Button) findViewById(R.id.verifybutton);
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
                    String pass = pin.getText().toString();
                    // TODO Auto-generated method stub

                    if (pass.equals("123")) {
                        //Toast.makeText(Otpverification.this, "Welcome", Toast.LENGTH_LONG).show();

                        final String MyPREFERENCES = "MyPrefs" ;
                        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("isRegistered", "true");
                        editor.commit();

                        Intent mainIntent = new Intent(Otpverification.this,MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Toast.makeText(Otpverification.this, "Invalid pin", Toast.LENGTH_LONG).show();
                    }

                }
            }

        });
    }
}
