package com.cviac.nheart.nheartapp.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.ConvMessage;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.LoginResponse;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;
import com.cviac.nheart.nheartapp.fragments.ChatFragment;


import com.cviac.nheart.nheartapp.fragments.GiftFragment;
import com.cviac.nheart.nheartapp.fragments.HugFragment;
import com.cviac.nheart.nheartapp.fragments.MusicFragment;
import com.cviac.nheart.nheartapp.fragments.SkezoFragment;
import com.cviac.nheart.nheartapp.receivers.AlarmReceiver;
import com.cviac.nheart.nheartapp.restapi.AddCookiesInterceptor;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.ReceivedCookiesInterceptor;
import com.cviac.nheart.nheartapp.utilities.BadgeDrawable;
import com.cviac.nheart.nheartapp.services.GPSTracker;
import com.cviac.nheart.nheartapp.xmpp.LocalBinder;
import com.cviac.nheart.nheartapp.xmpp.XMPPService;
import com.squareup.okhttp.OkHttpClient;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static final int MY_PERMISSION_MEDIA = 1;

    public static final int MY_PERMISSION_CALL_PHONE = 10;

    public static final int MY_PERMISSION_LOCATION = 100;

    String mob = Prefs.getString("to_mobile", "");


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    static TabLayout tabLayout;
    private LayerDrawable mcartMenuIcon;
    private int mCartCount = 0;
    private XMPPService mService;
    private GPSTracker mlocService;
    CoordinatorLayout coordinatorLayout;
    private BroadcastReceiver xmppConnReciver;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Toolbar toolbar;
    ActionBar ab;
    private boolean mBounded;
    String status;
    private GiftFragment giftFragment;
    private ChatFragment chatFrag;
    GPSTracker gpstracker;

    private MediaPlayer mp = new MediaPlayer();

    private List<MusicInfo> songlist;

    public List<MusicInfo> getSonglist() {
        if (songlist == null) {
            songlist = new ArrayList<MusicInfo>();
        }
        return songlist;
    }

    public void setSonglist(List<MusicInfo> songlist) {
        this.songlist = songlist;
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        @SuppressWarnings("unchecked")
        @Override
        public void onServiceConnected(final ComponentName name,
                                       final IBinder service) {
            mService = ((LocalBinder<XMPPService>) service).getService();
            mBounded = true;
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mService = null;
            mBounded = false;
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    private final ServiceConnection mLocationConnection = new ServiceConnection() {

        @SuppressWarnings("unchecked")
        @Override
        public void onServiceConnected(final ComponentName name,
                                       final IBinder service) {
            mlocService = ((LocalBinder<GPSTracker>) service).getService();
            if (ContextCompat.checkSelfPermission(MainActivity.this, (android.Manifest.permission.ACCESS_FINE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED)  {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_LOCATION);
                return;
            }
            else {
                mlocService.getLocation();
            }
            Log.d(TAG, "onLocationServiceConnected");
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mlocService = null;
            Log.d(TAG, "onLocationServiceDisconnected");
        }
    };

    public MediaPlayer getMediaPlayer() {
        return mp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checkPermissions();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setAlaram();



        /*final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(false, true);
*/

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_layout);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.setTag("Mirror");
        tab.setIcon(R.mipmap.ic_message_text_white_24dp);
        tab = tabLayout.getTabAt(1);
        tab.setTag("Lovewrap");
        tab.setIcon(R.mipmap.ic_gift_black_24dp);
        tab = tabLayout.getTabAt(2);
        tab.setTag("Co Listen");
        tab.setIcon(R.mipmap.ic_music_circle_black_24dp);
        tab = tabLayout.getTabAt(3);
        tab.setTag("Skezo");
        tab.setIcon(R.mipmap.robo_black);
        tab = tabLayout.getTabAt(4);
        tab.setTag("Hug");


        tab.setIcon(R.mipmap.ic_people_black_24dp);
        setTitle("Mirror");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTitle(tab.getTag().toString());
                setTabIcon((TabLayout.Tab) tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabIcon((TabLayout.Tab) tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getSetToken();
        doBindService();

    }

    private void setAlaram() {
        //List<Employee> emplist = Employee.eventsbydate();
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 0);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

    }

    private void setTabIcon(TabLayout.Tab tab, boolean isSelected) {
        String tabTitle = tab.getTag().toString();
        switch (tabTitle) {

            case "Mirror":
                int ic = (isSelected) ? R.mipmap.ic_message_text_white_24dp : R.mipmap.ic_message_text_black_24dp;
                tab.setIcon(ic);
                break;

            case "Lovewrap":
                int ic1 = (isSelected) ? R.mipmap.ic_gift_white_24dp : R.mipmap.ic_gift_black_24dp;
                tab.setIcon(ic1);
                break;

            case "Co Listen":
                int ic2 = (isSelected) ? R.mipmap.ic_music_circle_white_24dp : R.mipmap.ic_music_circle_black_24dp;
                tab.setIcon(ic2);
                break;

            case "Skezo":
                int ic3 = (isSelected) ? R.mipmap.robo_white : R.mipmap.robo_black;
                tab.setIcon(ic3);
                break;

            case "Hug":
                int ic4 = (isSelected) ? R.mipmap.ic_people_white_24dp : R.mipmap.ic_people_black_24dp;
                tab.setIcon(ic4);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mcartMenuIcon = (LayerDrawable) menu.findItem(R.id.action_cart).getIcon();
        setBadgeCount(this, mcartMenuIcon, "");
        getAndSetCartCount();
        return true;
    }


    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_cart_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_cart_badge, badge);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1000) {
            if (data != null) {
                String catId = data.getStringExtra("categoryid");

                if (catId != null)

                {
                    GiftFragment gfrag = (GiftFragment) getSupportFragmentManager().getFragments().get(1);

                    gfrag.refresh(catId);


                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_category:
                Intent i = new Intent(MainActivity.this, CategorylistActivity.class);
                startActivityForResult(i, 1000);
                break;
            // action with ID action_settings was selected
            case R.id.action_refresh:

                break;


            case R.id.action_call:

                ImageView cuscall = (ImageView) findViewById(R.id.ivcall);
                onClick(cuscall);


                break;


            case R.id.action_cart:
                if (mCartCount == 0) {
                    Intent h = new Intent(MainActivity.this, EmptyCartListActivity.class);
                    startActivity(h);
                }
                if (mCartCount != 0) {
                    Intent h = new Intent(MainActivity.this, CartItemListActivity.class);
                    startActivity(h);
                }
                break;
            case R.id.loc:

                Intent n = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(n);
                break;
            default:
                break;
        }

        return true;


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //return true;
    }

    public void onClick(View view) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mob));
        if (ContextCompat.checkSelfPermission(this, (android.Manifest.permission.CALL_PHONE))
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, MY_PERMISSION_CALL_PHONE);
            return;
        }
        startActivity(callIntent);
    }

    //return super.onOptionsItemSelected(item);


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        //   private final List<String> mFragmentTitleList = new ArrayList<>();
        private static final String ARG_SECTION_NUMBER = "section_number";
        private Context context;

        public PlaceholderFragment() {
        }


//
//
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);

            switch (position + 1) {
                case 1:
                    chatFrag = new ChatFragment();

                    return chatFrag;
                case 2:
                    giftFragment = new GiftFragment();
                    // Fragment frag = new GiftFragment();
                    return giftFragment;
                case 3:
                    //Fragment frag1 = new MusicFragment();
                    return new MusicFragment();
                case 4:
                    //Fragment frag2 = new SkezoFragment();
                    return new SkezoFragment();
                case 5:
                    //Fragment frag3 = new HugFragment();
                    return new HugFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {

           /* switch (position) {
                case 0:
                    return "Mirror";

                case 1:
                    return "Love Wrap";
                case 2:
                    return "Co-Listen";
                case 3:
                    return "Skezo";
                case 4:
                    return "Hug";
            }*/
            return null;
        }
    }

    private void getSetToken() {
        String token = null; //Prefs.getString("token", null);
        if (token == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
            okHttpClient.interceptors().add(new AddCookiesInterceptor());
            okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.domainname))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            OpenCartAPI api = retrofit.create(OpenCartAPI.class);

            String apiKey = "um5xn7zaF0RfeAzhN5vG3xsqeeFjupkgOjvtqSubhcR68yw1yg5l1nu4z0EIaYx2HLqRwlvkLGCnFL8EIG0T61L3AtD1v5HNCTPYKdksMXZrCGWGdFX1p5z8KKGQz7lBQzczWxopiQcsUXKr6B7vNasiWEpZ5pNWTjhZgMMOUILMKmnj335u67xLaO334LRmgDiEA6IDyR4Hmilqp3xjce2SvPJeRDwPuINSmSFLFxJO8qUSiF6xObvNhqZZAkey";
           // final Call<LoginResponse> call = api.login(apiKey);
            String email = Prefs.getString("email","");
            String pass = "12345";
            final Call<LoginResponse> call = api.login(email,pass);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                    int code = response.code();
                    LoginResponse rsp = response.body();
                    Prefs.putString("token", rsp.getToken());
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void getAndSetCartCount() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        String token = Prefs.getString("token", null);
        Call<GetCartItemsResponse> call = api.getCartItems();
        call.enqueue(new Callback<GetCartItemsResponse>() {

            public void onResponse(Response<GetCartItemsResponse> response, Retrofit retrofit) {
                GetCartItemsResponse rsp = response.body();
                if (rsp != null) {
                    mCartCount = rsp.getProds().size();
                    setBadgeCount(MainActivity.this, mcartMenuIcon, mCartCount + "");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
        }
        doUnbindService();
    }

    String[] permissions = new String[]{
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public boolean checkPermissions() {
        int result = 1;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(MainActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1000);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case MY_PERMISSION_MEDIA:
                if (grantResults.length > 0) {

                }
                break;
            case MY_PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mlocService != null) {
                        mlocService.getLocation();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Location track disabled", Toast.LENGTH_LONG).show();
                }
            }

            case MY_PERMISSION_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callintent = new Intent(Intent.ACTION_CALL);
                    callintent.setData(Uri.parse("tel:" + mob));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callintent);
                }
            }
        }
    }

    private void doBindService() {
//        xmppConnReciver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (status != null) {
//                    status = intent.getStringExtra("status");
//
//                }
//                if (status != null) {
//                    chatFrag.statuscheck(status);
//                }
//            }
//        };

        bindService(new Intent(this, XMPPService.class), mConnection, Context.BIND_AUTO_CREATE);
        //registerReceiver(xmppConnReciver, new IntentFilter("XMPPConnection"));
        bindService(new Intent(this, GPSTracker.class), mLocationConnection, Context.BIND_AUTO_CREATE);

    }


    void doUnbindService() {
        if (mService != null) {
            unbindService(mConnection);
//            if (xmppConnReciver != null) {
//                unregisterReceiver(xmppConnReciver);
//            }
        }

        if (mlocService != null) {
            unbindService(mLocationConnection);
        }

    }

    public XMPPService getmService() {
        return mService;
    }

    public GPSTracker getLocationService() {
        return mlocService;
    }
}
