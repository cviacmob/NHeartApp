package com.cviac.nheart.nheartapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.CatogryAdapter;
import com.cviac.nheart.nheartapp.adapters.ServiceinfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.Addcartinfo;
import com.cviac.nheart.nheartapp.datamodel.ServiceInfo;

public class AddtocartActivity extends AppCompatActivity {

  ListView lv;
    Addcartinfo root;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);

        loadServices();

        CatogryAdapter adapter = new CatogryAdapter(AddtocartActivity.this, R.layout.activity_catogry, root.getSublist());
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Addcartinfo pinfo = root.getSublist().get(i);
//                Intent ssa = new Intent(GridViewone.this,
//                        Fullscreen.class);
//                ssa.putExtra("pinfo", pinfo);
//                startActivity(ssa);

            }
        });
    }

    private void loadServices() {

        root = new Addcartinfo(0, "root", "String", "String");

        Addcartinfo ch1 = new Addcartinfo(R.mipmap.two, "ROSE", "ByCviac", "₹ 25");
        root.add(ch1);


        Addcartinfo ch2 = new Addcartinfo(R.mipmap.fthree, "LILLY", "ByCviac", "₹ 35");
        root.add(ch2);


        Addcartinfo ch3 = new Addcartinfo(R.mipmap.ffour, "JASMINE", "ByCviac", "₹ 65");
        root.add(ch3);


        Addcartinfo ch4 = new Addcartinfo(R.mipmap.ffive, "LOTUS", "ByCviac", "₹ 35");
        root.add(ch4);


        Addcartinfo ch5 = new Addcartinfo(R.mipmap.fsix, "ROSE", "ByCviac", "₹ 100");
        root.add(ch5);

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
}
