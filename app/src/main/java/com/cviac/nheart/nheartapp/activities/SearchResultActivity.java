package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.Productsadapter;
import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;
import com.cviac.nheart.nheartapp.datamodel.Product;
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

public class SearchResultActivity extends AppCompatActivity {

    GridView gv;
    List<Product> rowListItem;
    Productsadapter adapter;
    String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String catid = i.getStringExtra("categoryid");
        String catname = i.getStringExtra("categoryname");

        setTitle(catname);

        gv = (GridView) findViewById(R.id.searchgrid);
        gv.setFastScrollEnabled(true);
        rowListItem = new ArrayList<>();
        adapter = new Productsadapter(SearchResultActivity.this, rowListItem);
        gv.setAdapter(adapter);

        getProducts(catid);
    }

    public void getProducts(String catId) {

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

        final Call<CategoryProductsResponse> call = api.getProducts(catId);

        call.enqueue(new Callback<CategoryProductsResponse>() {
            @Override
            public void onResponse(Response<CategoryProductsResponse> response, Retrofit retrofit) {
                CategoryProductsResponse rsp = response.body();
                rowListItem.clear();
                rowListItem.addAll(rsp.getProducts());
                adapter.notifyDataSetInvalidated();
            }

            @Override
            public void onFailure(Throwable t) {
                rowListItem = null;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Product pr = rowListItem.get(pos);
                Intent prd = new Intent(SearchResultActivity.this, ProductdetailsActivity.class);
                prd.putExtra("productobj", pr);
                startActivity(prd);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}