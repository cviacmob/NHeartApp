package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.AddressAdapter;
import com.cviac.nheart.nheartapp.datamodel.Addressinfo;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ChangeAddressActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3;
    List<Addressinfo> addhis;
    ListView lv;
    FloatingActionButton fab;
    AddressAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chnageaddress);


        loadAddresses();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String aname = Prefs.getString("Regname", "User_Name");
        String amail = Prefs.getString("Regmail", "User_Email");
        String aphone = Prefs.getString("Regphone", "User_Phone");

        tv1 = (TextView) findViewById(R.id.uname);
        tv2 = (TextView) findViewById(R.id.umail);
        tv3 = (TextView) findViewById(R.id.uphone);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        tv1.setText(aname);
        tv2.setText(amail);
        tv3.setText(aphone);

        lv = (ListView) findViewById(R.id.addlst);
        addhis = new ArrayList<>();
        adapter1 = new AddressAdapter(ChangeAddressActivity.this, addhis);
        lv.setAdapter(adapter1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addaddr = new Intent(ChangeAddressActivity.this, AddNewAddressActivity.class);
                startActivityForResult(addaddr, 140);
            }
        });
    }

    public void loadAddresses() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        int c_id = Prefs.getInt("customer_id", -1);
        Call<List<Addressinfo>> call = api.getAdresses(c_id + "");
        call.enqueue(new Callback<List<Addressinfo>>() {

            public void onResponse(Response<List<Addressinfo>> response, Retrofit retrofit) {
                List<Addressinfo> rsp = response.body();
                addhis.clear();
                addhis.addAll(rsp);
                adapter1.notifyDataSetInvalidated();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 140) {
            loadAddresses();
        } else if (requestCode == 141) {
            loadAddresses();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}