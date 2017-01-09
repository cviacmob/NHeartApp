package com.cviac.nheart.nheartapp.activities;

import android.content.Intent;
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
import com.cviac.nheart.nheartapp.datamodel.Product;
import com.cviac.nheart.nheartapp.datamodel.ServiceInfo;

import java.util.ArrayList;
import java.util.List;

public class ChangeAddressActivity extends AppCompatActivity {

    Button addnew,decline;
    ListView lv;
    TextView tv1,tv2,tv3;
    List<Addressinfo> addresslist;
    AddressAdapter adapt;
    String name,emailid,mobile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chnageaddress);
        loadServices();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();

       lv=(ListView)findViewById(R.id.listaddress);
        adapt=new AddressAdapter(this,addresslist);
       lv.setAdapter(adapt);
        tv1=(TextView)findViewById(R.id.rname);
        tv2=(TextView)findViewById(R.id.emailid);
        tv3=(TextView)findViewById(R.id.rmobile);

        name= Prefs.getString("Name","");
        emailid= Prefs.getString("Email","");
        mobile= Prefs.getString("Phone","");
        tv1.setText(name);
        tv2.setText(emailid);
        tv3.setText(mobile);
//        Intent i = getIntent();
//        Addressinfo addresslist = (Addressinfo) i.getSerializableExtra("address");
        decline=(Button) findViewById(R.id.canc);

        addnew=(Button)findViewById(R.id.addnew);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent mainIntent = new Intent(ChangeAddressActivity.this,AddNewAddressActivity.class);
                startActivity(mainIntent);
            }
        });

            }
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void loadServices() {
        addresslist = new ArrayList<Addressinfo>();
        Addressinfo ad =  new Addressinfo("String", "String", "String", "String", "String","String","String","String","String");

        Addressinfo ch1 = new Addressinfo("vinodkumar", "no-9,alagiri salai,periyar nagar", "chennai", "chennai", "tamilknadu",
                "600097","9550986884","9566056773","near to ilangu nagar");
        addresslist.add(ch1);
        Addressinfo ch2 = new Addressinfo("manju", "no-9,perumal kovila street,indira nagar", "chennai", "chennai", "tamilknadu",
                "600097","9550986884","9566056773","near to virugambakkam");
        addresslist.add(ch2);
        Addressinfo ch3 = new Addressinfo("kathiravan", "no-9,gandhi street,alawarthiru nagar", "chennai", "chennai", "tamilknadu",
                "600097","8682011392","9566056773","near to shaligramam");
        addresslist.add(ch3);


//        ServiceInfo ch2 = new ServiceInfo(R.mipmap.fthree, "LILLY", "ByCviac", "₹ 35", "₹ 55");
//        root.add(ch2);
//
//
//        ServiceInfo ch3 = new ServiceInfo(R.mipmap.ffour, "JASMINE", "ByCviac", "₹ 65", "₹ 75");
//        root.add(ch3);
//
//
//        ServiceInfo ch4 = new ServiceInfo(R.mipmap.ffive, "LOTUS", "ByCviac", "₹ 35", "₹ 45");
//        root.add(ch4);
//
//
//        ServiceInfo ch5 = new ServiceInfo(R.mipmap.fsix, "ROSE", "ByCviac", "₹ 100", "₹ 115");
//        root.add(ch5);
//
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
