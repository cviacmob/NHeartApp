package com.cviac.nheart.nheartapp.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.R;

import com.cviac.nheart.nheartapp.activities.Chat_Skezo;
import com.cviac.nheart.nheartapp.activities.Chat_hug;
import com.cviac.nheart.nheartapp.activities.Skezo_Main;
import com.cviac.nheart.nheartapp.adapters.ConvMessageAdapter;
import com.cviac.nheart.nheartapp.adapters.Huginfoadapter;
import com.cviac.nheart.nheartapp.adapters.SkezoInfoAdapter;
import com.cviac.nheart.nheartapp.datamodel.ConvMessage;
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
    private ListView lv;
    private ImageButton img;
    private EditText edittxt;
    private String geteditmgs;
    String converseId, msgid;
    private TextView customTitle, customduration;
    private ImageView customimage, customimageback;
    private List<ConvMessage> chats;
    private ConvMessageAdapter chatAdapter;
    String mynum, tonum, myname;

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
                HugInfo emp = huglist.get(pos1);
                    Intent i = new Intent(getActivity().getApplicationContext(), Chat_hug.class);

                    i.putExtra("mob",emp);

                    startActivity(i);
            }
        });
        return view;
    }

    private void values() {

        huglist = new ArrayList<>();

        HugInfo hi2 = new HugInfo(R.mipmap.docter, "9894250016", "thank you","101", "Doctor");
        huglist.add(hi2);

        HugInfo hi = new HugInfo(R.mipmap.doctor22, "9791234809", "hello", "102", "Advisor");
        huglist.add(hi);

        HugInfo hi1 = new HugInfo(R.mipmap.coun, "7871816364", "how are you","103", "Counsellor");
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