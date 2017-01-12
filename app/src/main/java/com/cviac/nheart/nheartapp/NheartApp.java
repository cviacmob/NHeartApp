package com.cviac.nheart.nheartapp;

import android.app.Application;
import android.content.ContextWrapper;

public class NheartApp extends Application {

    private boolean networkstatus=true;

    public boolean isNetworkstatus() {
        return networkstatus;
    }

    public void setNetworkstatus(boolean networkstatus) {
        this.networkstatus = networkstatus;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

}
