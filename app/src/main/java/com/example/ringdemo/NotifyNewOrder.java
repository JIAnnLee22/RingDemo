package com.example.ringdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.ContentValues.TAG;

class NotifyNewOrder {

    private Context context;
    private static final String CHANNEL_ID = "com.example.ringdemo";

    public NotifyNewOrder(Context context) {
        this.context = context;
    }

    public void build() {
        final NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        Intent intentClick = new Intent();
        intentClick.setAction("notification_clicked");
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        NotificationChannel channel;
        final NotificationCompat.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //通知渠道
            channel = new NotificationChannel(CHANNEL_ID, "新订单", NotificationManager.IMPORTANCE_HIGH);
            //在此设置渠道，不然很多定制系统无法获取渠道
            nmc.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(context, channel.getId());
            builder.setContentTitle(channel.getName());
            //不使用通知提示音，而是直接使用soundPool按照次数播放声音
            channel.setSound(null, null);
        } else {
            builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("新订单");
        }
        builder.setSmallIcon(R.drawable.ic_yes)
                .setContentText("内容")
                .setContentIntent(pendingIntentClick)
                .setFullScreenIntent(pendingIntentClick, true)
                .setAutoCancel(true);
        nmc.notify((int) System.currentTimeMillis(), builder.build());
    }
}