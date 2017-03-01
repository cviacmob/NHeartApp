package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.CartItemAdapter;
import com.cviac.nheart.nheartapp.adapters.ContinueGridAdapter;
import com.cviac.nheart.nheartapp.datamodel.CartTotalInfo;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.ProductCartInfo;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.utilities.NonScrollableListview;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ContinueActivity extends AppCompatActivity {
    TextView tv1,tv2,tv3,total,pay;
    ListView lv;
    GridView gv;
    Button b1;
    RadioGroup rg;
    RadioButton rb,rb1,rb2,rb3;
    List<ProductCartInfo> cartProducts;
    List<CartTotalInfo> cartTotals;
    CartItemAdapter adapter;
    ContinueGridAdapter adpt;
    String address,add1,add2;

    @InjectView(R.id.paylist)
    NonScrollableListview nonScrollListView;

    //    String[] web = {
//            "Credit Card",
//            "Debit Card",
//            "Net Banking",
//            "Emi",
//            "Gift Voucher",
//            "Cash on Delivery",
//    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadCartItems();
        cartProducts = new ArrayList<>();
        adapter = new CartItemAdapter(this, R.layout.activity_catogry, cartProducts);
        nonScrollListView = (NonScrollableListview) findViewById(R.id.paylist);
        // adpt = new ContinueGridAdapter(ContinueActivity.this, web);
        // gv=(GridView)findViewById(R.id.grid);
        // gv.setAdapter(adpt);
        nonScrollListView.setAdapter(adapter);

        rb=(RadioButton)findViewById(R.id.Credit);
        rb1=(RadioButton)findViewById(R.id.Paytm);
        rb2=(RadioButton)findViewById(R.id.Debit);
        rb3=(RadioButton)findViewById(R.id.cash);
        total=(TextView)findViewById(R.id.amount);
        pay=(TextView)findViewById(R.id.pay);
        b1=(Button) findViewById(R.id.change);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent mainIntent = new Intent(ContinueActivity.this,ChangeAddressActivity.class);
                startActivity(mainIntent);
            }
        });
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
//            @Override
//           public void  onCheckedChanged(RadioGroup rg, int i){
//                String getName(){
//                    if(rb.isSelected()){
//                       return "credit";
//                   }else if(rb1.isSelected()){
//                       return "net";
//                   }else if(rb2.isSelected()){
//                        return "debit";
//                    }else if(rb3.isSelected()){
//                        return "emi";
//                    }else{
//                       return null;
//                   }
//                }
//           }
//        });

        tv1=(TextView)findViewById(R.id.addres1);
        tv2=(TextView)findViewById(R.id.addres2);
        tv3=(TextView)findViewById(R.id.addres3);
        address=Prefs.getString("Name","");
        add1= Prefs.getString("Email","");
        add2= Prefs.getString("Phone","");
        tv1.setText(address);
        tv2.setText(add1);
        tv3.setText(add2);

    }
    private void loadCartItems() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        String token = Prefs.getString("token",null);
        Call<GetCartItemsResponse> call = api.getCartItems(token);
        call.enqueue(new Callback<GetCartItemsResponse>() {

            public void onResponse(Response<GetCartItemsResponse> response, Retrofit retrofit) {
                GetCartItemsResponse rsp = response.body();
                cartProducts.addAll(rsp.getProds());
                // s= String.valueOf(rsp.getProds().size());
                //  tv.setText(s);
                adapter.notifyDataSetInvalidated();
                cartTotals = rsp.getTotals();

                total.setText(cartTotals.get(cartTotals.size()-1).getText());

            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
