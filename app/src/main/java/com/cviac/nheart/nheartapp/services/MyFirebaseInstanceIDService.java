package com.cviac.nheart.nheartapp.services;

import android.content.SharedPreferences;

import com.cviac.nheart.nheartapp.Prefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by User on 11/24/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "InstanceIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG, "Refreshed token: " + refreshedToken);
        if (refreshedToken != null) {
            Prefs.putString("pushId", refreshedToken);
        }
    }
}
