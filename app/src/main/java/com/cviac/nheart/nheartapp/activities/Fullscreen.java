/*
package com.cviac.nheart.nheartapp.activities;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.ServiceInfo;

public class Fullscreen extends Activity {
    private TextView tv1, tv2, text1, tv3,tv4;
    private Button b, b2, Buttoncart;
    private ImageView iv1;
    private RatingBar rating;
    ServiceInfo pinfo;
    static int count = 0;
@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        actionmethod();

        //GridCatogry gd=new GridCatogry();
        // gd.actionmethod();
      */
/*  Intent inte = getIntent();
        ServiceInfo pinfo = (ServiceInfo) inte.getSerializableExtra("pinfo");
        iv1 = (ImageView) findViewById(R.id.i1);
        tv1 = (TextView) findViewById(R.id.t1);
        tv2 = (TextView) findViewById(R.id.t2);
        b2=(Button)findViewById(R.id.button);
        text1 = (TextView) findViewById(R.id.old);
        tv3 = (TextView) findViewById(R.id.t4);
        tv4 = (TextView) findViewById(R.id.badge_notification_2);
        tv3.setPaintFlags(tv3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        //b=(Button)findViewById(R.id.buy);

        tv1.setText(pinfo.getServiceNAME());
        iv1.setImageResource(pinfo.getImgID());
        tv2.setText(pinfo.getDesc());
        text1.setText(pinfo.getPrice());
        tv3.setText(pinfo.getPrice1());

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               count++;
                tv4 .setText(String.valueOf(count));
            }});*//*

    }
     *//*

*/
/*   b1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent mainIntent = new Intent(InvitationReceived.this, MenuActivity
                        .class);
                startActivity(mainIntent);
                finish();

            }
        });*//*
*/
/*



    public void actionmethod() {

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
// Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B5CD1")));

            View customView = getLayoutInflater().inflate(R.layout.activity_fr, null);
           *//*

*/
/*
            Picasso.with(mContext).load(R.drawable.cart).resize(110, 110).transform(new CircleTransform())
                    .into(customimage);     *//*
*/
/*


// Set the on click listener for the title

            Buttoncart = (Button) findViewById(R.id.carttbutton);
       *//*

*/
/*     Picasso.with(getApplicationContext())
                    .load(R.drawable.cart)
                    .resize(80, 80)
                    .centerInside()
                    .into(imageButtoncart);*//*
*/
/*


          *//*

*/
/*  imageButtoncart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
*//*
*/
/*


// Apply the custom view
            actionBar.setCustomView(customView);
        }
    }
}




*/
