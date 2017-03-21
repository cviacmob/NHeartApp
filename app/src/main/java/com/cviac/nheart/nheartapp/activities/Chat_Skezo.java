//package com.cviac.nheart.nheartapp.activities;
//
//import android.Manifest;
//import android.app.ActionBar;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.content.res.Resources;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.text.InputType;
//import android.text.method.ScrollingMovementMethod;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.cviac.nheart.nheartapp.R;
//import com.cviac.nheart.nheartapp.adapters.CircleTransform;
//import com.cviac.nheart.nheartapp.datamodel.ChatMsg;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.picasso.MemoryPolicy;
//import com.squareup.picasso.Picasso;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import retrofit.Call;
//import retrofit.Callback;
//import retrofit.GsonConverterFactory;
//import retrofit.Response;
//import retrofit.Retrofit;
//
//public class Chat_Skezo extends AppCompatActivity implements View.OnClickListener {
//
//
//
//    android.support.v7.app.ActionBar actionBar;
//
//
//
//    ImageView customimageback, customimage, imgvwtick, cuscall;
//
//
//    RelativeLayout rp;
//    TextView txt, msgview, presenceText;
//    ImageButton sendbutton;
//    ListView lv;
//
//    ProgressDialog progressDialog;
//    private static final int MY_PERMISSION_CALL_PHONE = 10;
//
///*
//
//    private String getNormalizedConverseId(String myid, String receverid) {
//        if (myid.compareTo(receverid) > 0) {
//            return myid + "_" + receverid;
//        }
//        return receverid + "_" + myid;
//    }
//*/
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        sendbutton =(ImageButton)findViewById(R.id.sendbutton);
//        lv = (ListView) findViewById(R.id.listViewChat);
//        lv.setDivider(null);
//        lv.setDividerHeight(10);
//        actionmethod();
//        //chats = new ArrayList<ChatMessage>();
//
//
//        getSupportActionBar().show();
//        final String MyPREFERENCES = "MyPrefs";
//        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
//
//
//        Intent i = getIntent();
//
//        sendbutton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//                String msg = msgview.getText().toString();
//                if (msg.length() != 0) {
//                    ChatMsg mgsopj = new ChatMsg();
//                    mgsopj.setMsg(msg);
//
//                }
//            }
//        });
//
//
//
//    }
//
//
//
//
//
//
//
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    public void actionmethod() {
//
//        actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            // Disable the default and enable the custom
//            actionBar.setDisplayShowHomeEnabled(false);
//            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setDisplayShowCustomEnabled(true);
//            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9D0909")));
//
//            View customView = getLayoutInflater().inflate(R.layout.actionbar_hug, null);
//            customimage = (ImageView) customView.findViewById(R.id.imageViewcustom1);
//
//
//           // presenceText = (TextView) customView.findViewById(R.id.textView51);
//
//           /* Picasso.with(this).load(R.drawable.ic_call).resize(120, 100).transform(new CircleTransform())
//                    .into(cuscall);*/
//
//
//            Picasso.with(this).load(R.mipmap.girltwo).resize(100, 100).transform(new CircleTransform())
//
//
//                    .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(customimage);
//
//            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle1);
//
//            customTitle.setText("Tibo");
//            actionBar.setCustomView(customView);
//        }
//
//
//    }
//
//
//
//
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        onBackPressed();
//        return true;
//    }
//}
