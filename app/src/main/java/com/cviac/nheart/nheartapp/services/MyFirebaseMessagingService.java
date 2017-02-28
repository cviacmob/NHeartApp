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

import com.cviac.nheart.nheartapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

/**
 * Created by User on 11/24/2016.
 */
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
            showChatNotification(data);
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


}
