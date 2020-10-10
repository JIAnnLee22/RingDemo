package com.example.ringdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

class NewOrderReceiver extends BroadcastReceiver {

    NotificationClickListener clickListener;//声明通知监听接口

    @Override
    public void onReceive(Context context, Intent intent) {
        //要跳转的activity
        final Intent newOrder = new Intent(context, MainActivity.class);
        //设置flag为创建新的task和清除原来的栈，点击返回直接到桌面
        newOrder.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //获取action
        String actions = intent.getAction();
        assert actions != null;
        if (actions.equals("notification_clicked")) {
            Log.d(TAG, "onReceive: click");
            //跳转activity
            context.startActivity(newOrder);
            //监听通知点击的接口
            if (clickListener != null) {
                clickListener.notificationClick();
            }
        }

    }

    //监听通知点击
    public void setClickListener(NotificationClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
