package com.cviac.nheart.nheartapp.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
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
import android.widget.Toast;

import com.cviac.nheart.nheartapp.NheartApp;
import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.AddToCartResponse;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.LoginResponse;
import com.cviac.nheart.nheartapp.datamodel.Product;
import com.cviac.nheart.nheartapp.datamodel.ProductDetail;
import com.cviac.nheart.nheartapp.datamodel.Productdetailresponse;
import com.cviac.nheart.nheartapp.restapi.AddCookiesInterceptor;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.ReceivedCookiesInterceptor;
import com.cviac.nheart.nheartapp.utilities.BadgeDrawable;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.StreamHandler;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ProductdetailsActivity extends AppCompatActivity {


    private TextView tv, tv2, text1, text2, tv3, tv4;
    private Button b, b2, Buttoncart, cart;
    private ImageView iv1,additionimage;
    private RatingBar rating;
    ActionBar actionBar;
    static int count = 0;
    private SliderLayout imageSlider;
    ProductDetail prdetail=null ;

    private LayerDrawable mcartMenuIcon;
    private int mCartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        final Product probj = (Product) i.getSerializableExtra("productobj");

        setTitle(" ");


        tv = (TextView) findViewById(R.id.name);
        iv1 = (ImageView) findViewById(R.id.image);
        tv2 = (TextView) findViewById(R.id.descr);
        text1 = (TextView) findViewById(R.id.new1);
        text2 = (TextView) findViewById(R.id.discount);
//        additionimage=(ImageView) findViewById(R.id.additionimage) ;
        Button addtocartbutton = (Button) findViewById(R.id.addtocart);
        addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prdetail != null) {
                    addToCart(prdetail.getProduct_id(), "1");
                }
            }
        });


        Button buybutton = (Button) findViewById(R.id.buy);

        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(prdetail.getProduct_id(), "1");
                Intent h = new Intent(ProductdetailsActivity.this, ContinueActivity.class);
                startActivity(h);
            }
        });


        text2.setPaintFlags(text2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        // text2.setText("₹ 45");

//        text1.setText("₹ 25");
//
//        text2.setPaintFlags(text2.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//        text2.setText("₹ 45");
          rating=(RatingBar) findViewById(R.id.ratingBar2);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<Productdetailresponse> call = api.getProductdetails(probj.getProduct_id());
        call.enqueue(new Callback<Productdetailresponse>() {

            public void onResponse(Response<Productdetailresponse> response, Retrofit retrofit) {
                Productdetailresponse rsp = response.body();
                    prdetail = rsp.getProduct().get(0);
                    String detail = prdetail.getDescription();
                    tv2.setText(Html.fromHtml(Html.fromHtml(detail).toString()));
                    tv.setText(prdetail.getName());
                    text1.setText(prdetail.getPrice());
                    text2.setText(prdetail.getSpecial());
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
        mcartMenuIcon = (LayerDrawable) menu.findItem(R.id.action_cart).getIcon();
        menu.findItem(R.id.action_call).setVisible(false);
        menu.findItem(R.id.loc).setVisible(false);
        setBadgeCount(this, mcartMenuIcon, "");
        getAndSetCartCount();
        return true;
    }


    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_cart_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_cart_badge, badge);
    }

    private void getAndSetCartCount() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        String token = Prefs.getString("token", null);
        Call<GetCartItemsResponse> call = api.getCartItems();
        call.enqueue(new Callback<GetCartItemsResponse>() {

            public void onResponse(Response<GetCartItemsResponse> response, Retrofit retrofit) {
                GetCartItemsResponse rsp = response.body();
                if (rsp != null) {
                    mCartCount = rsp.getProds().size();
                    setBadgeCount(ProductdetailsActivity.this, mcartMenuIcon, mCartCount + "");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
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
        menu.findItem(R.id.action_search).setVisible(false);

        super.onPrepareOptionsMenu(menu);

        return true;
    }

    private void addToCart(String prodId, String quantity) {
        String token = Prefs.getString("token", null);
        // if (token != null)
        {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
            okHttpClient.interceptors().add(new AddCookiesInterceptor());
            okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.domainname))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            OpenCartAPI api = retrofit.create(OpenCartAPI.class);

            final Call<AddToCartResponse> call = api.addToCart(prodId, quantity);
            call.enqueue(new Callback<AddToCartResponse>() {
                @Override
                public void onResponse(Response<AddToCartResponse> response, Retrofit retrofit) {
                    AddToCartResponse rsp = response.body();
                    getAndSetCartCount();
                    Toast.makeText(ProductdetailsActivity.this, "Added to Cart", Toast.LENGTH_LONG).show();

                    NheartApp app = (NheartApp) getApplication();
                    app.notifyCartChange("add");
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(ProductdetailsActivity.this, "Server Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}