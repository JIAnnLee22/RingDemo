package com.example.ringdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.net.Inet4Address;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class NewOrdersReceiver extends BroadcastReceiver {

    NotificationClickListener clickListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        //要跳转的activity
        final Intent newOrder = new Intent(context, MainActivity.class);
        //设置flag
        newOrder.setFlags(FLAG_ACTIVITY_NEW_TASK);
        //获取action
        String actions = intent.getAction();
        assert actions != null;
        if (actions.equals("notification_clicked")) {
            //跳转activity
            context.startActivity(newOrder);
            if (clickListener != null) {
                clickListener.notificationClick();
            }
        }
    }

    public void setClickListener(NotificationClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
