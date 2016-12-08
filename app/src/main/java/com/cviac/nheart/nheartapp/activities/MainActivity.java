package com.cviac.nheart.nheartapp.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.fragments.ChatFragment;


import com.cviac.nheart.nheartapp.fragments.GiftFragment;
import com.cviac.nheart.nheartapp.fragments.HugFragment;
import com.cviac.nheart.nheartapp.fragments.MusicFragment;
import com.cviac.nheart.nheartapp.fragments.SkezoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static TabLayout tabLayout;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Toolbar toolbar;
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setExpanded(false, true);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
       mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                      ab.show();
                }
                return false;
            }
        });


        /*final int[] ICONS = new int[]{
                R.drawable.chaticon,
                R.drawable.hugicon,
                R.drawable.colistner,
                R.drawable.skezoicon,
                R.drawable.hugicon
        };
        final String[] resource = new String[]{
                "chat",
                "hugicon",
                "colistner",
                "skezoicon",
                "hugicon"
        };*/
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
/*        tabLayout.getTabAt(0).setIcon(R.drawable.chaticon);
        tabLayout.getTabAt(1).setIcon( R.drawable.giftsicon);
        tabLayout.getTabAt(2).setIcon( R.drawable.colistner);*/
        // tabLayout.getTabAt(3).setIcon(R.drawable.skezoicon);
        //tabLayout.getTabAt(4).setIcon( R.drawable.hugicon);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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
        tab.setIcon(R.mipmap.ic_computer_black_24dp);
        tab = tabLayout.getTabAt(4);
        tab.setTag("Hug");
        tab.setIcon(R.mipmap.ic_favorite_border_black_24dp);

        setTitle("Mirror");
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

        // tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
                int ic3 = (isSelected) ? R.mipmap.ic_computer_white_24dp : R.mipmap.ic_computer_black_24dp;
                tab.setIcon(ic3);
                break;

            case "Hug":
                int ic4 = (isSelected) ? R.mipmap.ic_favorite_border_white_24dp : R.mipmap.ic_favorite_border_black_24dp;
                tab.setIcon(ic4);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1000) {
            if(data !=null) {
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
           case R.id.action_cart:

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
                    return new ChatFragment();
                case 2:
                    Fragment frag = new GiftFragment();
                    return new GiftFragment();
                case 3:
                    return new MusicFragment();
                case 4:
                    return new SkezoFragment();
                case 5:
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
}
