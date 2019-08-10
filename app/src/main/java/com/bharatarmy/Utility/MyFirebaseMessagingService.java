package com.bharatarmy.Utility;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bharatarmy.Activity.ImageVideoUploadActivity;
import com.bharatarmy.Activity.Splash_Screen;
import com.bharatarmy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static Context context;
    private static int notifyID = 1;
    String data,message;
    private String screen = "";
    int icon = R.drawable.app_logo;
    private NotificationManager notifManager;


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
        context = this;

        Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        sendNotification(remoteMessage);//remoteMessage.getNotification().getBody());
    }


    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(RemoteMessage remoteMessage) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = context.getString(R.string.default_notification_channel_title); // Default Channel
        String aMessage =remoteMessage.getData().get("body");
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.proflie);

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(false);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, Splash_Screen.class);
            intent.putExtra("fromNotification", remoteMessage.getData().get("type"));
            intent.putExtra("message", remoteMessage.getData().get("body"));//remoteMessage.getNotification().getBody());
            intent.putExtra("cometonotification", "true");
            Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(bitmap))
                    .setProgress(100, 50, true)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, Splash_Screen.class);
            intent.putExtra("fromNotification", remoteMessage.getData().get("type"));
            intent.putExtra("message", remoteMessage.getData().get("body"));//remoteMessage.getNotification().getBody());
            intent.putExtra("cometonotification", "true");
        Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(bitmap))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);

//        notifyID = (int) (System.currentTimeMillis() & 0xfffffff);
//
//        Intent notificationIntent = new Intent(ctx, Splash_Screen.class);
//
//
//        String data = String.valueOf(remoteMessage.getData());
////
////        try {
////            JSONObject dataObject = new JSONObject (data);
////
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//
//        notificationIntent.putExtra("fromNotification", remoteMessage.getData().get("type"));
//        notificationIntent.putExtra("message", remoteMessage.getData().get("body"));//remoteMessage.getNotification().getBody());
//        notificationIntent.putExtra("cometonotification", "true");
//        Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));
//
//        notificationIntent.setAction(String.valueOf(notifyID));
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(ctx, notifyID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.proflie);
//        Notification noti = new NotificationCompat.Builder(ctx)
//                .setSmallIcon(R.drawable.logo)
//                .setTicker(String.valueOf(remoteMessage.getData().get("body")))
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("Bharat Army")//
//                .setContentText(remoteMessage.getData().get("body"))//remoteMessage.getNotification().getBody()
//                .setContentIntent(pendingNotificationIntent)
//                .setAutoCancel(true)
//                .setLargeIcon(bitmap)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(bitmap)
//                        .bigLargeIcon(bitmap))
//                .build();
//
//        noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
//        noti.flags |= Notification.FLAG_AUTO_CANCEL;
//        // Play default notification sound
//        noti.defaults |= Notification.DEFAULT_SOUND;
//        noti.defaults |= Notification.DEFAULT_LIGHTS;
//
//        // Vibrate if vibrate is enabled
//        noti.defaults |= Notification.DEFAULT_VIBRATE;
//        //Show the notification
//        notificationManager.notify(notifyID, noti);
//        //Integer.valueOf(push_message_id)

    }


}

