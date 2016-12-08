package com.cviac.nheart.nheartapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.cviac.nheart.nheartapp.R;

/**
 * Created by admin1 on 11/29/2016.
 */

public class HugFragment extends Fragment {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_category).setVisible(false);
        menu.findItem(R.id.action_cart).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}