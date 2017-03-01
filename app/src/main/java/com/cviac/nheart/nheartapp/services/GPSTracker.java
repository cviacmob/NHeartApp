package com.cviac.nheart.nheartapp.services;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.restapi.FCMSendMessageResponse;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.PushMessageInfo;
import com.cviac.nheart.nheartapp.xmpp.LocalBinder;
import com.cviac.nheart.nheartapp.xmpp.XMPPService;
import com.squareup.okhttp.OkHttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static android.location.LocationManager.*;
import static com.cviac.nheart.nheartapp.BuildConfig.DEBUG;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;


    public GPSTracker() {
        this.mContext = this;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return new LocalBinder<GPSTracker>(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags,
                              final int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(final Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopUsingGPS();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                showSettingsAlert();
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return null;
                    }
                    locationManager.requestLocationUpdates(NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        }
                        locationManager.requestLocationUpdates(GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (location != null) {
            Prefs.putDouble("latitude", location.getLatitude());
            Prefs.putDouble("longitude", location.getLongitude());
            sendLocationToPeer(location);
        }
        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu to enable?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        final double getLattitude, getLogitude;
        Log.d("Google", "Location Changed");
        getLattitude = location.getLatitude();
        getLogitude = location.getLongitude();
        Prefs.putDouble("latitude", getLattitude);
        Prefs.putDouble("longitude", getLogitude);
        Log.d("latitude", getLattitude + "");
        Log.d("longitude", getLogitude + "");
        sendLocationToPeer(location);
//
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(GPSTracker.this, "Lat: " + getLattitude + "Long: " + getLogitude, Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    private void sendLocationToPeer(Location location) {

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        Date nw = new Date();
        String dt =  dateformat.format(nw);
        String tm = timeFormatter.format(nw);
        Prefs.putString("locationdate",dt);
        Prefs.putString("locationtime",tm);
       String toPushId =  Prefs.getString("to_pushid","");
        if (!toPushId.isEmpty()) {
            sendLocationViaPushNotify(toPushId,location);
        }
    }

    private void sendLocationViaPushNotify(String pushId, Location location) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        OpenCartAPI api = retrofit.create(OpenCartAPI.class);
        String key = "key=AAAAtsX2lv4:APA91bHXo-G0dPI5UC6a7AUZmDRaEItUp_RPhNw7x3xOSpkjrN9wzDdf6Ui33zNjrc5D6rY7WYnH30qxd3WzHPdVUnF_n5xSBRJ9XhTIhB608Cc0GCp5rs9JDSYeWiNRVIUWwI5E9XM_";
        PushMessageInfo pinfo = new PushMessageInfo();
        pinfo.setTo(pushId);
        PushMessageInfo.DataInfo dinfo = new PushMessageInfo.DataInfo();
        dinfo.setType("location");
        dinfo.setMsg(location.getLatitude()+","+location.getLongitude());
        String mypushid = Prefs.getString("pushId","");
        dinfo.setMsgId(mypushid);
        pinfo.setData(dinfo);
        final Call<FCMSendMessageResponse> call = api.sendPushMessage(key, pinfo);
        call.enqueue(new Callback<FCMSendMessageResponse>() {
            @Override
            public void onResponse(Response<FCMSendMessageResponse> response, Retrofit retrofit) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}