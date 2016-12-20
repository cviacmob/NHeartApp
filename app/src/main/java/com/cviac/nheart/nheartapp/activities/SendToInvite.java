package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cviac.nheart.nheartapp.R;


public class SendToInvite extends AppCompatActivity {
    Button b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contt);

 setTitle("Invite");
        b = (Button) findViewById(R.id.getin);

        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent mainIntent = new Intent(SendToInvite.this, InvitationReceived
                        .class);
                startActivity(mainIntent);
                finish();

            }
        });
    }
}
