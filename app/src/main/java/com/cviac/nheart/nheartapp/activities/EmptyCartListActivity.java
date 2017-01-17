package com.cviac.nheart.nheartapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.cviac.nheart.nheartapp.R;

public class EmptyCartListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emptycartlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Cart Items");
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}






