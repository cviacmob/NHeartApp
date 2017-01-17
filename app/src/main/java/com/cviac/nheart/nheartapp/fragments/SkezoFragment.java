package com.cviac.nheart.nheartapp.fragments;

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

import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.activities.Chat_Skezo;
import com.cviac.nheart.nheartapp.adapters.SkezoInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;
import com.cviac.nheart.nheartapp.datamodel.SkezoInfo;

import java.util.ArrayList;
import java.util.List;


public class SkezoFragment extends Fragment {
    private List<SkezoInfo> skezolist;
    public ListView lv2;
    ImageButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_skezo, container, false);

        lv2 = (ListView) view.findViewById(R.id.list_skezo);
        fab=(ImageButton)view.findViewById(R.id.fab);
        values();



        SkezoInfoAdapter adapter = new SkezoInfoAdapter(getActivity(), skezolist);
        lv2.setAdapter(adapter);




        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos1, long pos2) {
                SkezoInfo emp = skezolist.get(pos1);
                Intent i = new Intent(getActivity().getApplicationContext(), Chat_Skezo.class);

                startActivity(i);
            }
        });







        return view;






    }




    private void values(){

        skezolist=new ArrayList<>();



        SkezoInfo si=new SkezoInfo(R.mipmap.sai,"hello","a moment ago","Geetu");
        skezolist.add(si);


        SkezoInfo si1=new SkezoInfo(R.mipmap.girlone,"how are you","16:25","Seetu");
        skezolist.add(si1);


        SkezoInfo si2=new SkezoInfo(R.mipmap.girlthree,"thank you","04:05","Frie");
        skezolist.add(si2);

        SkezoInfo sii=new SkezoInfo(R.mipmap.girltwo,"have a nice day","3days ago","Crie");
        skezolist.add(sii);

        SkezoInfo si3=new SkezoInfo(R.mipmap.girlone,"bye bye...","22/06/2016","Prie");
        skezolist.add(si3);


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
        super.onPrepareOptionsMenu(menu);
    }





}