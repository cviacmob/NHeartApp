package com.cviac.nheart.nheartapp;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class NheartApp extends MultiDexApplication {

    private boolean networkstatus=true;

    public boolean isNetworkstatus() {
        return networkstatus;
    }

    public void setNetworkstatus(boolean networkstatus) {
        this.networkstatus = networkstatus;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
