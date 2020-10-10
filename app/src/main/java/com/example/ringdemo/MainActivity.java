package com.example.ringdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, NotificationClickListener {

    private Button play;//播放声音按钮
    private Button stop;//停止声音按钮
    private Button newOrder;//新订单通知测试按钮
    private RadioButton once;//通知音循环一次选项
    private RadioButton thrice;//通知音循环三次选项
    private RadioButton loop;//通知音无限循环选项
    private RadioButton ring;//通知音0选项
    private RadioButton ring1;//通知音1选项
    private SoundPlay soundPlay;//声明音频操作类
    private int raw = R.raw.ring;//通知音
    private NotifyNewOrder notifyNewOrder;//新订单通知类声明
    private NewOrderReceiver receiver;//通知处理广播类声明
    private IntentFilter intentFilter;//通知过滤类声明

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建SoundPlay的实例
        soundPlay = new SoundPlay(this, raw);
        //初始化
        soundPlay.build();

        //创建IntentFilter的实例
        intentFilter = new IntentFilter();
        //添加需要接收的action
        intentFilter.addAction("notification_clicked");

        //创建通知类的实例
        notifyNewOrder = new NotifyNewOrder(this);
        //创建广播的实例
        receiver = new NewOrderReceiver();

        //初始化控件
        initButton();

    }

    private void initButton() {
        //注册按钮
        once = findViewById(R.id.button_once);
        thrice = findViewById(R.id.button_thrice);
        loop = findViewById(R.id.button_loop);
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        newOrder = findViewById(R.id.new_order);

        ring = findViewById(R.id.ring);
        ring1 = findViewById(R.id.ring1);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        newOrder.setOnClickListener(this);

        ring.setOnCheckedChangeListener(this);
        ring1.setOnCheckedChangeListener(this);
        once.setOnCheckedChangeListener(this);
        thrice.setOnCheckedChangeListener(this);
        loop.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlay.onResume();
        //注册广播
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册广播
        unregisterReceiver(receiver);
        if (soundPlay != null) {
            soundPlay.stopSound();
            soundPlay.onDestroy();
            soundPlay = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                soundPlay.startSound();
                break;
            case R.id.stop:
                soundPlay.stopSound();
                break;
            case R.id.new_order:
                notifyNewOrder.build();
                soundPlay.startSound();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            switch (compoundButton.getId()) {
                case R.id.button_once:
                    soundPlay.setFrequency(1);
                    break;
                case R.id.button_thrice:
                    soundPlay.setFrequency(3);
                    break;
                case R.id.button_loop:
                    soundPlay.setFrequency(0);
                    break;
                case R.id.ring:
                    raw = R.raw.ring;
                    soundPlay.setRaw(raw);
                    soundPlay.startSound();
                    break;
                case R.id.ring1:
                    raw = R.raw.ring1;
                    soundPlay.setRaw(raw);
                    soundPlay.startSound();
                    break;
            }
        }
    }


    @Override
    public void notificationClick() {
        //监听到点击了通知就停止通知音
        soundPlay.stopSound();
    }
}