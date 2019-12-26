package com.bharatarmy.Utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bharatarmy.Activity.Splash_Screen;
import com.bharatarmy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static Context context;
    private NotificationManager notifManager;

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance HomeTempleteIDModel token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        context = this;

        Log.d("Data", "" + remoteMessage.getData());
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

//        if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
        sendNotification(remoteMessage);


    }


    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(RemoteMessage remoteMessage) {
        final int NOTIFY_ID = (int) (System.currentTimeMillis() & 0xfffffff);
        ; // HomeTempleteIDModel of notification
        String id = context.getString(R.string.default_notification_channel_id); // default_channel_id

        String title = context.getString(R.string.default_notification_channel_title); // Default Channel
        String aMessage = remoteMessage.getData().get("title");// =

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(false);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, Splash_Screen.class);
            intent.putExtra("pagetype", remoteMessage.getData().get("PageType"));
            intent.putExtra("message", remoteMessage.getData().get("body"));

            Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo_new)   // required
                    .setContentText(remoteMessage.getData().get("body")) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setChannelId(id)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(remoteMessage.getData().get("body"))
                    .setLargeIcon(Utils.getBitmapFromURL(remoteMessage.getData().get("smallimageurl")))
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(Utils.getBitmapFromURL(remoteMessage.getData().get("image")))
                            .bigLargeIcon(Utils.getBitmapFromURL(remoteMessage.getData().get("smallimageurl"))))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setChannelId(id); //.setChannelId(id)
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, Splash_Screen.class);
            intent.putExtra("pagetype", remoteMessage.getData().get("PageType"));
            intent.putExtra("message", remoteMessage.getData().get("body"));
            Log.d("Messsagetype", String.valueOf(remoteMessage.getData()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo_new)   // required
                    .setContentText(remoteMessage.getData().get("body")) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(remoteMessage.getData().get("body"))
                    .setLargeIcon(Utils.getBitmapFromURL(remoteMessage.getData().get("smallimageurl")))
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(Utils.getBitmapFromURL(remoteMessage.getData().get("image")))
                            .bigLargeIcon(Utils.getBitmapFromURL(remoteMessage.getData().get("smallimageurl"))))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}


