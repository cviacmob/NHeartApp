package com.cviac.nheart.nheartapp.activities;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.CartItemAdapter;
import com.cviac.nheart.nheartapp.datamodel.CartItemInfo;
import com.cviac.nheart.nheartapp.datamodel.CartTotalInfo;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.ProductCartInfo;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static android.support.v7.appcompat.R.styleable.View;

public class CartItemListActivity extends AppCompatActivity {

    ListView lv;
    CartItemInfo root;
    List<ProductCartInfo> cartProducts;
    CartItemAdapter adapter;
    List<CartTotalInfo> cartTotals;
    TextView total;

    static Button notifCount;
    static int mNotifCount = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cartProducts = new ArrayList<>();
        loadCartItems();

        total = (TextView)  findViewById(R.id.totalamout);
        adapter = new CartItemAdapter(this, R.layout.activity_catogry,cartProducts);
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CartItemInfo pinfo = root.getSublist().get(i);
//                Intent ssa = new Intent(GridViewone.this,
//                        Fullscreen.class);
//                ssa.putExtra("pinfo", pinfo);
//                startActivity(ssa);

            }
        });
    }

    private void loadCartItems() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com/index.php?route=api/cart/products")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        String token = Prefs.getString("token",null);
        Call<GetCartItemsResponse> call = api.getCartItems(token);
        call.enqueue(new Callback<GetCartItemsResponse>() {

            public void onResponse(Response<GetCartItemsResponse> response, Retrofit retrofit) {
                GetCartItemsResponse rsp = response.body();
                cartProducts.addAll(rsp.getProds());
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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        View count = menu.findItem(R.id.badge).getActionView();
//        notifCount = (Button) count.findViewById(R.id.notif_count);
//        notifCount.setText(String.valueOf(mNotifCount));
//        return super.onCreateOptionsMenu(menu);
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }




}


