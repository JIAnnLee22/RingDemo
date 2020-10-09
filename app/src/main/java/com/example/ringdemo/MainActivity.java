package com.example.ringdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, NotificationClickListener {

    private Button play, stop, newOrder;
    private RadioButton once, thrice, loop, ring, ring1;
    private SoundPlay soundPlay;
    private int raw = R.raw.ring;
    private String TAG = "MainActivity";
    private NotifyNewOrder notifyNewOrder;
    private NewOrdersReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlay = new SoundPlay(this, raw);
        soundPlay.build();

        Intent intentClick = new Intent(this, NewOrdersReceiver.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("newOrders");
        intentClick.setAction("notification_clicked");
        notifyNewOrder = new NotifyNewOrder(this, intentClick);
        receiver = new NewOrdersReceiver();
        receiver.setClickListener(this);
        registerReceiver(receiver, intentFilter);

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
        Intent intentClick = new Intent(this, NewOrdersReceiver.class);

        soundPlay.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlay.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPlay.stopSound();
        soundPlay.onDestroy();
        soundPlay = null;
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
        switch (compoundButton.getId()) {
            case R.id.button_once:
                if (b) {
                    soundPlay.setFrequency(1);
                    soundPlay.startSound();
                }
                break;
            case R.id.button_thrice:
                if (b) {
                    soundPlay.setFrequency(3);
                    soundPlay.startSound();
                }
                break;
            case R.id.button_loop:
                if (b) {
                    soundPlay.setFrequency(0);
                    soundPlay.startSound();
                }
                break;
            case R.id.ring:
                if (b) {
                    raw = R.raw.ring;
                    soundPlay.setRaw(raw);
                }
                break;
            case R.id.ring1:
                if (b) {
                    raw = R.raw.ring1;
                    soundPlay.setRaw(raw);
                }
                break;
        }
    }


    @Override
    public void notificationClick() {
        Log.d(TAG, "notificationClick: click");
    }
}