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

import com.cviac.nheart.nheartapp.adapters.Huginfoadapter;
import com.cviac.nheart.nheartapp.adapters.SkezoInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.HugInfo;
import com.cviac.nheart.nheartapp.datamodel.MusicInfo;

import com.cviac.nheart.nheartapp.datamodel.SkezoInfo;

import java.util.ArrayList;
import java.util.List;


public class HugFragment extends Fragment {
    private List<HugInfo> huglist;
    public ListView lv2;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_hug, container, false);

        lv2 = (ListView) view.findViewById(R.id.list_hug);

        values();



        Huginfoadapter adapter = new Huginfoadapter(getActivity(), huglist);
        lv2.setAdapter(adapter);




        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


            }
        });


        return view;






    }




    private void values(){

        huglist=new ArrayList<>();

        HugInfo hi2=new HugInfo(R.mipmap.docter,"thank you","04:05","Doctor");
        huglist.add(hi2);

        HugInfo hi=new HugInfo(R.mipmap.doctor22,"hello","a moment ago","Advisor");
        huglist.add(hi);


        HugInfo hi1=new HugInfo(R.mipmap.sai,"how are you","16:25","Counsellor");
        huglist.add(hi1);




        HugInfo hii=new HugInfo(R.mipmap.coun,"have a nice day","3days ago","Other");
        huglist.add(hii);

        HugInfo hi3=new HugInfo(R.mipmap.docter,"bye bye...","22/06/2016","Relative");
        huglist.add(hi3);


    }












    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_category).setVisible(false);
        menu.findItem(R.id.action_cart).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}