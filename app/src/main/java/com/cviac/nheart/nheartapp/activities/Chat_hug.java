package com.cviac.nheart.nheartapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.Manifest;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;


import com.cviac.nheart.nheartapp.NheartApp;
import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.ChatMessageAdapter;
import com.cviac.nheart.nheartapp.adapters.CircleTransform;
import com.cviac.nheart.nheartapp.adapters.ConvMessageAdapter;
import com.cviac.nheart.nheartapp.datamodel.ChatMsg;
import com.cviac.nheart.nheartapp.datamodel.ConvMessage;
import com.cviac.nheart.nheartapp.datamodel.HugInfo;
import com.cviac.nheart.nheartapp.xmpp.XMPPService;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat_hug extends AppCompatActivity implements View.OnClickListener {

    android.support.v7.app.ActionBar actionBar;


    private BroadcastReceiver messageReceiver;
    private ListView mListView;
    private ImageButton mButtonSend;
    private EditText mEditTextMessage;

    private static final int MY_PERMISSION_CALL_PHONE = 10;
    public static Chat chat;
    private ChatMessageAdapter mAdapter;
    HugInfo hug;
    String ss1, mob;
    private ListView lv;
    private ImageButton img;
    private EditText edittxt;
    private String geteditmgs;
    String converseId, msgid;
    private TextView customTitle, customduration;
    private ImageView customimage, customimageback;
    private List<ConvMessage> chats;
    private ConvMessageAdapter chatAdapter;
    String mynum, tonum, myname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_virtual);

        lv = (ListView) findViewById(R.id.listViewChat);
        lv.setDivider(null);
        img = (ImageButton) findViewById(R.id.sendbutton);
        edittxt = (EditText) findViewById(R.id.editTextsend);

        Intent i = getIntent();
        hug = (HugInfo) i.getSerializableExtra("mob");

        mynum = Prefs.getString("mobile", "");
        myname = Prefs.getString("name", "");
        tonum = hug.getMob();
       /* mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);*/

        final String MyPREFERENCES = "MyPrefs";
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);




        mob = hug.getMob();
        ss1 = hug.getTitle();
        setTitle(ss1);

        actionmethod();

        loadConvMessages();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geteditmgs = edittxt.getText().toString();
                if (!geteditmgs.equals("")) {
                    String converseId = getNormalizedConverseId(mynum, tonum);
                    msgid = getMsgID();
                    com.cviac.nheart.nheartapp.xmpp.ChatMessage chat = new com.cviac.nheart.nheartapp.xmpp.ChatMessage(converseId, mynum, tonum, geteditmgs, msgid, true);
                    chat.setSenderName(myname);
                    XMPPService.sendMessage(chat);
                    saveChatMessage(chat);
                    edittxt.getText().clear();

                    ChatMsg msg = new ChatMsg();
                    msg.setSenderid(mynum);
                    msg.setSendername(myname);
                    msg.setMsg(geteditmgs);
                    msg.setMsgid(msgid);
                    msg.setReceiverid(tonum);
                    // checkAndSendPushNotfication(conv.getEmpid(), msg);
                }


            }


        });
        NheartApp app = (NheartApp)getApplication();
        //app.setChatFrag(this);


        messageReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                loadConvMessages();


            }
        };
        registerReceiver(messageReceiver,new IntentFilter("XMPPConnection"));
    }

    private String getMsgID() {

        return System.currentTimeMillis() + "";

    }

    private void loadConvMessages() {
        converseId = getNormalizedConverseId(mynum, tonum);
        chats = ConvMessage.getAll(converseId);
        chatAdapter = new ConvMessageAdapter(chats,getApplicationContext());
        lv.setAdapter(chatAdapter);
    }

    public String getConverseId() {
        return converseId;
    }

    private String getNormalizedConverseId(String myid, String receverid) {
        if (myid.compareTo(receverid) > 0) {
            return myid + "_" + receverid;
        }
        return receverid + "_" + myid;
    }

    public void addInMessage(ConvMessage msg) {
        // chats.add(msg);
        loadConvMessages();
        chatAdapter.notifyDataSetChanged();
    }

    public void reload() {
        loadConvMessages();
        chatAdapter.notifyDataSetChanged();
    }

    public void saveChatMessage(com.cviac.nheart.nheartapp.xmpp.ChatMessage msg) {
        ConvMessage cmsg = new ConvMessage();
        cmsg.setMsg(msg.msg);
        cmsg.setCtime(new Date());
        cmsg.setConverseid(msg.converseid);
        cmsg.setSenderName(msg.senderName);
        cmsg.setReceiver(msg.receiver);
        cmsg.setSender(msg.sender);
        cmsg.setMsgid(msg.msgid);
        cmsg.setMine(msg.isMine);
        cmsg.setMine(true);
        cmsg.setStatus(1);
        cmsg.save();
        chats.add(cmsg);
        chatAdapter.notifyDataSetChanged();
    }


    public void onDestroy() {
        super.onDestroy();
        NheartApp app = (NheartApp)getApplication();
        unregisterReceiver(messageReceiver);
        app.setChatFrag(null);

    }


    public void actionmethod() {

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B24496")));

            View customView = getLayoutInflater().inflate(R.layout.actionbar_hug, null);
            customimage = (ImageView) customView.findViewById(R.id.imageViewcustom1);

            Picasso.with(this).load(R.mipmap.docter).resize(100, 100).transform(new CircleTransform())

                    .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(customimage);

            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle1);

            customTitle.setText(ss1);
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
                ImageView cuscall = (ImageView) findViewById(R.id.ivcall);
                onClick(cuscall);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tonum));
        if (ContextCompat.checkSelfPermission(this, (android.Manifest.permission.CALL_PHONE))
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Chat_hug.this, new String[]{android.Manifest.permission.CALL_PHONE}, MY_PERMISSION_CALL_PHONE);
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callintent = new Intent(Intent.ACTION_CALL);
                    callintent.setData(Uri.parse("tel:" + tonum));
                    if (ActivityCompat.checkSelfPermission(Chat_hug.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callintent);
                }
            }
        }
    }
}