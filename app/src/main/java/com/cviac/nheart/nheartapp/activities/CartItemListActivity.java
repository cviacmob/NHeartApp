package com.cviac.nheart.nheartapp.activities;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.CartItemAdapter;
import com.cviac.nheart.nheartapp.datamodel.CartTotalInfo;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.ProductCartInfo;
import com.cviac.nheart.nheartapp.datamodel.ProductDetail;
import com.cviac.nheart.nheartapp.datamodel.Productdetailresponse;
import com.cviac.nheart.nheartapp.restapi.AddCookiesInterceptor;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.ReceivedCookiesInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CartItemListActivity extends AppCompatActivity{

    ListView lv;
    List<ProductCartInfo> cartProducts;
    CartItemAdapter adapter;
    List<CartTotalInfo> cartTotals;
    TextView total,tv;
    String s;

    android.support.v7.app.ActionBar actionBar;
     Button cartcontinue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        // setTitle("Cart Items");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cartProducts = new ArrayList<>();
        loadCartItems();
        actionmethod();

        total = (TextView)  findViewById(R.id.totalamout);
        adapter = new CartItemAdapter(this, R.layout.activity_catogry,cartProducts);
        lv = (ListView) findViewById(R.id.cartlist);
        lv.setAdapter(adapter);
        cartcontinue=(Button)findViewById(R.id.Continue);
        cartcontinue.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(CartItemListActivity.this,ContinueActivity.class);
                startActivity(mainIntent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductCartInfo pinfo = cartProducts.get(i);
                getProduct(pinfo.getProduct_id());
            }
        });
    }

    private void getProduct(String productId) {

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

        Call<Productdetailresponse> call = api.getProductdetails(productId);
        call.enqueue(new Callback<Productdetailresponse>() {

            public void onResponse(Response<Productdetailresponse> response, Retrofit retrofit) {
                Productdetailresponse rsp = response.body();
                ProductDetail prdet = rsp.getProduct().get(0);
                Intent i=new Intent(CartItemListActivity.this, ProductdetailsActivity.class);
                i.putExtra("productobj",  prdet);
                startActivity(i);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void loadCartItems() {

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

        String token = Prefs.getString("token",null);
        Call<GetCartItemsResponse> call = api.getCartItems();
        call.enqueue(new Callback<GetCartItemsResponse>() {

            public void onResponse(Response<GetCartItemsResponse> response, Retrofit retrofit) {
                GetCartItemsResponse rsp = response.body();
                cartProducts.addAll(rsp.getProds());
                s= String.valueOf(rsp.getProds().size());
                tv.setText(s);
                adapter.notifyDataSetInvalidated();
                cartTotals = rsp.getTotals();

                total.setText(cartTotals.get(cartTotals.size()-1).getText());

            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });


//        root = new CartItemInfo(0, "root", "String", "String");
//
//        CartItemInfo ch1 = new CartItemInfo(R.mipmap.two, "ROSE", "ByCviac", "₹ 25");
//        root.add(ch1);
//
//
//        CartItemInfo ch2 = new CartItemInfo(R.mipmap.fthree, "LILLY", "ByCviac", "₹ 35");
//        root.add(ch2);
//
//
//        CartItemInfo ch3 = new CartItemInfo(R.mipmap.ffour, "JASMINE", "ByCviac", "₹ 65");
//        root.add(ch3);
//
//
//        CartItemInfo ch4 = new CartItemInfo(R.mipmap.ffive, "LOTUS", "ByCviac", "₹ 35");
//        root.add(ch4);
//
//
//        CartItemInfo ch5 = new CartItemInfo(R.mipmap.fsix, "ROSE", "ByCviac", "₹ 100");
//        root.add(ch5);

//        ServiceInfo ch6 = new ServiceInfo(R.mipmap.fseven, "LILLY", "ByCviac", "₹ 115", "₹ 150");
//        root.add(ch6);
//
//        ServiceInfo ch7 = new ServiceInfo(R.mipmap.ffour, "JASMINE", "ByCviac", "₹ 150", "₹ 175");
//        root.add(ch7);
//
//        ServiceInfo ch8 = new ServiceInfo(R.mipmap.ffive, "ROSE", "ByCviac", "₹ 35", "₹ 75");
//        root.add(ch8);
//
//        ServiceInfo ch9 = new ServiceInfo(R.mipmap.fthree, "JASMINE", "ByCviac", "₹ 35", "₹ 45");
//        root.add(ch9);
//
//        ServiceInfo ch10 = new ServiceInfo(R.mipmap.ffour, "LILLY", "ByCviac", "₹ 85", "₹ 95");
//        root.add(ch10);
//
//        ServiceInfo ch11 = new ServiceInfo(R.mipmap.feight, "ROSE", "ByCviac", "₹ 25", "₹ 35");
//        root.add(ch11);
//
//        ServiceInfo ch12 = new ServiceInfo(R.mipmap.fsix, "LOTUS", "ByCviac", "₹ 15", "₹ 25");
//        root.add(ch12);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void actionmethod() {
        actionBar =  getSupportActionBar();
        if (actionBar != null) {
// Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B5CD1")));
            View customView = getLayoutInflater().inflate(R.layout.activity_fr, null);
            tv=(TextView) customView.findViewById(R.id.count);
            tv.setText(s);
            actionBar.setCustomView(customView);


        }

    }

}