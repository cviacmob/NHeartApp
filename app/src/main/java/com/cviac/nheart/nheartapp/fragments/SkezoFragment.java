package com.cviac.nheart.nheartapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.adapters.SkezoInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;
import com.cviac.nheart.nheartapp.datamodel.SkezoInfo;

import java.util.ArrayList;
import java.util.List;


public class SkezoFragment extends Fragment {
    private List<SkezoInfo> skezolist;
    public ListView lv2;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_skezo, container, false);

        lv2 = (ListView) view.findViewById(R.id.list_skezo);
        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        values();



        SkezoInfoAdapter adapter = new SkezoInfoAdapter(getActivity(), skezolist);
        lv2.setAdapter(adapter);




        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {






            }
        });


                return view;






    }




    private void values(){

        skezolist=new ArrayList<>();



        SkezoInfo si=new SkezoInfo("R.mipmap.settings","hello","a moment ago","Geetu");
        skezolist.add(si);


        SkezoInfo si1=new SkezoInfo("R.mipmap.sai","how are you","16:25","Seetu");
        skezolist.add(si1);


        SkezoInfo si2=new SkezoInfo("R.mipmap.musicimg","thank you","04:05","Frie");
        skezolist.add(si2);

        SkezoInfo sii=new SkezoInfo("R.mipmap.musicimg","have a nice day","3days ago","Crie");
        skezolist.add(sii);

        SkezoInfo si3=new SkezoInfo("R.mipmap.musicimg","bye bye...","22/06/2016","Prie");
        skezolist.add(si3);


    }












    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_category).setVisible(false);
        menu.findItem(R.id.action_cart).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}