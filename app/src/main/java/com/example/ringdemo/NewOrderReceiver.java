package com.example.ringdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

class NewOrderReceiver extends BroadcastReceiver {
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
            Log.d(TAG, "onReceive: click");
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
