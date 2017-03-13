package com.cviac.nheart.nheartapp.activities;

/**
 * Created by user on 2/9/2017.
 */
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.alicebot.ab.Timer;


import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.ChatMessageAdapter;
import com.cviac.nheart.nheartapp.activities.ChatMessage;
import com.cviac.nheart.nheartapp.adapters.CircleTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Skezo_Main extends AppCompatActivity implements View.OnClickListener  {


    android.support.v7.app.ActionBar actionBar;

    private ListView mListView;
    private ImageButton mButtonSend;
    private EditText mEditTextMessage;
    TextView presenceText;
    private ImageView mImageView, customimage;
    public Bot bot;
    public static Chat chat;
    private ChatMessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_virtual);
        mListView = (ListView) findViewById(R.id.listViewChat);
        mButtonSend = (ImageButton) findViewById(R.id.sendbutton);
        mEditTextMessage = (EditText) findViewById(R.id.editTextsend);
        //mImageView = (ImageView) findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);
        actionmethod();
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString();
                //bot
                String response = chat.multisentenceRespond(mEditTextMessage.getText().toString());
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                sendMessage(message);
                mimicOtherMessage(response);
                mEditTextMessage.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
        //checking SD card availablility
        boolean a = isSDCARDAvailable();
        //receiving the assets from the app directory
        AssetManager assets = getResources().getAssets();
        File jayDir = new File(Environment.getExternalStorageDirectory().toString() + "/hari/bots/Hari");
        boolean b = jayDir.mkdirs();
        if (jayDir.exists()) {
            //Reading the file
            try {
                for (String dir : assets.list("Hari")) {
                    File subdir = new File(jayDir.getPath() + "/" + dir);
                    boolean subdir_check = subdir.mkdirs();
                    for (String file : assets.list("Hari/" + dir)) {
                        File f = new File(jayDir.getPath() + "/" + dir + "/" + file);
                        if (f.exists()) {
                            f.delete();
                        }
                        InputStream in = null;
                        OutputStream out = null;
                        in = assets.open("Hari/" + dir + "/" + file);
                        out = new FileOutputStream(jayDir.getPath() + "/" + dir + "/" + file);
                        //copy file from assets to the mobile's SD card or any secondary memory
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //get the working directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/hari";
        System.out.println("Working Directory = " + MagicStrings.root_path);
        AIMLProcessor.extension = new PCAIMLProcessorExtension();
        //Assign the AIML files to bot for processing
        bot = new Bot("Hari", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
        String[] args = null;
        mainFunction(args);

    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);

        //mimicOtherMessage(message);
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }

    //check SD card availability
    public static boolean isSDCARDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
    }

    //copying the file
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    //Request and response of user and the bot
    public static void mainFunction(String[] args) {
        MagicBooleans.trace_mode = false;
        System.out.println("trace mode = " + MagicBooleans.trace_mode);
        Graphmaster.enableShortCuts = true;
        Timer timer = new Timer();
        String request = "Hello.";
        String response = chat.multisentenceRespond(request);

        System.out.println("Human: " + request);
        System.out.println("Robot: " + response);
    }


    @Override
    public void onClick(View v) {

    }

    public void actionmethod() {

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#811617")));

            View customView = getLayoutInflater().inflate(R.layout.actionbar_hug, null);
            customimage = (ImageView) customView.findViewById(R.id.imageViewcustom1);


            // presenceText = (TextView) customView.findViewById(R.id.textView51);

           /* Picasso.with(this).load(R.drawable.ic_call).resize(120, 100).transform(new CircleTransform())
                    .into(cuscall);*/


            Picasso.with(this).load(R.mipmap.girlone).resize(100, 100).transform(new CircleTransform())


                    .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(customimage);

            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle1);

            customTitle.setText("Libo");
            actionBar.setCustomView(customView);
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}



