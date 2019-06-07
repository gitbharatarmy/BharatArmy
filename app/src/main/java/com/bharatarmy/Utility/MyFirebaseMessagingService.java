package com.bharatarmy.Utility;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bharatarmy.Activity.Splash_Screen;
import com.bharatarmy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static Context ctx;
    private static int notifyID = 1;
    String data,message;
    private String screen = "";

    /**
     * Method checks if the app is in background or not
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ctx = this;

        Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        sendNotification(remoteMessage);//remoteMessage.getNotification().getBody());
    }


    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(RemoteMessage remoteMessage) {
        notifyID = (int) (System.currentTimeMillis() & 0xfffffff);

        Intent notificationIntent = new Intent(ctx, Splash_Screen.class);


        String data = String.valueOf(remoteMessage.getData());
//
//        try {
//            JSONObject dataObject = new JSONObject (data);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        notificationIntent.putExtra("fromNotification", remoteMessage.getData().get("type"));
        notificationIntent.putExtra("message", remoteMessage.getData().get("body"));//remoteMessage.getNotification().getBody());
        notificationIntent.putExtra("cometonotification", "true");
        Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));

        notificationIntent.setAction(String.valueOf(notifyID));
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(ctx, notifyID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification noti = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.app_logo)
                .setTicker(String.valueOf(remoteMessage.getData().get("body")))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Bharat Army")//
                .setContentText(remoteMessage.getData().get("body"))//remoteMessage.getNotification().getBody()
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true).build();

        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        // Play default notification sound
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.defaults |= Notification.DEFAULT_LIGHTS;

        // Vibrate if vibrate is enabled
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        //Show the notification
        notificationManager.notify(notifyID, noti);
        //Integer.valueOf(push_message_id)

    }


}

