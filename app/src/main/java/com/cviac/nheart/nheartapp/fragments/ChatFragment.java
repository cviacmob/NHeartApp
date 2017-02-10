package com.cviac.nheart.nheartapp.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.NheartApp;
import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.ConvMessageAdapter;
import com.cviac.nheart.nheartapp.datamodel.ChatMsg;
import com.cviac.nheart.nheartapp.datamodel.ConvMessage;
import com.cviac.nheart.nheartapp.restapi.GetStatus;
import com.cviac.nheart.nheartapp.xmpp.ChatMessage;
import com.cviac.nheart.nheartapp.xmpp.XMPPService;

import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ChatFragment extends Fragment {
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatsfrgs = inflater.inflate(R.layout.activity_chat, container, false);
        lv = (ListView) chatsfrgs.findViewById(R.id.chatlist);
        img = (ImageButton) chatsfrgs.findViewById(R.id.sendbutton);
        edittxt = (EditText) chatsfrgs.findViewById(R.id.editTextsend);

        mynum = Prefs.getString("mobile", "");
        myname = Prefs.getString("name", "");
        tonum = Prefs.getString("to_mobile", "");

        loadConvMessages();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geteditmgs = edittxt.getText().toString();
                if (!geteditmgs.equals("")) {
                    String converseId = getNormalizedConverseId(mynum, tonum);
                    msgid = getMsgID();
                    ChatMessage chat = new ChatMessage(converseId, mynum, tonum, geteditmgs, msgid, true);
                    chat.setSenderName(myname);
                    XMPPService.sendMessage(chat);
                    saveChatMessage(chat);
                    edittxt.getText().clear();

                    ChatMsg msg = new ChatMsg();
                    msg.setSenderid(mynum);
                    msg.setSendername(myname);
                    msg.setMsg(geteditmgs);
                    msg.setMsgid(msgid);
                    msg.setReceiverid(tonum);
                    // checkAndSendPushNotfication(conv.getEmpid(), msg);
                }


            }


        });
        NheartApp app = (NheartApp) getActivity().getApplication();
        app.setChatFrag(this);
        return chatsfrgs;
    }

    private String getMsgID() {

        return System.currentTimeMillis() + "";

    }

    private void loadConvMessages() {
        converseId = getNormalizedConverseId(mynum, tonum);
        chats = ConvMessage.getAll(converseId);
        chatAdapter = new ConvMessageAdapter(chats, getActivity());
        lv.setAdapter(chatAdapter);
    }

    public String getConverseId() {
        return converseId;
    }

    private String getNormalizedConverseId(String myid, String receverid) {
        if (myid.compareTo(receverid) > 0) {
            return myid + "_" + receverid;
        }
        return receverid + "_" + myid;
    }

    public void addInMessage(ConvMessage msg) {
        // chats.add(msg);
        loadConvMessages();
        chatAdapter.notifyDataSetChanged();
    }

    public void reload() {
        loadConvMessages();
        chatAdapter.notifyDataSetChanged();
    }

    public void saveChatMessage(ChatMessage msg) {
        ConvMessage cmsg = new ConvMessage();
        cmsg.setMsg(msg.msg);
        cmsg.setCtime(new Date());
        cmsg.setConverseid(msg.converseid);
        cmsg.setSenderName(msg.senderName);
        cmsg.setReceiver(msg.receiver);
        cmsg.setSender(msg.sender);
        cmsg.setMsgid(msg.msgid);
        cmsg.setMine(msg.isMine);
        cmsg.setMine(true);
        cmsg.setStatus(1);
        cmsg.save();
        chats.add(cmsg);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NheartApp app = (NheartApp) getActivity().getApplication();
        app.setChatFrag(null);

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
        menu.findItem(R.id.action_call).setVisible(true);
        menu.findItem(R.id.loc).setVisible(true);

        super.onPrepareOptionsMenu(menu);
    }

}
