package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.Productsadapter;
import com.cviac.nheart.nheartapp.datamodel.Category;
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

public class ProductlistActivity extends AppCompatActivity {

    List<Product> productList;
    Productsadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        Intent i= getIntent();
        Category catobj = (Category) i.getSerializableExtra("categoryobj");

        setTitle(catobj.getName());

        productList = new ArrayList<Product>();
//        Category ct = new Category();
//        ct.setName("Flowers");
//        categoryList.add(ct);
        GridView vw = (GridView) findViewById(R.id.gridView1);
        adapter = new Productsadapter(this, productList);
        vw.setAdapter(adapter);


        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
//        okHttpClient.interceptors().add(new AddCookiesInterceptor());
//        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<CategoryProductsResponse> call = api.getProducts(catobj.getCategory_id());
        call.enqueue(new Callback<CategoryProductsResponse>() {

            public void onResponse(Response<CategoryProductsResponse> response, Retrofit retrofit) {
                CategoryProductsResponse rsp = response.body();
                productList.addAll(rsp.getProducts());
                // adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }

            @Override
            public void onFailure(Throwable t) {
                productList = null;
            }
        });
        vw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Product pr = productList.get(pos);
                Intent i=new Intent(ProductlistActivity.this, ProductdetailsActivity.class);
                i.putExtra("productobj",  pr);
                startActivity(i);
            }
        });

    }

}
