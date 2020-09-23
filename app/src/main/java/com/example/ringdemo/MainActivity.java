package com.example.ringdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button once, thrice, loop, play, stop;
    private SoundPlay soundPlay;
    private int raw = R.raw.ring;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        once = findViewById(R.id.button_once);
        thrice = findViewById(R.id.button_thrice);
        loop = findViewById(R.id.button_loop);
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);

        once.setOnClickListener(this);
        thrice.setOnClickListener(this);
        loop.setOnClickListener(this);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlay = new SoundPlay(this, raw);
        soundPlay.build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_once:
                soundPlay.setFrequency(1);
                break;
            case R.id.button_thrice:
                soundPlay.setFrequency(3);
                break;
            case R.id.button_loop:
                soundPlay.setFrequency(-1);
                break;
            case R.id.play:
                soundPlay.startSound();
                break;
            case R.id.stop:
                soundPlay.stopSound();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPlay.stopSound();
        soundPlay.release();
        soundPlay = null;
    }
}