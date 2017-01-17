package com.cviac.nheart.nheartapp.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.CircleTransform;
import com.cviac.nheart.nheartapp.datamodel.ChatMsg;
import com.cviac.nheart.nheartapp.datamodel.HugInfo;
import com.cviac.nheart.nheartapp.fragments.HugFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Chat_hug extends AppCompatActivity implements View.OnClickListener {


    android.support.v7.app.ActionBar actionBar;


    private List<HugInfo> huglist;
    ImageView customimageback, customimage, imgvwtick;
    HugInfo hug;
    HugInfo hugInfo;
    RelativeLayout rp;
    TextView txt, msgview, presenceText;
    ImageButton sendbutton;
    ListView lv;
    boolean isCallEnabled=false;
    String callnum="";

    ProgressDialog progressDialog;
    private static final int MY_PERMISSION_CALL_PHONE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_hug);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sendbutton = (ImageButton) findViewById(R.id.sendbutton1);
        lv = (ListView) findViewById(R.id.listViewHug);
        lv.setDivider(null);
        lv.setDividerHeight(10);
        actionmethod();

        //chats = new ArrayList<ChatMessage>();

        final String MyPREFERENCES = "MyPrefs";
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);


        Intent i = getIntent();
        hug=(HugInfo)i.getSerializableExtra("mob");
        //callnum=hug.getMob();
        String ss=hug.getMob();

        //String sn = (String) i.getSerializableExtra("mob");
        //Bundle b = i.getExtras();
        //if(b!=null) {
        // String j = (String) b.get("mob");

        //}

        sendbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                String msg = msgview.getText().toString();
                if (msg.length() != 0) {
                    ChatMsg mgsopj = new ChatMsg();
                    mgsopj.setMsg(msg);

                }
            }
        });


    }


    public void actionmethod() {

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D7017E")));

            View customView = getLayoutInflater().inflate(R.layout.actionbar_hug, null);
            customimage = (ImageView) customView.findViewById(R.id.imageViewcustom1);
            //customimageback = (ImageView) customView.findViewById(R.id.imageViewback1);
            //cuscall = (ImageView)findViewById(R.id.ivcall);

            presenceText = (TextView) customView.findViewById(R.id.textView51);

           /* Picasso.with(this).load(R.drawable.ic_call).resize(120, 100).transform(new CircleTransform())
                    .into(cuscall);*/


            Picasso.with(this).load(R.mipmap.girlone).resize(100, 100).transform(new CircleTransform())


                    .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(customimage);

            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle1);
          /*  customimageback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        Intent i = new Intent(Chat_hug.this, MainActivity.class);
                        startActivity(i);

                    finish();
                }
            });*/
            customTitle.setText("Hug");
            actionBar.setCustomView(customView);
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        switch (item.getItemId()) {
            case R.id.progress1:
                 ImageButton cuscall = (ImageButton) findViewById(R.id.progress1);
                onClick(cuscall);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        Intent callintent = new Intent(Intent.ACTION_CALL);
        callintent.setData(Uri.parse("tel:" + hug.getMob()));
        if (ActivityCompat.checkSelfPermission(Chat_hug.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Chat_hug.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_CALL_PHONE);
            return;
        }
        startActivity(callintent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callintent = new Intent(Intent.ACTION_CALL);
                    callintent.setData(Uri.parse("tel:" + hug.getMob()));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callintent);
                }
            }
        }
    }
}
