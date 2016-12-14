package com.cviac.nheart.nheartapp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.GridView;
import android.widget.*;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.ServiceinfoAdapter;

import com.cviac.nheart.nheartapp.datamodel.ServiceInfo;

public class GridViewone extends Activity {

    GridView grid;
    ServiceInfo root;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);

        loadServices();

        ServiceinfoAdapter adapter = new ServiceinfoAdapter(GridViewone.this, R.layout.program_layout, root.getSublist());
        grid = (GridView) findViewById(R.id.gridView1);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ServiceInfo pinfo = root.getSublist().get(i);
//                Intent ssa = new Intent(GridViewone.this,
//                        Fullscreen.class);
//                ssa.putExtra("pinfo", pinfo);
//                startActivity(ssa);

            }
        });
    }

    private void loadServices() {

        root = new ServiceInfo(0, "root", "String", "String", "String1");

        ServiceInfo ch1 = new ServiceInfo(R.mipmap.two, "ROSE", "ByCviac", "₹ 25", "₹ 45");
        root.add(ch1);


        ServiceInfo ch2 = new ServiceInfo(R.mipmap.fthree, "LILLY", "ByCviac", "₹ 35", "₹ 55");
        root.add(ch2);


        ServiceInfo ch3 = new ServiceInfo(R.mipmap.ffour, "JASMINE", "ByCviac", "₹ 65", "₹ 75");
        root.add(ch3);


        ServiceInfo ch4 = new ServiceInfo(R.mipmap.ffive, "LOTUS", "ByCviac", "₹ 35", "₹ 45");
        root.add(ch4);


        ServiceInfo ch5 = new ServiceInfo(R.mipmap.fsix, "ROSE", "ByCviac", "₹ 100", "₹ 115");
        root.add(ch5);

        ServiceInfo ch6 = new ServiceInfo(R.mipmap.fseven, "LILLY", "ByCviac", "₹ 115", "₹ 150");
        root.add(ch6);

        ServiceInfo ch7 = new ServiceInfo(R.mipmap.ffour, "JASMINE", "ByCviac", "₹ 150", "₹ 175");
        root.add(ch7);

        ServiceInfo ch8 = new ServiceInfo(R.mipmap.ffive, "ROSE", "ByCviac", "₹ 35", "₹ 75");
        root.add(ch8);

        ServiceInfo ch9 = new ServiceInfo(R.mipmap.fthree, "JASMINE", "ByCviac", "₹ 35", "₹ 45");
        root.add(ch9);

        ServiceInfo ch10 = new ServiceInfo(R.mipmap.ffour, "LILLY", "ByCviac", "₹ 85", "₹ 95");
        root.add(ch10);

        ServiceInfo ch11 = new ServiceInfo(R.mipmap.feight, "ROSE", "ByCviac", "₹ 25", "₹ 35");
        root.add(ch11);

        ServiceInfo ch12 = new ServiceInfo(R.mipmap.fsix, "LOTUS", "ByCviac", "₹ 15", "₹ 25");
        root.add(ch12);


    }


}