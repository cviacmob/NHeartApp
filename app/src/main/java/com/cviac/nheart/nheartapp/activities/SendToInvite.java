package com.cviac.nheart.nheartapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;


public class SendToInvite extends AppCompatActivity {
    Button b;
  EditText ed;
    String str,str1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contt);
      ed=(EditText)findViewById(R.id.num);
      setTitle("Invite");
        str= Prefs.getString("Phone","");


        b = (Button) findViewById(R.id.getin);


        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                String mbno=ed.getText().toString();
                Prefs.putString("Phone",mbno);
                if(mbno.equals(str)){
                    Toast.makeText(SendToInvite.this,"send valid number",Toast.LENGTH_LONG).show();
                }
                 else{
                    Intent btn = new Intent(SendToInvite.this,
                            InvitationReceived.class);
                    startActivity(btn);
                    finish();
                }
               // finish();

            }
        });

    }
}
