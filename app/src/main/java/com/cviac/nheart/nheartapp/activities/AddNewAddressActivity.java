package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.Addressinfo;

import java.util.ArrayList;
import java.util.List;

public class AddNewAddressActivity extends AppCompatActivity {
      EditText pincode,city,state,name,mobile,alternatemobile,shipping,landmark;
      Button cancel,add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewaddress);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pincode=(EditText)findViewById(R.id.pinbox);
        city=(EditText)findViewById(R.id.citybox);
        state=(EditText)findViewById(R.id.statebox);
        name=(EditText)findViewById(R.id.fulnamebox);
        mobile=(EditText) findViewById(R.id.mobilebox);
        alternatemobile=(EditText) findViewById(R.id.alternatemobilebox);
        shipping=(EditText) findViewById(R.id.shippingbox);
        landmark=(EditText) findViewById(R.id.landmarkbox);
        cancel=(Button)findViewById(R.id.addcancel);
        add=(Button)findViewById(R.id.savecontinue);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String str= name.getText().toString();
                String str1= city.getText().toString();
                String str3= state.getText().toString();
                String str2= landmark.getText().toString();
                String str4=mobile.getText().toString();
                String str5=alternatemobile.getText().toString();
                String str6=pincode.getText().toString();

                boolean error = false;

                if (pincode.length() < 6) {
                    pincode.setError("invalid pincode number");
                    pincode.requestFocus();
                    error = true;
                }
                if (state.length() == 0) {
                    state.setError("state not entered");
                    state.requestFocus();
                    error = true;
                }
                if (name.length() == 0) {
                    name.setError("name not entered");
                    name.requestFocus();
                    error = true;
                }
                if (city.length() == 0) {
                    city.setError("city not entered");
                    city.requestFocus();
                    error = true;
                }
                if (shipping.length() == 0) {
                    shipping.setError("shipping address not entered");
                    shipping.requestFocus();
                    error = true;
                }
                if (alternatemobile.length() < 10) {
                    alternatemobile.setError("invalid alternate mobile number");
                    alternatemobile.requestFocus();
                    error = true;
                }



                if (mobile.length() < 10) {
                    mobile.setError("invalid mobile number");
                    mobile.requestFocus();
                    error = true;
                }

               List<Addressinfo> addresslist=new ArrayList<Addressinfo>();
                if(addresslist.size() >0) {
                    Addressinfo info=addresslist.get(0);
                    name.setText(info.getFulname());
                    city.setText(info.getCity());
                    state.setText(info.getState());
                    landmark.setText(info.getLandmark());
                    mobile.setText(info.getMobile());
                    alternatemobile.setText(info.getAlternatemobile());
                    pincode.setTag(info.getPincode());
                    Intent i=new Intent(AddNewAddressActivity.this, ChangeAddressActivity.class);
                    i.putExtra("address",  info);
                    startActivity(i);

                }

            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
