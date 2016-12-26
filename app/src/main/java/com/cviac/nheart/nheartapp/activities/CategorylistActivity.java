package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.CategoryAdapter;
import com.cviac.nheart.nheartapp.datamodel.CategoriesResponse;
import com.cviac.nheart.nheartapp.datamodel.Category;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CategorylistActivity extends AppCompatActivity {

    List<Category> categoryList;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylist);
        setTitle("Select a Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryList = new ArrayList<Category>();
// Category ct = new Category();
// ct.setName("Flowers");
// categoryList.add(ct);
        ListView vw = (ListView) findViewById(R.id.listview);
        vw.setDivider(null);
        adapter = new CategoryAdapter(this, categoryList);
        vw.setAdapter(adapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.133")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        final Call<CategoriesResponse> call = api.getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Response<CategoriesResponse> response, Retrofit retrofit) {
                CategoriesResponse rsp = response.body();
                categoryList.addAll(rsp.getCategories());
// adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }
            @Override
            public void onFailure(Throwable t) {
                categoryList = null;
            }
        });
        vw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Category cta = categoryList.get(pos);
                Intent i=new Intent();
                i.putExtra("categoryid",cta.getCategory_id());
                setResult(1000,i);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }





}