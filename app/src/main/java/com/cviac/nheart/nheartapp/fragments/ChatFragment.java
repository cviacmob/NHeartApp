package com.cviac.nheart.nheartapp.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.ConvMessageAdapter;
import com.cviac.nheart.nheartapp.datamodel.ConvMessage;
import com.cviac.nheart.nheartapp.restapi.GetStatus;

import java.util.List;


public class ChatFragment extends Fragment {
    private ListView lv;
    private ImageButton img;
    private EditText edittxt;
    private String geteditmgs;
    String converseId, msgid;
    private ConvMessage conv;
    private TextView customTitle,customduration;
    private ImageView customimage,customimageback;
    private List<ConvMessage> chats;
    private ConvMessageAdapter chatAdapter;
    Context mcontext;
    ActionBar actionBar;
    GetStatus convstatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatsfrgs = inflater.inflate(R.layout.activity_chat, container, false);
        lv = (ListView) chatsfrgs.findViewById(R.id.chatlist);
        img = (ImageButton) chatsfrgs.findViewById(R.id.sendbutton);
        edittxt = (EditText) chatsfrgs.findViewById(R.id.editTextsend);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geteditmgs = edittxt.getText().toString();


                if (!geteditmgs.equals("")) {
                    String converseId = getNormalizedConverseId(conv.getSender(), conv.getReceiver());
                    msgid = getMsgID();
                              /*ChatMessage chat = new ChatMessage(converseId, myempId, conv.getEmpid(), geteditmgs, msgid, true);
                        chat.setSenderName(myempname);
                        XMPPService.sendMessage(chat);
                        saveChatMessage(chat);*/
                    edittxt.getText().clear();

                        /*ChatMsg msg = new ChatMsg();
                        msg.setSenderid(myempId);
                        msg.setSendername(myempname);
                        msg.setMsg(geteditmgs);
                        msg.setMsgid(msgid);
                        msg.setReceiverid(conv.getEmpid());
                        checkAndSendPushNotfication(conv.getEmpid(), msg);*/
                }


            }


        });
        return chatsfrgs;
    }

    private String getMsgID() {

        return System.currentTimeMillis() + "";

    }

    private void loadConvMessages() {
        converseId = getNormalizedConverseId(conv.getSender(), conv.getReceiver());
       // chats = ConvMessage.getAll(converseId);
        chatAdapter = new ConvMessageAdapter(chats, mcontext);
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
/*    public void actionmethod() {

        actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF4848")));

            View customView = getLayoutInflater(getArguments()).inflate(R.layout.actionbar_title, null);
            customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);
            customimage = (ImageView) customView.findViewById(R.id.imageViewcustom);
            customduration = (TextView) customView.findViewById(R.id.duration);
            //lastseen();
            String url1 = conv.getImageurl();
            if (url1 != null && url1.length() > 0) {
                Picasso.with(mcontext).load(conv.getImageurl()).resize(100, 100).transform(new CircleTransform())
                        .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(customimage);
            }
            }

            customTitle.setText(conv.getSenderName());
            if (convstatus != null && convstatus.getStatus() != null) {
                customduration.setText(convstatus.getStatus());
            }


            // Change the font family (optional)

            // Set the on click listener for the title




            // Apply the custom view
            actionBar.setCustomView(customView);
        }*/


}
