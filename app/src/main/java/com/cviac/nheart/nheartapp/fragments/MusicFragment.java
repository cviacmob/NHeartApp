package com.cviac.nheart.nheartapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.activities.MusicActivity;
import com.cviac.nheart.nheartapp.activities.Otpverification;

/**
 * Created by admin1 on 11/29/2016.
 */

public class MusicFragment extends Fragment {

TextView t1;
    ImageView iv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cv=inflater.inflate(R.layout.music_home,container,false);
        t1=(TextView) cv.findViewById(R.id.texmusic);
        iv=(ImageView) cv.findViewById(R.id.imadd);

        iv.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), MusicActivity.class);
                startActivity(mainIntent);
            }
        });




        //thiscontext = container.getContext();
        return cv;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_category).setVisible(false);
        menu.findItem(R.id.action_cart).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}