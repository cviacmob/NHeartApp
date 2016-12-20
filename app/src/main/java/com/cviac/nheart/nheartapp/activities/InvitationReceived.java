package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cviac.nheart.nheartapp.R;


public class InvitationReceived extends AppCompatActivity {

    Button b1,b2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        setTitle("Received Invitation ");
        b1 = (Button) findViewById(R.id.accept);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent mainIntent = new Intent(InvitationReceived.this, MainActivity
                        .class);
                startActivity(mainIntent);
                finish();

            }
        });
        b2=(Button) findViewById(R.id.cancel);

        b2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent mainIntent = new Intent(InvitationReceived.this, MainActivity
                        .class);
                startActivity(mainIntent);
                finish();

            }
        });
            }
}