package com.example.admin.browserdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class AirplaneModeReceiver extends BroadcastReceiver{

    private static final String CHANNEL_ID = "MyChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getBooleanExtra("state", false)) {
            Log.d("airplanemode", "onReceive");

            Intent google = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("http://google.com");
            google.setData(uri);

            createNotificationChannel(context);

            PendingIntent pending = PendingIntent.getActivity(context, 1, google, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("Google")
                    .setContentText("It's time to go Google")
                    .setSmallIcon(android.R.drawable.btn_radio)
                    .setContentIntent(pending);

            NotificationManagerCompat.from(context).notify(1, builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
