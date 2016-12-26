package com.cviac.nheart.nheartapp.activities;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.AddToCartResponse;
import com.cviac.nheart.nheartapp.datamodel.LoginResponse;
import com.cviac.nheart.nheartapp.datamodel.Product;
import com.cviac.nheart.nheartapp.datamodel.ProductDetail;
import com.cviac.nheart.nheartapp.datamodel.Productdetailresponse;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ProductdetailsActivity extends AppCompatActivity {


    private TextView tv, tv2, text1, text2, tv3, tv4;
    private Button b, b2, Buttoncart,cart;
    private ImageView iv1;
    private RatingBar rating;
    ActionBar actionBar;
    static int count = 0;

    ProductDetail prdetail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        Product probj = (Product) i.getSerializableExtra("productobj");

        setTitle(" ");


        tv = (TextView) findViewById(R.id.name);
        iv1 = (ImageView) findViewById(R.id.image);
        tv2 = (TextView) findViewById(R.id.descr);
        //text1=(TextView) findViewById(R.id.price);
        // text2=(TextView) findViewById(R.id.old);
        Button addtocartbutton = (Button) findViewById(R.id.addtocart);
        addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(prdetail.getProduct_id(),"1");
            }
        });


        Button buybutton = (Button) findViewById(R.id.buy);

        text1 = (TextView) findViewById(R.id.new1);
        text1.setText("₹ 25");
        text2 = (TextView) findViewById(R.id.old);

        text2.setPaintFlags(text2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        text2.setText("₹ 45");

//        text1.setText("₹ 25");
//
//        text2.setPaintFlags(text2.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//        text2.setText("₹ 45");
        //  rating=(RatingBar) findViewById(R.id.ratingBar2);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.133")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<Productdetailresponse> call = api.getProductdetails(probj.getProduct_id());
        call.enqueue(new Callback<Productdetailresponse>() {

            public void onResponse(Response<Productdetailresponse> response, Retrofit retrofit) {
                Productdetailresponse rsp = response.body();
                prdetail = rsp.getProduct().get(0);
                String detail = prdetail.getDescription();
//                CharSequence squence = Html.fromHtml(detail);
//                SpannableStringBuilder strbuilder = new SpannableStringBuilder(squence);
                tv2.setText(detail);
                tv.setText(prdetail.getName());
//                text1.setText(prdetail.getPrice());
//                text2.setText(prdetail.getDiscounts());


                String url = prdetail.getThumb();
                Picasso.with(ProductdetailsActivity.this).load(url).resize(500, 500).into(iv1);
            }

            @Override
            public void onFailure(Throwable t) {
                prdetail = null;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent h = new Intent(ProductdetailsActivity.this, CartItemListActivity.class);
                startActivity(h);

                break;
        }
        return true;
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_category).setVisible(false);

        super.onPrepareOptionsMenu(menu);

        return true;
    }

    private void addToCart(String prodId, String quantity) {
        String token = Prefs.getString("token",null);
        if (token != null ) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.133")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenCartAPI api = retrofit.create(OpenCartAPI.class);

            final Call<AddToCartResponse> call = api.addToCart(token,prodId, quantity);
            call.enqueue(new Callback<AddToCartResponse>() {
                @Override
                public void onResponse(Response<AddToCartResponse> response, Retrofit retrofit) {
                    AddToCartResponse rsp = response.body();
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        actionmethod();
    }

    public void actionmethod() {
        actionBar =  getSupportActionBar();
        if (actionBar != null) {
// Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B5CD1")));
            View customView = getLayoutInflater().inflate(R.layout.actionbartitle, null);
            tv=(TextView) customView.findViewById(R.id.count);
//            tv1 =(TextView ) customView.findViewById(R.id.title);
//
            cart=(Button)customView.findViewById(R.id.cartbutoon);
//            tv1.setText("Cart Items");
            actionBar.setCustomView(customView);


        }

    }

}