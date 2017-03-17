package com.cviac.nheart.nheartapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.activities.AcceptAnimationAcitvity;
import com.cviac.nheart.nheartapp.activities.RejectAnimationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessageService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            String type =  data.get("type");
            if (type.equalsIgnoreCase("inviteresponse")) {
                showInviteRespohnseNotification(data);
            }
            else if (type.equalsIgnoreCase("location")) {
                updatePeerLocation(data);
            }
            else {
                showChatNotification(data);
            }
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            return;
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String notofi=remoteMessage.getNotification().getBody();
            //annoncement(notofi);
            Log.d(TAG, "Message Annoncements Body: " + remoteMessage.getNotification().getBody());

        }
    }
   /* private void annoncement(String msg){
        Annoncements anc=new Annoncements();
        anc.setAnnoncemsg(msg);
        anc.setDate(new Date());
        anc.save();
    }*/

    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    private void showChatNotification(Map<String, String> data) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(data.get("sendername"))
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setContentText(data.get("msg"));
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }

    private void showInviteRespohnseNotification(Map<String, String> data) {

        String status = data.get("msg");
        String notifyMsg = "Your invitation to " + Prefs.getString("to_mobile","") + " is " + status;

        Prefs.putString("to_name",data.get("sendername"));

        String to_pushid = data.get("msgId");
        if (to_pushid != null && !to_pushid.isEmpty()) {
            Prefs.putString("to_pushid", to_pushid);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Intent resultIntent;
        if (status.equalsIgnoreCase("accepted")) {
            resultIntent = new Intent(this,AcceptAnimationAcitvity.class);
        }
        else {
            resultIntent = new Intent(this,RejectAnimationActivity.class);
        }
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(data.get("sendername"))
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setContentText(notifyMsg);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }


    private void updatePeerLocation(Map<String, String> data) {
        final String location = data.get("msg");
        String locpair[] = location.split(",");
        if (locpair.length == 2) {
            Double lat = Double.parseDouble(locpair[0]);
            Double lng = Double.parseDouble(locpair[1]);
            Prefs.putDouble("to_latitude", lat);
            Prefs.putDouble("to_longitude", lng);
        }
        else {
            return;
        }

        Intent intent = new Intent();
        intent.setAction("NHeartLocation");
        intent.putExtra("location", location);
        sendBroadcast(intent);

//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MyFirebaseMessagingService.this, "NotifyLatLong: " + location, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

