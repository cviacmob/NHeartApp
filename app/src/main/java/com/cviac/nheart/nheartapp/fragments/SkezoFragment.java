package com.cviac.nheart.nheartapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.R;


import com.cviac.nheart.nheartapp.activities.Chat_hug;
import com.cviac.nheart.nheartapp.activities.Registration;

import com.cviac.nheart.nheartapp.adapters.Huginfoadapter;
import com.cviac.nheart.nheartapp.adapters.SkezoInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.HugInfo;


import java.util.ArrayList;
import java.util.List;


public class SkezoFragment extends Fragment {
    ProgressBar progress;
    private List<HugInfo> huglist;
    public ListView lv2;
    ImageButton fab;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_skezo, container, false);

        lv2 = (ListView) view.findViewById(R.id.list_skezo);
       /*  progress = (ProgressBar)getActivity().findViewById(R.id.progressBarFetch);
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);*/

        values();
      /*  progress.setVisibility(View.GONE);*/

        Huginfoadapter adapter = new Huginfoadapter(getActivity(), huglist);

        lv2.setAdapter(adapter);

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos1, long pos2) {
                HugInfo emp = huglist.get(pos1);
                Intent i = new Intent(getActivity().getApplicationContext(), Chat_hug.class);
                i.putExtra("mob",emp);
                startActivity(i);
            }
        });
        return view;
    }

    private void values(){

        huglist=new ArrayList<>();

        HugInfo hi2 = new HugInfo(R.mipmap.girlone, "1002", "Libo");
        huglist.add(hi2);

        HugInfo hi = new HugInfo(R.mipmap.girltwo, "1001","Tibo");
        huglist.add(hi);




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
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}