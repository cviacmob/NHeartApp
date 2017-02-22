package com.cviac.nheart.nheartapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    android.support.v7.app.ActionBar actionBar;
    TextView title,lastloca;
    String toname;

    private boolean showLevelPicker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionmethod();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        toname=Prefs.getString("to_name","");
//        if(toname.isEmpty()) {
//           toname= Prefs.getString("to_mobile", "");
//        }
        //setTitle(toname);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng india = new LatLng(12.9830269,80.2594001);
        mMap.addMarker(new MarkerOptions().position(india).title("Marker in chennai"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.9830269, 80.2594001), 18));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    public void actionmethod() {
        actionBar =  getSupportActionBar();
        if (actionBar != null) {
// Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B5CD1")));
            View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);
            title=(TextView) customView.findViewById(R.id.title);
            toname=Prefs.getString("to_name","");
            if(toname.isEmpty()) {
                toname= Prefs.getString("to_mobile", "");
            }
            title.setText(toname);
            lastloca=(TextView)customView.findViewById(R.id.lastlocation);
            actionBar.setCustomView(customView);


        }

    }

}
