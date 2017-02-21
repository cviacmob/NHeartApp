package com.cviac.nheart.nheartapp.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.activities.Chat_Skezo;
import com.cviac.nheart.nheartapp.activities.Chat_hug;
import com.cviac.nheart.nheartapp.adapters.Huginfoadapter;
import com.cviac.nheart.nheartapp.adapters.SkezoInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.HugInfo;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;

import com.cviac.nheart.nheartapp.datamodel.SkezoInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HugFragment extends Fragment {
    private List<HugInfo> huglist;
    public ListView lv2;
    HugInfo emp;
/*
    private void setProgressDialog() {
        progressDialog = new ProgressDialog(FireChatActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_hug, container, false);

        lv2 = (ListView) view.findViewById(R.id.list_hug);

        values();
        Huginfoadapter adapter = new Huginfoadapter(getActivity(), huglist);
        lv2.setAdapter(adapter);


        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos1, long pos2) {
              emp = huglist.get(pos1);
                //String ss=emp.getMob().toString();
                Intent i = new Intent(getActivity().getApplicationContext(), Chat_hug.class);
                i.putExtra("mob", emp);

                startActivity(i);
            }
        });


        return view;


    }


    private void values() {

        huglist = new ArrayList<>();

        HugInfo hi2 = new HugInfo(R.mipmap.docter, "7893939008", "thank you",new Date(), "Doctor");
        huglist.add(hi2);

        HugInfo hi = new HugInfo(R.mipmap.doctor22, "9791234809", "hello", new Date(), "Advisor");
        huglist.add(hi);


        HugInfo hi1 = new HugInfo(R.mipmap.coun, "744", "how are you",new Date(), "Counsellor");
        huglist.add(hi1);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_category).setVisible(false);
        menu.findItem(R.id.action_cart).setVisible(false);
        menu.findItem(R.id.action_call).setVisible(false);
        menu.findItem(R.id.loc).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }


}