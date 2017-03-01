package com.cviac.nheart.nheartapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    android.support.v7.app.ActionBar actionBar;
    TextView title, lastloca;
    String toname;

    private boolean showLevelPicker = true;
    double latitude, longitude;

    private BroadcastReceiver locReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionmethod();

        boolean trackStatus =  Prefs.getBoolean("trackstatus",false);
        if (trackStatus) {
            regLocationChange();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        latitude = Prefs.getDouble("to_latitude", 0);
        longitude = Prefs.getDouble("to_longitude", 0);

        if (latitude != 0 && longitude != 0) {
            // Add a marker in Sydney and move the camera
            LatLng india = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(india).title(Prefs.getString("locationtime", "")));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
        }
    }


    public void actionmethod() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);

            SwitchCompat cmpt = (SwitchCompat) customView.findViewById(R.id.switch1);
            title = (TextView) customView.findViewById(R.id.title);
            toname = Prefs.getString("to_name", "");
            if (toname.isEmpty()) {
                toname = Prefs.getString("to_mobile", "");
            }
            title.setText(toname);
            lastloca = (TextView) customView.findViewById(R.id.lastlocation);
            lastloca.setText("Last Seen: "+ Prefs.getString("locationdate",""));
            actionBar.setCustomView(customView);
            cmpt.setChecked(Prefs.getBoolean("trackstatus",false));
            cmpt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        Prefs.putBoolean("trackstatus",true);
                        //Toast.makeText(getApplicationContext(), "Track On", Toast.LENGTH_LONG).show();
                        regLocationChange();
                    } else {
                        Prefs.putBoolean("trackstatus",false);
                        //Toast.makeText(getApplicationContext(), "Track Off", Toast.LENGTH_LONG).show();
                        unRegLocationChange();
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void regLocationChange() {
        if (locReceiver == null) {
            locReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                   final String loc =  intent.getStringExtra("location");
                    if (loc != null && !loc.isEmpty()) {
                        String locpair[] = loc.split(",");
                        if (locpair.length == 2) {
                            Double lat = Double.parseDouble(locpair[0]);
                            Double lng = Double.parseDouble(locpair[1]);
                            mMap.clear();
                            LatLng india = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(india).title(Prefs.getString("locationtime", "")));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                            //Toast.makeText(MapsActivity.this, "MapLatLong: " + loc, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
            registerReceiver(locReceiver, new IntentFilter("NHeartLocation"));
        }
    }

    private void unRegLocationChange() {
        if (locReceiver != null) {
            unregisterReceiver(locReceiver);
            locReceiver = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegLocationChange();
    }
}
