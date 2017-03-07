package com.cviac.nheart.nheartapp.restapi;

import com.cviac.nheart.nheartapp.Prefs;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Cviac on 02/03/2017.
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = (HashSet) Prefs.getStringSet("oc_cookies",new HashSet<String>());

            for (String header : originalResponse.headers("Set-Cookie")) {
                int indx =  header.indexOf(';');
                String val = header.substring(0,(indx > 0) ? indx: header.length());
                cookies.add(val);
            }
            if (!cookies.isEmpty()) {
                Prefs.putStringSet("oc_cookies", cookies);
            }
        }
        return originalResponse;
    }
}